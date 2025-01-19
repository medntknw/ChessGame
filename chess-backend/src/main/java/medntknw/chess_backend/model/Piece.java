package medntknw.chess_backend.model;

public abstract class Piece {
    protected boolean white;
    protected boolean killed;
    protected boolean hasMoved; // For kings, rooks (castling) and pawns (first move)

    public Piece(boolean white) {
        this.white = white;
        this.killed = false;
        this.hasMoved = false;
    }

    public boolean isWhite(){
        return this.white;
    }

    public void setHasMoved(){
        this.hasMoved = true;
    }
    public abstract boolean canMove(Box start, Box end);

    protected boolean isDiagonalMove(Box start, Box end) {
        return Math.abs(end.getX() - start.getX()) == Math.abs(end.getY() - start.getY());
    }

    protected boolean isStraightMove(Box start, Box end) {
        return start.getX() == end.getX() || start.getY() == end.getY();
    }
}
