package tictactoe;

import java.util.Arrays;

public class GameBoard {

    /* VARS */

    private static final char EMPTY_BOX = Settings.EMPTY_BOX;
    private static final char SYMBOL_PLAYER_1 = Settings.SYMBOL_PLAYER_1;
    private static final char SYMBOL_PLAYER_2 = Settings.SYMBOL_PLAYER_2;

    private final char[] charBoard = new char[9];   // Board array with chars to print easily
    private final int[] intBoard = new int[9];      // Board array with integers to calculate game-situations

    private int moveNo = 0;

    /* CONSTRUCTOR */
    GameBoard(){
        // init board as empty
        emptyBoard();
    }

    /* SETTER */


    /* GETTER */

    public static char getEmptyBox() {
        return EMPTY_BOX;
    }

    public static char getSymbolPlayer1(){
        return SYMBOL_PLAYER_1;
    }

    public static char getSymbolPlayer2() {
        return SYMBOL_PLAYER_2;
    }

    public int getMoveNo() {
        return moveNo;
    }

    public char[] getCharBoard(){
        return charBoard;
    }

    public int[] getIntBoard() {
        return intBoard;
    }

    public int getCurrentPlayer(){
        return (moveNo % 2) + 1;
    }

    public int getNotCurrentPlayer(){
        return ((moveNo+1) % 2) + 1;
    }

    /* ... */

    public void setTick(int index){

        int currentPlayer = (moveNo % 2) + 1;

        if (currentPlayer == 1) {
            intBoard[index] = 1;
            charBoard[index] = SYMBOL_PLAYER_1;
        } else {
            intBoard[index] = -1;
            charBoard[index] = SYMBOL_PLAYER_2;
        }

        moveNo++;
    }

    public void emptyBoard(){
        Arrays.fill(charBoard, EMPTY_BOX);
        Arrays.fill(intBoard, 10);
        moveNo = 0;
    }

    public boolean isEmptyBox(int index){
        return charBoard[index] == EMPTY_BOX;
    }

    /**
     * checks if there is situation on the board that end the game by winning it
     * @return true if winning, false if not
     */
    public boolean checkForWin(){

        for(int i = 0; i < 3; i++){
            // check rows
            if ( winLine(new int[] { i*3, 1+i*3, 2+i*3 } ) ) { return true; }
            // check columns
            if ( winLine(new int[] { i, 3+i, 6+i } ) ) { return true; }
        }

        //check diagonals
        return winLine(new int[]{0, 4, 8}) || winLine(new int[]{2, 4, 6});

    }

    /**
     * takes 3 boxes (a line) and checks if they are all the same and not empty
     */
    private boolean winLine (int[] line){
        return charBoard[line[0]] != EMPTY_BOX && charBoard[line[0]] == charBoard[line[1]] && charBoard[line[0]] == charBoard[line[2]];
    }


}
