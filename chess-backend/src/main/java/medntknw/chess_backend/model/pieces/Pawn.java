package medntknw.chess_backend.model.pieces;

import medntknw.chess_backend.model.Box;
import medntknw.chess_backend.model.Piece;

public class Pawn extends Piece {
    public Pawn(boolean white) {
        super(white);
    }

    @Override
    public boolean canMove(Box start, Box end) {
        int direction = white ? 1 : -1;
        int dx = end.getX() - start.getX();
        int dy = end.getY() - start.getY();

        // Normal move forward
        if (dy == 0 && dx == direction && end.getPiece() == null) {
            return true;
        }

        // First move can be 2 squares
        if (!hasMoved && dy == 0 && dx == 2 * direction && end.getPiece() == null) {
            return true;
        }

        // Capture diagonally
        if (Math.abs(dy) == 1 && dx == direction && end.getPiece() != null) {
            return end.getPiece().isWhite() != this.isWhite();
        }

        return false;
    }
}