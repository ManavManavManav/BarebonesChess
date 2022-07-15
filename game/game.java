package chess.src.game;

import java.util.Scanner;
import java.util.regex.Pattern;

import chess.src.chessapp.Board.*;
import chess.src.chessapp.Cell.*;
import chess.src.chessapp.Piece.*;

/*
    *main class game, that has a play method with the chess implementation
    *contains methods for printing the visual board, location indexer(user input to array index),status flags(draw, stalemate, checkmate, etc)

    @author Manav Gohil mrg225
    @author Manan Patel mpp128
*/
public class game {

    Board mainBoard = new Board();

    /*
     * prints initial board and starts the scanner to take in user inputs
     * scanMain scans a line and scan iterates through the line to get the specific
     * start final pos
     * the player is asked for input until gameEnd flag is triggered or game is
     * resigned, draw, or won by a player
     * 
     * @author Manav Gohil mrg225
     * 
     * @author Manan Patel mpp128
     */
    public void play() {

        // first print of freshboard
        printBoard(mainBoard);

        // this boolean controls game flow and when game end is triggered
        boolean gameEnd = false;

        boolean isDraw=false;

        // white turn is the turn boolean that alternates between both turns and is
        // toggled at the end of each turn completition
        boolean whiteTurn = true;

        try {
            int x, y;
            // main scanner, scans line by line till new line character
            Scanner scanMain = new Scanner(System.in);
            scanMain.useDelimiter(Pattern.compile("(\\n)|;"));
            int[] lastlastMove = { -1, -1 };
            int[] LastMove = { -1, -1 };
            while (!gameEnd) {

                // gets the kings position for the current turn color
                String currentColor = whiteTurn ? "w" : "b";
                int[][] kingCell = getKingCell(currentColor);

                // checks for a "check" and if triggered further checks for a king move out of
                // check, and finally if check can be blocked or the check giver can be killed
                if (((King) mainBoard.getCell(kingCell[0][0], kingCell[0][1]).getPiece()).isCheck(mainBoard,
                        kingCell[0][0], kingCell[0][1])) {
                    if ((((King) mainBoard.getCell(kingCell[0][0], kingCell[0][1]).getPiece()).isCircleCheck(mainBoard,
                            kingCell[0][0], kingCell[0][1])
                            && ((King) mainBoard.getCell(kingCell[0][0], kingCell[0][1]).getPiece())
                                    .isCheckBlock(mainBoard, kingCell[0][0], kingCell[0][1]))) {
                        System.out.println("Checkmate");
                        System.out.println(!whiteTurn ? "White Wins" : "Black Wins");
                        return;
                    }
                    System.out.println("Check");
                }

                // prints turn based on turn toggle
                if (whiteTurn) {
                    System.out.println("White Turn: ");
                } else {
                    System.out.println("Black Turn: ");
                }
                // string holds promotion piece when promotion is applicable
                String promotion = "";

                // contains each line
                String line = scanMain.next();

                // first check for draw case
                if (line.contains("draw?")) {
                    isDraw=true;
                }
                // second check for resign
                if (line.contains("resign")) {
                    // if last move was made by black, then resign was triggered by black and thus
                    // white wins, otherwise white wins
                    System.out.println(!whiteTurn ? "White wins" : "Black wins");
                    return;
                }

                // move scanner that iteraters through the line entry
                Scanner scan = new Scanner(line);

                // move string is index starting position
                String move = scan.next();
                // moveTo is index destination position
                String moveTo = scan.hasNext() ? scan.next() : "";

                // getLoc converts chess grid to a array form grid, due to arrays being top to
                // bottom and not reverse
                int[] coord1 = getLoc(move);
                int[] coord2 = getLoc(moveTo);

                // stores the piece at initial position provided by user
                Piece temp = mainBoard.getCell(coord1[0], coord1[1]).getPiece();

                // runs a validity check and asks again if: empty box selected, invalid final
                // destination, wrong color piece, exposesKing to check
                while (temp == null || !temp.valid(mainBoard, coord1[0], coord1[1], coord2[0], coord2[1])
                        || (temp.getColor().equals("w") && !whiteTurn)
                        || temp.getColor().equals("b") && whiteTurn
                        || exposeKing(mainBoard, coord1[0], coord1[1], coord2[0], coord2[1])) {

                    System.out.println("Illegal move, try again");
                    if (whiteTurn) {
                        System.out.println("White turn: ");
                    } else {
                        System.out.println("Black Turn: ");
                    }
                    line = scanMain.next();

                    if (line.contains("draw?")) {
                        isDraw=true;
                    }
                    scan = new Scanner(line);
                    move = scan.next();
                    moveTo = scan.next();
                    coord1 = getLoc(move);
                    coord2 = getLoc(moveTo);
                    temp = mainBoard.getCell(coord1[0], coord1[1]).getPiece();
                }

                // does a preliminary castle check if a king is selected
                if (temp instanceof King) {
                    ((King) temp).hasMoved = true;
                    // canCastle = false;

                    //if the castle move was considered valid then moves the rook as well, valid check only moves king, here we move the rook
                    if (coord1[0] == 7 || coord1[0] == 0) {
                        Rook temp1;
                        if (coord2[1] - coord1[1] == 2) {
                            temp1 = (Rook) mainBoard.getCell(coord1[0], 7).getPiece();
                            mainBoard.getCell(coord1[0], 5).setPiece(temp1);
                            mainBoard.getCell(coord1[0], 7).setPiece(null);
                            temp1.hasMoved = true;
                        } else if (coord2[1] - coord1[1] == -2) {
                            temp1 = (Rook) mainBoard.getCell(coord1[0], 0).getPiece();
                            mainBoard.getCell(coord1[0], 3).setPiece(temp1);
                            mainBoard.getCell(coord1[0], 0).setPiece(null);
                            temp1.hasMoved = true;
                        }
                    }
                }

                // if a king is moved then kings piece flag is triggered, and the king of that
                // color can no longer castle
                if (temp instanceof King) {
                    ((King) temp).hasMoved = true;
                }
                // if a rook is moved that rook can no longer castle
                if (temp instanceof Rook) {
                    ((Rook) temp).hasMoved = true;
                }

                // makes the actual move
                mainBoard.getCell(coord2[0], coord2[1]).setPiece(temp);
                mainBoard.getCell(coord1[0], coord1[1]).setPiece(null);

                // promotion
                if (temp instanceof Pawn) {
                    if ((temp.getColor().equals("w") && coord2[0] == 0)
                            || (temp.getColor().equals("b") && coord2[0] == 7)) {
                        promotion = scan.hasNext() ? scan.next() : "";

                        if (promotion.equals("Q") || promotion.equals("")) {
                            mainBoard.getCell(coord2[0], coord2[1]).setPiece(new Queen(true));

                        } else if (promotion.equals("K")) {
                            mainBoard.getCell(coord2[0], coord2[1]).setPiece(new Night(true));
                        } else if (promotion.equals("R")) {
                            mainBoard.getCell(coord2[0], coord2[1]).setPiece(new Rook(true));
                        } else if (promotion.equals("B")) {
                            mainBoard.getCell(coord2[0], coord2[1]).setPiece(new Bishop(true));
                        }
                        mainBoard.getCell(coord2[0], coord2[1]).getPiece()
                                .setColor(temp.getColor().equals("w") ? "w" : "b");

                    }
                }

                printBoard(mainBoard);

                if(isDraw){
                    System.out.println(!whiteTurn?"White Turn: ":"Black Turn: ");
                    line = scanMain.next();
                    if (line.contains("draw")) {
                        System.out.println("Game Draw");
                        scan.close();
                        scanMain.close();
                        return;
                    }
                }

                // turn toggle
                whiteTurn = whiteTurn ? false : true;

                mainBoard.setHistory(coord1[0], coord1[1], coord2[0], coord2[1]);

                // line(iterator) scanner
                scan.close();
            }
            scanMain.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
    * main method that calls game to start chess game
    * @param args
    * */
    public static void main(String[] args) {
        game game = new game();
        game.play();
    }

    /*
     * returns the cell location of the given colored king
     * 
     * @param color(color of current turn)
     * 
     * @author Manankumar Patel
     * 
     * @return int[][] containing kings location
     */
    public int[][] getKingCell(String color) {
        Cell[][] cells = mainBoard.getCells();
        int[][] ret = new int[1][2];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                Piece current = cells[i][j].getPiece();
                if (current != null && current.getColor().equals(color) && current instanceof King) {
                    ret[0][0] = i;
                    ret[0][1] = j;
                    return ret;
                }
            }
        }
        return ret;
    }

