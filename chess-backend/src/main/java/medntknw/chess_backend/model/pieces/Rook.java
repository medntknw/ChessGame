package medntknw.chess_backend.model.pieces;

import medntknw.chess_backend.model.Box;
import medntknw.chess_backend.model.Piece;

public class Rook extends Piece {
    public Rook(boolean white) {
        super(white);
    }

    @Override
    public boolean canMove(Box start, Box end) {
        // Rook can only move straight
        return isStraightMove(start, end);
    }
}