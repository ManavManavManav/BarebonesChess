package chess.src.chessapp.Piece;

import chess.src.chessapp.Board.Board;

/**
 * bishop object with its valid method
 * @author Manav Gohil
 */
public class Bishop extends Piece {
    /*
    * basic constructor
    * */
    public Bishop(boolean free) {
        super(free);
    }

    /**
     * checks if the move diagnoal
     * @author Manav gohilå
     * @param x1 initial x
     * @param y1 initial y
     * @param x2 final x
     * @param y2 final y
     * @return boolean move validity
     */
    @Override
    public boolean valid(Board board, int x1, int y1, int x2, int y2) {

        int tempX = x2 - x1;
        int tempY = y2 - y1;

        //if net x movement is not the same as net y movement
        if (Math.abs(tempX) != Math.abs(tempY) || (Math.abs(tempX) + Math.abs(tempY)) == 0) {
            return false;
        }
        //if the final destination is non empty and the same color as selected piece
        if (board.getCell(x2,y2).getPiece() != null){
            if (board.getCell(x1,y1).getPiece().getColor().equals(board.getCell(x2,y2).getPiece().getColor())){
                return false;
            }
        }

        //keep going in that diagonal until and exit case is found
        if (tempX < 0) {
            if (tempY < 0) {
                //southwest
                for (int i = 1; i < Math.abs(tempY); i++) {
                    //exit case if a non empty piece is found before reaching the destination
                    if (board.getCell(x1 - i, y1 - i).getPiece() != null) {
                        return false;
                    }
                }

            } else {
                //left up NorthWest
                for (int i = 1; i < Math.abs(tempY); i++) {
                    if (board.getCell(x1 - i, y1 + i).getPiece() != null) {
                        return false;
                    }
                }
            }
            return true;
        } else {
                if (tempY < 0) {
                    //right down SouthEast
                    for (int i = 1; i < Math.abs(tempY); i++) {
                        if (board.getCell(x1 + i, y1 - i).getPiece() != null) {
                            return false;
                        }
                    }
                } else {
                    //right up NorthEast
                    for (int i = 1; i < Math.abs(tempY); i++) {
                        if (board.getCell(x1 + i, y1 + i).getPiece() != null) {
                            return false;
                        }
                    }
                }
            return true;
        }
        }
    }

