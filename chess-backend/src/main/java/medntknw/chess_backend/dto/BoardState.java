package medntknw.chess_backend.dto;

import medntknw.chess_backend.model.Board;
import medntknw.chess_backend.model.Piece;
import medntknw.chess_backend.model.pieces.*;

public class BoardState {
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