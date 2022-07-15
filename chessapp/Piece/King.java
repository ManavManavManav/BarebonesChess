package chess.src.chessapp.Piece;

import chess.src.chessapp.Board.Board;
import chess.src.chessapp.Cell.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * basic king implementation and check triggeres including checkmate
 * @author Manav Gohil
 * @author Manankumar Patel
 */
public class King extends Piece {

    public boolean hasMoved = false;

    /*
     * basic constructor to create king
     * */
    public King(boolean free) {
        super(free);
    }

    /**
     * castle check and normal one length moves
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


            // Piece kg=board.getCPiece(x1, y1);

            // if(kg!=null){
                

            //     if(kg.getColor().equals("w")){
            //         Piece left=board.getCPiece(x2-1, y1-1);
            //         Piece right=board.getCPiece(x2-1, y1+1);
            //     }
            //     else{

            //     }
            // }


            if(((King) board.getCPiece(x1, y1)).isCheck(board, x2,y2)){
                return false;
            }





            if (Math.abs(tempY) == 2 && tempX == 0) {
                if(!hasMoved) {
                    if (!isCheck(board, x1, y1)) {
                        if (tempY == -2) {
                            if(board.getCell(x1 , y1-4).getPiece()==null){
                                return false;
                            }
                            Piece temp = board.getCell(x1 , y1-4).getPiece();
                            if (temp instanceof Rook && !((Rook) temp).hasMoved) {
                                for (int i = 1; i < 4; i++) {
                                    if (board.getCell(x1 , y1-i).getPiece() == null) {
                                        if (i != 3) {
                                            if (isCheck(board, x1 , y1-i)) {
                                                return false;
                                            }
                                        }
                                    } else {
                                        return false;
                                    }
                                }
                                return true;
                            } else {
                                return false;
                            }
                        } else if (tempY == 2) {
                            if(board.getCell(x1, y1+3).getPiece()==null){
                                return false;
                            }
                            Piece temp = board.getCell(x1 , y1+3).getPiece();
                            if (temp instanceof Rook && !((Rook) temp).hasMoved) {
                                for (int i = 1; i < 3; i++) {
                                    if (board.getCell(x1 , y1+i).getPiece() == null) {
                                        if (isCheck(board, x1 , y1+i)) {
                                            return false;
                                        }
                                    } else {
                                        return false;
                                    }
                                }
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }
                } else {return false;}
            }
    
        if ((Math.abs(tempX) == 0 && Math.abs(tempY) == 1) || (Math.abs(tempX) == 1 && Math.abs(tempY) == 0) || (Math.abs(tempX) == 1 && Math.abs(tempY) == 1)) {
            if (board.getCell(x2, y2).getPiece() != null) {
                if((board.getCell(x2, y2).getPiece().getColor().equals(board.getCell(x1, y1).getPiece().getColor()))){
                    return false;
                }
            }
            Piece king = board.getCell(x1, y1).getPiece();
            Piece temp = board.getCell(x2, y2).getPiece();
            board.setCell(x1, y1, null);
            board.setCell(x2, y2, king);
            boolean isCheck = this.isCheck(board, x2, y2);
            board.setCell(x1, y1, king);
            board.setCell(x2, y2, temp);

            return !isCheck;
        }

        return false;
    }

    /*
     * iterates through every piece of the opposite color and runs their move on king to see if cause a check
     * @return boolean if check can be triggered
     * */
    public boolean isCheck(Board board, int x1, int y1) {


        Cell[][] cells = board.getCells();
        List<Piece> pieceList = new ArrayList<>();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                Piece current = cells[i][j].getPiece();
                //if piece is diff color check if it can hit king
                if (current != null &&!(current instanceof King)&& !current.getColor().equals(this.getColor())) {
                    pieceList.add(current);
                    if (current.valid(board, i, j, x1, y1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*
     * checks the surroundings of the king to see if king can escape the check easily
     * @param board board passed in by that instance of game
     * @param x1 king posx
     * @param y1 king posy
     * @return boolean whether king can move out or capture check giving piece
     * */
    public boolean isCircleCheck(Board board, int x1, int y1) {
        King king = (King) board.getCPiece(x1,y1);
        for(int i = x1-1; i<=x1+1;i++){
            for(int j = y1-1; j <=y1+1; j++){
                if((i == x1 && j == y1) ||i<0 || i>7||j<0 || j>7){
                    continue;
                }
                if(king.valid(board, x1, y1, i, j)&& !(king.isCheck(board,i,j))){
                    return false;
                }
            }
        }
        return true;
    }

    /*
     * checks each piece of the same color as king to see if anything can interfer with the check and stop it
     * @param board board passed in by that instance of game
     * @param x1 king posx
     * @param y1 king posy
     * @return boolean whether check can be stopped
     * */
    public boolean isCheckBlock(Board board, int kingX, int kingY){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(board.getCPiece(i,j)!=null&&(board.getCPiece(i,j).getColor().equals(board.getCPiece(kingX,kingY).getColor()))){
                    for (int k = 0; k < 8; k++) {
                        for (int l = 0; l < 8; l++) {
                            if (board.getCPiece(i,j)!=null&&board.getCPiece(i,j).valid(board, i, j, k, l)) {
                                Piece temp = board.getCPiece(i, j);
                                board.getCell(i, j).setPiece(null);
                                Piece temp2 = board.getCPiece(k, l);
                                board.getCell(k, l).setPiece(temp);
                                if (!isCheck(board, kingX, kingY)) {
                                    board.getCell(i, j).setPiece(temp);
                                    board.getCell(k, l).setPiece(temp2);
                                    return false;
                                }
                                board.getCell(i, j).setPiece(temp);
                                board.getCell(k, l).setPiece(temp2);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

}
