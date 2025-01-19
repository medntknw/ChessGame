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
    private final ChessGame game;

    public ChessController() {
        this.game = new ChessGame();
        // Initialize with default players
        Player p1 = new Player("player1", "Player 1", "p1@example.com");
        Player p2 = new Player("player2", "Player 2", "p2@example.com");
        p1.setWhiteSide(true);
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.resetBoard();
    }

    @GetMapping("/board")
    public ResponseEntity<BoardState> getBoardState() {
        return ResponseEntity.ok(new BoardState(game.getBoard()));
    }

    @PostMapping("/move")
    public ResponseEntity<MoveResult> makeMove(@RequestBody MoveRequest moveRequest) {
        Move move = game.makeMove(
                game.getActivePlayer(),
                moveRequest.startX,
                moveRequest.startY,
                moveRequest.endX,
                moveRequest.endY
        );

        if (!game.isValidMove(move)) {
            return ResponseEntity.badRequest().body(new MoveResult(false, "Invalid move"));
        }

        game.executeMove(move);
        boolean gameOver = game.isOver(move);
        game.getOtherPlayer();

        return ResponseEntity.ok(new MoveResult(
                true,
                gameOver ? "Game Over" : "Move successful",
                game.getGameStatus(),
                new BoardState(game.getBoard())
        ));
    }
}