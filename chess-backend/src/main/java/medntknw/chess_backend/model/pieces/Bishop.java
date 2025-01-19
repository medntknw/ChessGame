package medntknw.chess_backend.model.pieces;

import medntknw.chess_backend.model.Box;
import medntknw.chess_backend.model.Piece;

public class Bishop extends Piece {
    public Bishop(boolean white) {
        super(white);
    }

    @Override
    public boolean canMove(Box start, Box end) {
        // Bishop can only move diagonally
        return isDiagonalMove(start, end);
    }
}