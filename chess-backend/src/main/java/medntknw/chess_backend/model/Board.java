package medntknw.chess_backend.model;

import medntknw.chess_backend.model.pieces.*;

public class Board {
    Box[][] boxes;
    final int sz = 8;
    public Board() {
        this.boxes = new Box[sz][sz];
    }

    public void resetBoxes(){
        for(int i=0; i<sz; i++){
            for(int j=0; j<sz; j++){
                boxes[i][j] = new Box(i, j);
            }
        }
        // Set top of the board as white
        boxes[0][0] = new Box(0,0,new Rook(true));
        boxes[0][1] = new Box(0,1,new Knight(true));
        boxes[0][2] = new Box(0,2,new Bishop(true));
        boxes[0][3] = new Box(0,3,new Queen(true));
        boxes[0][4] = new Box(0,4,new King(true));
        boxes[0][5] = new Box(0,5,new Bishop(true));
        boxes[0][6] = new Box(0,6,new Knight(true));
        boxes[0][7] = new Box(0,7,new Rook(true));
        for(int j=0;j<8;j++){
            boxes[1][j] = new Box(1, j, new Pawn(true));
        }

        // Set bottom of board as black
        boxes[7][0] = new Box(7,0,new Rook(false));
        boxes[7][1] = new Box(7,1,new Knight(false));
        boxes[7][2] = new Box(7,2,new Bishop(false));
        boxes[7][3] = new Box(7,3,new Queen(false));
        boxes[7][4] = new Box(7,4,new King(false));
        boxes[7][5] = new Box(7,5,new Bishop(false));
        boxes[7][6] = new Box(7,6,new Knight(false));
        boxes[7][7] = new Box(7,7,new Rook(false));
        for(int j=0;j<8;j++){
            boxes[6][j] = new Box(6, j, new Pawn(false));
        }
    }

    public void updateBox(int x, int y, Piece piece){
        boxes[x][y].setPiece(piece);
    }

    public Box getBox(int x, int y){
        return boxes[x][y];
    }
}