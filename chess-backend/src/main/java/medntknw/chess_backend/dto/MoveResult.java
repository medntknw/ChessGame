package medntknw.chess_backend.dto;

import medntknw.chess_backend.model.GameStatus;

public class MoveResult {
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