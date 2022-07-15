package chess.src.chessapp.Piece;

import chess.src.chessapp.Board.Board;




/*
    *abstract class Piece that is the framework for the other pieces
    *has a boolean free for general use, and color z as default to avoid confusion

    @author Manav Gohil
*/
public abstract class Piece {
    private String color="z";
    private boolean free;

    /**
     *
     * Piece
     *
     * @param free  free default status is true
     */
    public Piece(boolean free) {
        super();
        this.free = free;
    }
    /**
     *
     * Isfree
     *
     * @return boolean
     */
    public boolean isfree() {
        return free;
    }
    /**
     *
     * Setfree
     *
     * @param free  the free
     */
    public void setfree(boolean free) {
        this.free = free;
    }
    /**
     *
     * Gets the color
     *
     * @return the color
     */
    public String getColor() {
        return color;
    }
    /**
     *
     * Sets the color
     *
     * @param color  the color
     */
    public void setColor(String color) {
        this.color = color;
    }
    /*
    * basic framework for valid method that each piece will implement
    */
    public abstract boolean valid(Board board,  int x1, int y1, int x2, int y2);


}
