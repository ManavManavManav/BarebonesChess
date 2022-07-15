package chess.src.chessapp.Piece;

import chess.src.chessapp.Board.Board;

/**
 * @author Manankumar Patel
 * @author Manav Gohil
 */
public class Queen  extends Piece{
    /*
     * basic constructor
     * */
    public Queen(boolean free) {
        super(free);
    }

    /**
     * @author Manav Gohil
     * @author Manankumar Patel
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return boolean move validity
     */
    @Override
    public boolean valid(Board board, int x1, int y1, int x2, int y2) {

        String colCur = board.getCell(x1, y1).getPiece().getColor();
        int tempX = x2 - x1;
        int tempY = y2 - y1;
        //instant exit if the final piece is the same color(cannot take its own color piece)
        if (board.getCell(x2,y2).getPiece() != null){
            if (board.getCell(x1,y1).getPiece().getColor().equals(board.getCell(x2,y2).getPiece().getColor())){
                return false;
            }
        }
        //same logic as rook without the instant exit case
        if (tempY == 0) {
            int i = 0;
            do {
                if (tempX == i) {
                    return true;
                }

                if (tempX < 0) {
                    i--;
                } else {
                    i++;
                }
            } while (board.getCell(x1 + i, y1).getPiece() == null
                    || ((board.getCell(x1 + i, y1).getPiece().getColor() != colCur) && (tempX == i)));
        } else if(tempX == 0){
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
                    || ((board.getCell(x1, y1+i).getPiece().getColor() != colCur) && (tempY == i)));
        }

        //same logic as bishop without the instant exit cases
        boolean negX = tempX<0;
        boolean negY = tempY<0;
        if(Math.abs(tempX)==Math.abs(tempY)){
            if (tempX < 0) {
                if (tempY < 0) {
                    for (int i = 1; i < Math.abs(tempY); i++) {
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
        return false;

    }
}
