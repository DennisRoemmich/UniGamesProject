package TicTacToe;

import java.io.IOException;
import java.util.Arrays;

public class TicTacToe {


    private char emptyBox = '◦';
    private char symbolPlayer1 = '⍉';
    private char symbolPlayer2 = '✚';

    private String player1 = "Player1";
    private String player2 = "Player2";

    private char[] board = new char[9];

    private int moveNo = 0;


    // call constructor
   TicTacToe() {

       // init empty board
        Arrays.fill(board, emptyBox);

    }

    public void setPlayer1(String name){
        player1 = name;
    }

    public void setPlayer2(String name){
        player2 = name;
    }

    public void startGame() throws IOException {
        gameLoop();
    }

    // reads from input-buffer. Execute read() two times to "delete" buffer after pressing enter
    private char consoleInput() throws IOException {
        char input = (char) System.in.read();
        if (input != 10) {
            System.in.read();
        }
        return input;
    }

    private void gameLoop() throws IOException {

        var exit = false;


        System.out.println("• play: (1 - 9) + [Enter]\n• quit: [q]  + [Enter]\n\n Boxes are numbered like this:");
        drawBoard(true);
        System.out.print("Player 1 (" + symbolPlayer1 + ") has the move\n:");

        while (!exit) {

            // get terminal input
            char input = consoleInput();

            // exit game if desired
            if(input == 'q'){
                System.out.println("Exit game.");
                return;
            }

            // 49 - 57 are the key values of 1 - 9
            if (input >= 49 && input <= 57 ){

                if (board[input - 49] != emptyBox){
                    System.out.print("! This box is already ticked.\n:");
                } else {

                    var winner = setMark(input - 49);
                    drawBoard(false);

                    if (winner || moveNo == 9) {

                        if(winner){
                            System.out.println("\n Player " + (moveNo % 2 + 1) + " WINS after an epic fight of " + (moveNo + 1) + " moves.\n Congratulations!");
                        } else if (moveNo == 9){
                            System.out.println("Tie!");
                        }

                        System.out.println("\nAgain? Press [anything] + [Enter]. Press [q] + [Enter] to quit. ");
                        if (System.in.read() == 'q') {
                            exit = true;
                        } else {
                            Arrays.fill(board, emptyBox);
                            moveNo = 0;
                            drawBoard(true);
                            System.out.print(":");
                        }

                    } else {
                        String playerLabel;

                        if(moveNo % 2 == 0) {
                            playerLabel = "Player 1 (" + symbolPlayer1 + ")";
                        } else {
                            playerLabel = "Player 2 (" + symbolPlayer2 + ")";
                        }
                        System.out.print(playerLabel + " has the move.\n:");
                    }

                }

        } else {

            System.out.print("• play: [1 - 9] + Enter\n• quit: [q]  + Enter\n:");

        }





        }



    }

    private void drawBoard(boolean numbered){


        System.out.println(" ┌───┬───┬───┐ ");

        for (int y = 0; y < 3; y++){

            for (int x = 0; x < 3; x++){
                if (numbered) {
                    System.out.print(" │ " + (y * 3 + x + 1));
                } else {
                    System.out.print(" │ " + board[y * 3 + x]);
                }

            }

            System.out.println(" │");

        }

        System.out.println(" └───┴───┴───┘ ");

    }

    private boolean setMark(int index){

        char mark;

        if (moveNo % 2 == 0){
            mark = symbolPlayer1;
        } else {
            mark = symbolPlayer2;
        }

        board[index] = mark;

        if (board[0] == mark && board[1]  == mark && board[2] == mark){
            return true;
        }
        if (board[3] == mark && board[4]  == mark && board[5] == mark){
            return true;
        }
        if (board[6] == mark && board[7]  == mark && board[8] == mark){
            return true;
        }
        if (board[0] == mark && board[3]  == mark && board[6] == mark){
            return true;
        }
        if (board[1] == mark && board[4]  == mark && board[7] == mark){
            return true;
        }
        if (board[2] == mark && board[5]  == mark && board[8] == mark){
            return true;
        }
        if (board[0] == mark && board[4]  == mark && board[8] == mark){
            return true;
        }
        if (board[6] == mark && board[4]  == mark && board[2] == mark){
            return true;
        }


        moveNo ++;
        return false;

    }


}