    /*
     * checks whether a certain move will expose the king
     * makes a ghose move and checks whether a check was triggered by said move
     * 
     * @param board, starting position, final position
     * 
     * @author Manav Gohil
     * 
     * @return boolean whether the move will expose the king to check
     */
    public boolean exposeKing(Board board, int x1, int y1, int x2, int y2) {
        Cell[][] cells = mainBoard.getCells();
        int[] ret = { -1, -1 };
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                Piece current = cells[i][j].getPiece();
                if (current != null && (current.getColor().equals(mainBoard.getCPiece(x1, y1).getColor()))
                        && current instanceof King) {
                    ret[0] = i;
                    ret[1] = j;
                }
            }
        }
        // if the piece being moved is the king itself, it relocates the kingCell method
        // to the given index of move
        Piece temp = board.getCPiece(x1, y1);
        if (temp instanceof King) {
            ret[0] = x2;
            ret[1] = y2;
        }

        // fakes the move and runs isCheck to see if this move will cause a "check"
        board.getCell(x1, y1).setPiece(null);
        Piece temp2 = board.getCPiece(x2, y2);
        board.getCell(x2, y2).setPiece(temp);
        // if the move does result in a check to the king, it resets the ghost move and
        // returns true
        if (((King) mainBoard.getCell(ret[0], ret[1]).getPiece()).isCheck(mainBoard, ret[0], ret[1])) {
            board.getCell(x1, y1).setPiece(temp);
            board.getCell(x2, y2).setPiece(temp2);
            return true;
        }
        // if no check is triggered it just resets the ghost move and returns false
        board.getCell(x1, y1).setPiece(temp);
        board.getCell(x2, y2).setPiece(temp2);
        return false;
    }

    /*
     * takes in the user input as string, converts that input to characters and
     * returns an array index that the move is referring to
     * 
     * @param move x and y
     * 
     * @author Manav Gohil
     * 
     * @return int[] containing array converted moves
     */
    public static int[] getLoc(String move) {
        // need to change x and y because arrays uses first forloop for height and
        // second for width
        int x = ((int) move.charAt(0)) - 96;
        int y = (move.charAt(1)) - 48;

        // the x and y are flipped due to java array standard and initial loop being
        // vertical first
        int z = x;
        x = y;
        y = z;
        y--;
        // 8- results in the board bein flipped vertically(8-1 instead of 1-8)
        x = 8 - x;

        return new int[] { x, y };
    }

    /*
     * iterates through all possible move sets for the same color pieces, and if no
     * move is possible then returns true
     * 
     * @param board, current player color, kings location
     * 
     * @author Manav Gohil
     * 
     * @return boolean containing stalemate status
     */
    public boolean staleMate(Board board, String color, int[][] king) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // if the piece at index i,j is not null and same color as the current king
                if (board.getCPiece(i, j) != null && board.getCPiece(i, j).getColor().equals(color)) {
                    for (int k = 0; k < 8; k++) {
                        for (int l = 0; l < 8; l++) {
                            // if the move is valid for the piece at i,j
                            if (board.getCPiece(i, j).valid(board, i, j, k, l)) {
                                Piece temp = board.getCPiece(i, j);
                                board.getCell(i, j).setPiece(null);
                                Piece temp2 = board.getCPiece(k, l);
                                board.getCell(k, l).setPiece(temp);
                                // if the piece move doesnt result in check
                                if (!((King) board.getCPiece(king[0][0], king[0][1])).isCheck(board, king[0][0],
                                        king[0][1])) {
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
        // if no move is possible then it is actually stalemate and return true
        return true;
    }

    /*
     * iterates through the board(a 2d array of cells) and retrieves all the cells,
     * printing their respective colors, and class initials
     * 
     * @param board
     * 
     * @author Manav Gohil
     */
    public static void printBoard(Board board) {

        System.out.println("");
        // one extra line for the index number rows
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 8; j++) {
                if (i < 8) {
                    Piece curr = board.getCell(i, j).getPiece();
                    // if the cell is not empty, then print its color, then check for its obj class,
                    // print the first letter of its class
                    // if not empty= print (piece color+classInitial)
                    if (curr != null) {
                        if(curr instanceof Pawn){
                            System.out.print(curr.getColor()+"p ");
                        }
                        else if(curr instanceof Rook){
                            System.out.print(curr.getColor()+"R ");
                        }
                        else if(curr instanceof Bishop){
                            System.out.print(curr.getColor()+"B ");
                        }
                        else if(curr instanceof King){
                            System.out.print(curr.getColor()+"K ");
                        }
                        else if(curr instanceof Queen){
                            System.out.print(curr.getColor()+"Q ");
                        }
                        else if(curr instanceof Night){
                            System.out.print(curr.getColor()+"N ");
                        } 
                    } else {
                        // if a cell is empty and lies at a black square, print ##
                        System.out.print(((i % 2 == 0 && j % 2 == 1) || (i % 2 == 1 && j % 2 == 0)) ? "## " : "   ");
                    }
                }
                // print index characters for each col
                System.out.print(i == 8 ? " " + (char) (97 + j) + " " : "");
            }
            System.out.println((i != 8) ? 8 - i : " ");
        }
        System.out.println("");
    }

}