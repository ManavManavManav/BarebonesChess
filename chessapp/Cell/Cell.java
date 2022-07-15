package chess.src.chessapp.Cell;

import chess.src.chessapp.Piece.Piece;


/*
    *a basic object that holds the piece and groups with multiple cells to build the 8x8 board

    @author Manav Gohil
*/
public class Cell {
    Piece piece = null;

    /*
    * basic constructor to create empty cell
    * */
    public Cell() {
    }
    /*
     * basic constructor to create cell and put piece in it
     * */
    public Cell(Piece piece) {
        this.piece = piece;
    }

    /**
     * sets the cells piece to pass param piece type
     * @param piece
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * retrieves piece held by the cell
     * @return piece
     */
    public Piece getPiece() {
        return piece;
    }

}
