package medntknw.chess_backend.model.pieces;
import medntknw.chess_backend.model.Box;
import medntknw.chess_backend.model.Piece;

public class King extends Piece {
    public King(boolean white) {
        super(white);
    }

    @Override
    public boolean canMove(Box start, Box end) {
        int dx = Math.abs(end.getX() - start.getX());
        int dy = Math.abs(end.getY() - start.getY());

        // King can move one square in any direction
        return dx <= 1 && dy <= 1 && (dx != 0 || dy != 0);
    }
}
