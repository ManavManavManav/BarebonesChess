package chess.src.chessapp.Piece;

import chess.src.chessapp.Board.Board;

/**
 * rook class with a valid check and has moved property to keep track of castle flag
 * @author Manav Gohil
 */
public class Rook extends Piece {
    //keeps track of each rook and whether it can castle
    public boolean hasMoved = false;
    /*
     * basic constructor
     * */
    public Rook(boolean free) {
        super(free);

    }

    /**
     * checks if the move is up down left right and within bounds unblocked, or final destination is different color
     * @author Manav gohil
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return boolean move validity
     */
    @Override
    public boolean valid(Board board, int x1, int y1, int x2, int y2) {

        //if diagonal move is made
        if (x1 != x2 && y1 != y2) {
            return false;
        }

        
        String colCur = board.getCell(x1, y1).getPiece().getColor();
        int tempX = x2 - x1;
        int tempY = y2 - y1;


        //vertical move
        if (tempY == 0) {
            int i = 0;
            do {
                //exit case when final destination is reached
                if (tempX == i) {
                    return true;
                }

                if (tempX < 0) {
                    i--;
                } else {
                    i++;
                }
                //keep going in given direction till final destination is reached and is empty or contains a piece of different color
            } while (board.getCell(x1 + i, y1).getPiece() == null
                    || ((board.getCell(x1 + i, y1).getPiece().getColor() != colCur) && (tempX == i)));
        } else {
            //same logic reversed for moving in opposite direction
            int i = 0;
            do {
                if (tempY == i) {
                    return true;
                }

                if (tempY < 0) {
                    i--;
                } else {
                    i++;
                }
            } while (board.getCell(x1, y1 + i).getPiece() == null
                    || ((board.getCell(x1 , y1+i).getPiece().getColor() != colCur) && (tempY == i)));
        }
        return false;

    }

}
