package medntknw.chess_backend.controller;

import medntknw.chess_backend.game.ChessGame;
import medntknw.chess_backend.model.*;
import medntknw.chess_backend.model.pieces.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/chess")
@CrossOrigin(origins = "http://localhost:3000")
public class ChessController {
    private ChessGame game;

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
class BoardState {
    public PieceState[][] squares;

    public BoardState(Board board) {
        squares = new PieceState[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getBox(i, j).getPiece();
                if (piece != null) {
                    squares[i][j] = new PieceState(
                            getPieceType(piece),
                            piece.isWhite()
                    );
                }
            }
        }
    }

    private String getPieceType(Piece piece) {
        if (piece instanceof King) return "KING";
        if (piece instanceof Queen) return "QUEEN";
        if (piece instanceof Rook) return "ROOK";
        if (piece instanceof Bishop) return "BISHOP";
        if (piece instanceof Knight) return "KNIGHT";
        if (piece instanceof Pawn) return "PAWN";
        return null;
    }
}

class PieceState {
    public String type;
    public boolean isWhite;

    public PieceState(String type, boolean isWhite) {
        this.type = type;
        this.isWhite = isWhite;
    }
}

class MoveRequest {
    public int startX;
    public int startY;
    public int endX;
    public int endY;
}

class MoveResult {
    public boolean success;
    public String message;
    public GameStatus gameStatus;
    public BoardState boardState;

    public MoveResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public MoveResult(boolean success, String message, GameStatus gameStatus, BoardState boardState) {
        this.success = success;
        this.message = message;
        this.gameStatus = gameStatus;
        this.boardState = boardState;
    }
}