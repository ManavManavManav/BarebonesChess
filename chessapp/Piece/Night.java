package chess.src.chessapp.Piece;

import chess.src.chessapp.Board.Board;

/**
 * @author Manav Gohil
 */
public class Night extends Piece {
    /*
    * basic constructor
    * */
    public Night(boolean free) {
        super(free);
    }

    /**
     * checks if the move one of the 8 possible squares knights can move
     * @author Manav gohil
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return boolean move validity
     */
    @Override
    public boolean valid(Board board, int x1, int y1, int x2, int y2) {

        int tempX = x2 - x1;
        int tempY = y2 - y1;

        //if the move is not +/-1 is one axis and +/-2 in another axis
        if ((Math.abs(tempX) + Math.abs(tempY)) != 3 || (tempX == 0 || tempY == 0)) {
            return false;
        }

        //if final pos is empty
        if (board.getCell(x2, y2).getPiece()== null) {
            return true;
        }//final pos is diff color
        else if(board.getCell(x2, y2).getPiece().getColor()!=board.getCell(x1, y1).getPiece().getColor()){
            return true;
        }

        return false;
    }
}
