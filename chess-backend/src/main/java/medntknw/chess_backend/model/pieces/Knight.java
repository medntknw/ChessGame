package medntknw.chess_backend.model.pieces;

import medntknw.chess_backend.model.Box;
import medntknw.chess_backend.model.Piece;

public class Knight extends Piece {
    public Knight(boolean white) {
        super(white);
    }

    @Override
    public boolean canMove(Box start, Box end) {
        int dx = Math.abs(end.getX() - start.getX());
        int dy = Math.abs(end.getY() - start.getY());

        // Knight moves in L-shape: 2 squares in one direction and 1 in the other
        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }
}
