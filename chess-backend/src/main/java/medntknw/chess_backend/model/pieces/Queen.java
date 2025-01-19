package medntknw.chess_backend.model.pieces;
import medntknw.chess_backend.model.Box;
import medntknw.chess_backend.model.Piece;

public class Queen extends Piece {
    public Queen(boolean white) {
        super(white);
    }

    @Override
    public boolean canMove(Box start, Box end) {
        // Queen can move diagonally or straight
        return isDiagonalMove(start, end) || isStraightMove(start, end);
    }
}