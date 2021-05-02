package tictactoe;

import java.util.Arrays;
import java.util.Random;

public class TicBot {

    private static final String NAME = Settings.BOT_NAME;
    private Difficulty diff = Difficulty.STRONG;
    private static final char EMPTY_BOX = Settings.EMPTY_BOX;

    private final GameBoard board;
    private final Random rand = new Random();

    /* CONSTRUCTOR */
    TicBot(GameBoard board){
        this.board = board;
    }

    /* SETTER */

    public void setDifficulty(TicBot.Difficulty difficulty) {
        this.diff = difficulty;
    }

    /* GETTER */

    public String getName() {
        return NAME;
    }

    // bot will start in 2/3 cases
    public int getPlayer(){
        if (rand.nextInt(3) == 0){
            return 2;
        } else {
            return 1;
        }
    }

    /* ... */

    public int getMove(){

        switch (diff){

            case WEAK, PERFECT:
                // not implemented
                return 0;
            case RANDOM:
                return getRandomMove();
            case STRONG:

                /*
                TODO: it's possible to beat the bot by playing f.e 5 -> 1 -> 9. Change that.
                 */

                // 0 - return a random move with a certain probability, so bot is never really unbeatable

                final var stupidRate = 30; // 1/x is probability of random (stupid) move

                int tickBox;

                if(rand.nextInt(stupidRate) == 0){
                    return getRandomMove();
                }

                // 1 - if one of the first three moves, try to tick center; if not possible tick corner

                if(board.isEmptyBox(4)){
                    return 4;
                } else if (board.getMoveNo() <= 2 && board.isEmptyBox(2) ){
                    return 2;
                } else if (board.getMoveNo() <= 2 && board.isEmptyBox(6) ){
                    return 6;
                }


                // 2 - check if possible wins exists

                tickBox = checkForPossibleWins(board.getCurrentPlayer());

                if(tickBox != -1){
                    return tickBox;
                }

                // 3 - check if possible must-blocks exist

                tickBox = checkForPossibleWins(board.getNotCurrentPlayer());

                if(tickBox != -1){
                    return tickBox;
                }

                // 4 - check for possible forks


                tickBox  = findForkBox(board.getCurrentPlayer());

                if(tickBox != -1){
                    return tickBox;
                }

                // 5 - if nothing worked : return random move

                return getRandomMove();
        }

        return 0;
    }

    // returns a random but valid move
    private int getRandomMove(){
        int moveIndex;

        do {
            moveIndex = rand.nextInt(9);
        } while (!board.isEmptyBox(moveIndex));

        return moveIndex;
    }


    /**
     * @return box that wins game instantly for given player or -1 if not possible
     */
    public int checkForPossibleWins(int player){

        var boxIndex = -1;
        int buf;
        for(var i = 0; i < 3; i++){
            // check rows
            if ((buf = winConditionLine(new int[] { i*3, 1+i*3, 2+i*3 }, player)) != -1){
                boxIndex = buf;
            }
            if ((buf = winConditionLine(new int[] { i, 3+i, 6+i }, player)) != -1) {
                boxIndex = buf;
            }
        }

        if ((buf = winConditionLine(new int[] { 0, 4, 8 }, player)) != -1) {
            boxIndex = buf;
        }

        if ((buf = winConditionLine(new int[] { 2, 4, 6 }, player)) != -1) {
            boxIndex = buf;
        }

        return boxIndex;

    }

    /**
     * @param player player that can fork
     * @return the index of a box that will create a fork (that can win the game) or -1 if no such fork exists
     */
    public int findForkBox(int player){

        var countArray = new int[9];
        Arrays.fill(countArray, 0);

        // find boxes that are alone in their line and count the number if lines for each box. If it's 2, it's a fork.

        int[] buf;
        for(var i = 0; i < 3; i++){
            // check rows
            buf = singleBoxTickedLines(new int[] { i*3, 1+i*3, 2+i*3 }, player);
            if (buf[0] != -1){
                countArray[buf[0]]++;
                countArray[buf[1]]++;
            }
            buf = singleBoxTickedLines(new int[] { i, i+3, i+6 }, player);
            if (buf[0] != -1){
                countArray[buf[0]]++;
                countArray[buf[1]]++;
            }
        }
        buf = singleBoxTickedLines(new int[] { 0, 4, 8 }, player);
        if (buf[0] != -1) {
            countArray[buf[0]]++;
            countArray[buf[1]]++;
        }
        buf = singleBoxTickedLines(new int[] { 2, 4, 6 }, player);
        if (buf[0] != -1) {
            countArray[buf[0]]++;
            countArray[buf[1]]++;
        }

        // find a box that is contained in 2 possible win lines (fork)
        for(var i = 0; i < 9; i++){
            if (countArray[i] == 2) { return i; }
        }
        // no such box exists
        return -1;

    }

    /**
     * @param line takes 3 boxes (a line) and checks if there is a win-condition.
     * @param player Player the win-condition is checked for.
     * @return the index of the empty box if win condition found, otherwise -1
     */
    private int winConditionLine (int[] line, int player){

        var charBoard = board.getCharBoard();
        var intBoard = board.getIntBoard();

        int sum = intBoard[line[0]] + intBoard[line[1]] + intBoard[line[2]];
        if (sum == 12 && player == 1 || sum == 8 && player == 2) {
            if (charBoard[line[0]] == EMPTY_BOX) { return line[0]; }
            if (charBoard[line[1]] == EMPTY_BOX) { return line[1]; }
            if (charBoard[line[2]] == EMPTY_BOX) { return line[2]; }
        }
        return -1;
    }



    /**
     * For a given line, this function checks if the line has exactly one player-box and is empty besides this box.
     * is needed for findForkBox()
     * @param line three boxes (a line)
     * @param player the player the condition is checked for
     * @return if true it returns the two empty boxes in the line; if false it returns an array of -1
     */
    private int[] singleBoxTickedLines(int[] line, int player){

        var charBoard = board.getCharBoard();
        var intBoard = board.getIntBoard();

        var lineBoxArray = new int[]{-1, -1};

        int sum = intBoard[line[0]] + intBoard[line[1]] + intBoard[line[2]];
        if (sum == 21 && player == 1 || sum == 19 && player == 2) {
            if (charBoard[line[0]] == EMPTY_BOX) { lineBoxArray[0] = line[0]; }
            if (charBoard[line[1]] == EMPTY_BOX) {
                if (lineBoxArray[0] == -1){ lineBoxArray[0] = line[1]; } else { lineBoxArray[1] = line[1]; }
            }
            if (charBoard[line[2]] == EMPTY_BOX) { lineBoxArray[1] = line[2]; }
        }
        return lineBoxArray;
    }

    enum Difficulty {
        RANDOM,
        WEAK,
        STRONG,
        PERFECT
    }

}
