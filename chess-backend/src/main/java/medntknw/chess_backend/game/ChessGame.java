package medntknw.chess_backend.game;

import java.util.*;
import medntknw.chess_backend.model.*;
import medntknw.chess_backend.model.pieces.King;

public class ChessGame {
    private List<Player> players;
    private Board board;
    private int currPlayerIndex;
    private GameStatus gameStatus;
    private List<Move> movesPlayed;

    public ChessGame(){
        this.players = new ArrayList<>();
        this.board = new Board();
        this.currPlayerIndex = 0;
        this.gameStatus = GameStatus.ACTIVE;
        this.movesPlayed = new ArrayList<>();
    }

    public Board getBoard(){
        return board;
    }
    public List<Player> getPlayers(){
        return players;
    }
    public void resetBoard(){
        this.board.resetBoxes();
    }
    public void addPlayer(Player player){
        if(this.players.size() == 2){
            System.out.println("Cannot add more than 2 players!");
            return;
        }
        this.players.add(player);
    }

    public Player getActivePlayer(){
        return players.get(currPlayerIndex);
    }

    public void getOtherPlayer(){
        this.currPlayerIndex ^= 1;
    }
    public void setGameStatus(GameStatus gameStatus){
        this.gameStatus = gameStatus;
    }
    public GameStatus getGameStatus(){
        return this.gameStatus;
    }

    public Move makeMove(Player player, int startX, int startY, int endX, int endY){
        Box start = board.getBox(startX, startY);
        Box end = board.getBox(endX, endY);
        return new Move(player, start, end);

    }

    public boolean isValidMove(Move move){
        // Check if the end piece is same color as start piece
        if(move.getPieceMoved().isWhite() && move.getPieceKilled() != null && move.getPieceKilled().isWhite()){
            return false;
        }
        // Check the Piece movement
        return move.getPieceMoved().canMove(move.getStart(), move.getEnd());
    }

    public boolean isOver(Move move){
        if(move.getPieceKilled() instanceof King) {
            if(move.getPieceKilled().isWhite()) setGameStatus(GameStatus.BLACKWIN);
            else setGameStatus(GameStatus.WHITEWIN);
            return true;
        }
        return false;
    }

    public void executeMove(Move move){
        board.updateBox(move.getEnd().getX(), move.getEnd().getY(), move.getPieceMoved());
        board.updateBox(move.getStart().getX(), move.getStart().getY(), null);
    }

}