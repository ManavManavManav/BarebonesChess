package chess.src.chessapp.Board;

import chess.src.chessapp.Cell.*;
import chess.src.chessapp.Piece.*;

/**
 * A 8x8 array of cells with preset locations being certain pieces and certain ranges being certain color
 * @author Manav Gohil
 */
public class Board {
    
    private Cell[][] cells = new Cell[8][8];


    int[] history = new int[4];
    /**
     * sets a board property that contains the last move made
     * @param x1 starting posx
     * @param y1 starting posy
     * @param x2 finishing posx
     * @param y2 finishing posy
     */
    public void setHistory(int x1, int y1, int x2, int y2){
        this.history = new int[]{x1, y1, x2, y2};
    }

    /**
     * retrueves the history stored in history array
     * @return int[] containing history
     */
    public int[] getHistory(){
        return this.history;
    }

    /*
    * basic constructor for board*/
    public Board() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                this.cells[i][j] = new Cell();
            }
        }


        // cells[4][2].setPiece(new Pawn(true));
        // cells[4][2].getPiece().setColor("w");

        cells[7][7].setPiece(new Rook(true));
        cells[7][0].setPiece(new Rook(true));
        cells[0][7].setPiece(new Rook(true));
        cells[0][0].setPiece(new Rook(true));

        cells[7][3].setPiece(new Queen(true));
        cells[0][3].setPiece(new Queen(true));

        cells[0][4].setPiece(new King(true));
        cells[7][4].setPiece(new King(true));

        cells[7][6].setPiece(new Night(true));
        cells[7][1].setPiece(new Night(true));
        cells[0][1].setPiece(new Night(true));
        cells[0][6].setPiece(new Night(true));

        cells[7][2].setPiece(new Bishop(true));
        cells[7][5].setPiece(new Bishop(true));
        cells[0][2].setPiece(new Bishop(true));
        cells[0][5].setPiece(new Bishop(true));

        for (int i = 0; i < cells.length; i++) {
            cells[1][i].setPiece(new Pawn(true));
            cells[6][i].setPiece(new Pawn(true));
        }

        for (int j = 0; j < cells.length; j++) {
            cells[0][j].getPiece().setColor("b");
            cells[1][j].getPiece().setColor("b");

            cells[6][j].getPiece().setColor("w");
            cells[7][j].getPiece().setColor("w");
        }

    }
    /**
     * passes a piece to be stored in a certain cell index
     * @param x cell x
     * @param y cell y
     * @param piece piece type
     */
    public void setCell(int x, int y, Piece piece) {
        cells[x][y].setPiece(piece);
    }

    /**
     * returns the cell object at the board location passed
     * @param x board x
     * @param y board y
     * @return cell object containing all cell properties
     */
    public Cell getCell(int x, int y) {
        if(x<0||x>7||y<0||y>7){return null;}
        return cells[x][y];
    }

    /**
     * skips a middle step if only a piece is needed not the cell
     * @param x board x
     * @param y board y
     * @return piece type
     */
    public Piece getCPiece(int x, int y) {
        if(x<0||x>7||y<0||y>7){return null;}
        return cells[x][y].getPiece();
    }
    /**
     * returns complete board
     * @return the whole 2darray of cells
     */
    public Cell[][] getCells() {
        return cells;
    }
}
