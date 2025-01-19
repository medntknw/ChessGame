package medntknw.chess_backend.controller;
import medntknw.chess_backend.game.ChessGame;
import medntknw.chess_backend.model.*;
import medntknw.chess_backend.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/chess")
@CrossOrigin(origins = "http://localhost:3000")
public class ChessController {
    private final GameService gameService;

    public ChessController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/game")
    public ResponseEntity<BoardState> createGame(@RequestBody CreateGameRequest createGameRequest) {
        ChessGame game = gameService.createGame(
                createGameRequest.gameId,
                createGameRequest.player1,
                createGameRequest.player2
        );
        return ResponseEntity.ok(new BoardState(game.getBoard()));
    }

    @GetMapping("/game/{gameId}/board")
    public ResponseEntity<BoardState> getBoardState(@PathVariable String gameId) {
        ChessGame game = gameService.getGame(gameId);
        if (game == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new BoardState(game.getBoard()));
    }

    @PostMapping("/game/{gameId}/move")
    public ResponseEntity<MoveResult> makeMove(
            @PathVariable String gameId,
            @RequestBody MoveRequest moveRequest) {
        ChessGame game = gameService.getGame(gameId);
        if (game == null) {
            return ResponseEntity.notFound().build();
        }

        if (game.getGameStatus() != GameStatus.ACTIVE) {
            return ResponseEntity.badRequest()
                    .body(new MoveResult(false, "Game is inactive!"));
        }

        Move move = game.makeMove(
                game.getActivePlayer(),
                moveRequest.startX,
                moveRequest.startY,
                moveRequest.endX,
                moveRequest.endY
        );

        if (!game.isValidMove(move)) {
            return ResponseEntity.badRequest()
                    .body(new MoveResult(false, "Invalid move"));
        }

        game.executeMove(move);
        boolean gameOver = game.isOver(move);
        game.setOtherPlayer();

        return ResponseEntity.ok(new MoveResult(
                true,
                gameOver ? "Game Over" : "Move successful",
                game.getGameStatus(),
                new BoardState(game.getBoard())
        ));
    }
}
