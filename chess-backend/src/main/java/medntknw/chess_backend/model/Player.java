package medntknw.chess_backend.model;

public class Player extends Person {
    private String username;
    private boolean whiteSide = false;

    public Player(String username, String name, String email){
        super(name, email);
        this.username = username;
        this.whiteSide = false;
    }

    public String getUsername(){
        return this.username;
    }
    public boolean isWhiteSide() {
        return whiteSide;
    }

    public void setWhiteSide(boolean whiteSide) {
        this.whiteSide = whiteSide;
    }
}