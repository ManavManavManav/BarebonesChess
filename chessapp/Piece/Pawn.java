package chess.src.chessapp.Piece;

import chess.src.chessapp.Board.Board;

/**
 * @author Manav Gohil
 * @author Manankumar Patel
 */
public class Pawn extends Piece {
    /*
     * basic constructor
     * */
    public Pawn(boolean free) {
        super(free);
    }
    /**
     * valid check for pawn that integrates enpassant check
     * checks move length being one unit if pawn isnt on one of the 2 rows
     * @author Manankumar Patel
     * @author Manav Gohil
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
        //stores color of current pawn
        String col = board.getCPiece(x1, y1).getColor();
        if (col.equals("w")) {
            if(enpassant(board, x1, y1, x2, y2)){
                return true;
            }
            if (tempY == 0) {
                // no left right
                if (tempX == -1) {
                    return board.getCPiece(x2, y2) == null;
                } else if (tempX == -2 && x1 == 6) {
                    board.getCPiece(x1,y1).setfree(!(board.getCPiece(x2, y2) == null && board.getCPiece(x1 - 1, y1) == null));
                    return board.getCPiece(x2, y2) == null && board.getCPiece(x1 - 1, y1) == null;

                }
            } else if (Math.abs(tempY) == 1) {
                // diagonal
                if (tempX == -1) {
                    if (board.getCPiece(x2, y2) != null) {
                        return board.getCPiece(x2, y2).getColor().equals("b");
                    }
                }
            }
        } else {
            if(enpassant(board, x1, y1, x2, y2)){
                return true;
            }
            // black pawn
            if (tempY == 0) {
                // no left right
                if (tempX == 1) {
                    return board.getCPiece(x2, y2) == null;
                } else if (tempX == 2 && x1 == 1) {
                    board.getCPiece(x1,y1).setfree(!(board.getCPiece(x2, y2) == null && board.getCPiece(x1 + 1, y1) == null));
                    return board.getCPiece(x2, y2) == null && board.getCPiece(x1 + 1, y1) == null;
                }
                
            } else if (Math.abs(tempY) == 1) {
                // diagonal
                if (tempX == 1) {
                    if (board.getCPiece(x2, y2) != null) {
                        return board.getCPiece(x2, y2).getColor().equals("w");
                    }
                }
            }
        }

        return false;
    }
    /**
     * checks the board history to verify pawn being last moved up and moved 2 places
     * verifies this eligibility and runs this test as part of the standard pawn valid
     * @param board
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return boolean whether enpassant conditions are met
     */
    public boolean enpassant(Board board, int x1, int y1, int x2, int y2){
        //retrieves the last move made
        int[] arrz = (board.getHistory());
        int tempX = x2-x1;
        int tempY = y2-y1;
        //where move placed piece
        int[] lastMoveFinal = new int[]{arrz[2],arrz[3]};
        //where move moved from
        int[] lastMoveIniital = new int[]{arrz[0],arrz[1]};
        if(board.getCPiece(x1,y1).getColor().equals("w")){
            if(x1==3) {
                if(Math.abs(tempX)==1 && Math.abs(tempY)==1){
                    //if the final pawn pos is the same as last moves col, if last move was 2 wide, and if its one behind the last pawn moved(passed it)
                    if(arrz[3]==y2&&arrz[2]-arrz[0]==2&&arrz[2]-1==x2){
                        board.getCell(arrz[2],arrz[3]).setPiece(null);
                        return true;
                    }
                }
            }
        }
        else if(board.getCPiece(x1,y1).getColor().equals("b")){
            if(x1==4) {
                if(Math.abs(tempX)==1 && Math.abs(tempY)==1){
                    //if the final pawn pos is the same as last moves col, if last move was 2 wide, and if its one behind the last pawn moved(passed it)
                    if(arrz[3]==y2&&arrz[2]-arrz[0]==-2&&arrz[2]+1==x2){
                        board.getCell(arrz[2],arrz[3]).setPiece(null);
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
