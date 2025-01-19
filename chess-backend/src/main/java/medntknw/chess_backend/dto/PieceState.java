package medntknw.chess_backend.dto;

public class PieceState {
    public String type;
    public boolean isWhite;

    public PieceState(String type, boolean isWhite) {
        this.type = type;
        this.isWhite = isWhite;
    }
}