package TicTacToeFX;

import java.util.Arrays;

/*
    TODO : make replay a class
    TODO : moveNo only in GameBoard ?
 */

public class TicTacToe {

    private static final int REPLAY_MOVE_DELAY = Settings.REPLAY_MOVE_DELAY;

    private final GameBoard board = new GameBoard();
    private final TicBot tBot = new TicBot(board);
    private final int[] moveLog = new int[9];
    private final String namePlayer1;
    private final String namePlayer2;

    private int[] scorePlayers = new int[] {0,0};
    private int[] scoreVSKi = new int[] {0,0};

    private String[] currentPlayerNames = new String[] {Settings.PLAYER_NAMES[0], Settings.PLAYER_NAMES[1]};
    private int moveNo = 0;
    private int kiPlayer = 0;

    /* CONSTRUCTOR */
    TicTacToe(String player1, String player2) {

        namePlayer1 = player1;
        namePlayer2 = player2;

        Arrays.fill(moveLog, 0);

    }

    /* SETTER */


    /* GETTER */

    public GameBoard getBoard(){
        return board;
    }

    public String getCurrentPlayerName1(){ return currentPlayerNames[0];}

    public String getCurrentPlayerName2(){
        return currentPlayerNames[1];
    }

    public TicBot getBot() { return tBot; }

    public int[] getScorePlayers() {
        return scorePlayers;
    }

    public int[] getScoreVSKi() {
        return scoreVSKi;
    }


    public int getActivePlayer(){
        return (moveNo % 2) + 1 ;
    }

    public boolean isKiActive(){
        return kiPlayer != 0;
    }

    public int getKiPlayer(){
        return kiPlayer;
    }

    /* ... */

    /**
     * Only call before restarting game
     */
    public void setKI(boolean active){

        if(active){
            kiPlayer = tBot.getPlayer();
            System.out.print(kiPlayer);

            updateNames();
            // PlayerNameStuff
        } else {
            kiPlayer = 0;
        }

    }

    private void updateNames(){
        var looper = new int[] {0,1};

        for (int num : looper) {
            if(kiPlayer == num + 1){
                currentPlayerNames[num] = tBot.getName();
            } else {
                currentPlayerNames[num] = Settings.PLAYER_NAMES[num];
            }
        }


    }

    public void startGame(gameType type){

        switch (type) {
            case PVP -> {
            }
            case KI_RANDOM -> {

            }
            case KI_STRONG -> {
            }
            case REPLAY -> {
            }
        }

    }

    public void startWithTerminal() {

        // start message?

        do {  // game loop

            int inputChar;

            do { // stay in menu after replay

                inputChar = getInput(inputType.MENU);

                if (inputChar == -1) {
                    return;
                }
                if (inputChar == 'r') {
                    replayMatch();
                }

                if (inputChar == 'k') {
                    kiPlayer = tBot.getPlayer();
                    print(printType.MESSAGE, "~ " + tBot.getName() + " is Player " + kiPlayer + " ~\n");
                    currentPlayerNames = new String[] {namePlayer1, namePlayer2};
                    currentPlayerNames[kiPlayer - 1] = tBot.getName();
                } else {
                    kiPlayer = 0;
                    currentPlayerNames = new String[] {namePlayer1, namePlayer2};
                }

            } while (inputChar == 'r');

            restartGame();

        } while (gameLoop() != -1);

    }

    public void restartGame(){
        moveNo = 0;
        board.emptyBoard();
        Arrays.fill(moveLog, -1);
        print(printType.MESSAGE, "~ NEW GAME ~\n\n" + currentPlayerNames[moveNo % 2] + " has the move.\n");

        if(kiPlayer != 0){
            kiPlayer = tBot.getPlayer();
        }
        if(kiPlayer == 1){
            gameMove(tBot.getMove());
        }

        updateNames();

    }

    public int setFXMove(int input, SampleController controller){

        if(moveNo == 9){
            return 3;
        }

        // make sure this is a valid move
        if(board.getIntBoard()[input] != 10){
            return 0;
        }

        var gameState = gameMove(input);

        controller.refreshGrid(false);
        controller.doWaitForKI = true;

        if ((moveNo % 2) + 1 == kiPlayer && gameState == 0) {

            gameState = gameMove(tBot.getMove());
        }

        return gameState;

    }

    private int gameMove(int input){
        board.setTick(input);
        logMove(input);
        int gameState = checkGameState();
        var subtitle = stateMessage(gameState);
        print(printType.BOARD, subtitle);

        moveNo++;

        if(gameState == 1 || gameState == 2){
            if (kiPlayer == gameState){
                scoreVSKi[1]++;
            } else if (kiPlayer != 0){
                scoreVSKi[0]++;
            } else {
                scorePlayers[gameState-1]++;
            }
        }

        return gameState;
    }

    /**
     * every iteration of the loop is one move
     * @return -1 if 'q' was pressed -> game exits
     */
    private int gameLoop() {

        var gameState = 0;

        while (gameState == 0) {

            int input;
            var currentPlayer = (moveNo % 2) + 1;

            if (kiPlayer == 0 || currentPlayer != kiPlayer) {
                input = getInput(inputType.GAME);
            } else {
                input = tBot.getMove() + 1;
            }

            if (input == -1) {
                return -1;
            }

            board.setTick(input - 1);
            logMove(input - 1);

            gameState = checkGameState();

            var subtitle = stateMessage(gameState);

            print(printType.BOARD, subtitle);

            moveNo++;

        }

        return 0;

    }


    private String stateMessage(int gameState) {

        if (gameState == 3) {
            return "its a draw!\n";
        }

        if (gameState == 1 || gameState == 2) {
            return currentPlayerNames[gameState - 1] + " WINS after an epic fight of " + (moveNo + 1) + " moves.\nCongratulations!\n\n";
        }

        return currentPlayerNames[(moveNo + 1) % 2] + " has the move.\n";

    }

    public int checkGameState() {


        if (board.checkForWin()) {
            // game is won
            return (moveNo % 2) + 1;
        }


        if (moveNo == 8) {
            // it's a draw
            return 3;
        }

        // game still running
        return 0;

    }

    private void logMove(int move) {

        moveLog[moveNo] = move;

    }

    /**
     *
     * @param type type of allowed input (menu or ingame)
     * @return input
     */
    private int getInput(inputType type) {

        char inputChar;

        var replayAvailable = moveLog[0] != 0;

        if (type == inputType.MENU) {

            var printString = "Enter ";

            if (replayAvailable) {
                printString += "[r] to watch replay, ";
            }
            printString += ("[h] to play hotseat, [k] to play against KI or [q] to quit.\n→ ");

            print(printType.MESSAGE, printString);

        }
        if (type == inputType.GAME) {
            print(printType.MESSAGE, "Enter [1-9] to play.\n→ ");
        }

        try {
            inputChar = (char) System.in.read();
            if (inputChar != 10) {
                System.in.read();
            }
        } catch (Exception e) {
            // error
            return -1;
        }

        if (inputChar == 'q') {
            return -1;
        }

        if (type == inputType.MENU) {    // replay; quit; new game

            if ((inputChar != 'r' || !replayAvailable) && inputChar != 'k' && inputChar != 'h') {
                print(printType.WARNING, "Invalid input.\n");
                return getInput(type);
            } else {
                return inputChar;
            }

        }

        if (type == inputType.GAME) {

            int numVal = Character.getNumericValue(inputChar);

            if (numVal < 1 || numVal > 9) {
                print(printType.WARNING, "Invalid input.\n");
                return getInput(type);
            } else if (!board.isEmptyBox(numVal - 1)) {
                print(printType.WARNING, "Box is already ticked. Try again.\n");
                return getInput(type);
            } else {
                return numVal;
            }

        }


        // error
        return -1;

    }

    private void print(printType type, String message) {



        var printString = "";

        if (type == printType.WARNING) {
            var border = "----------------------------\n";
            printString += (border + message + border);
        }


        if (type == printType.BOARD || type == printType.BOARDINFO) {

            printString += "  ┌─────┬─────┬─────┐ \n";

            for (var y = 0; y < 3; y++) {

                for (var x = 0; x < 3; x++) {
                    if (type == printType.BOARDINFO) {
                        printString += ("  │  " + (y * 3 + x + 1));
                    } else {
                        printString += ("  │  " + board.getCharBoard()[y * 3 + x]);
                    }

                }

                printString += ("  │\n");

                if (y < 2) {
                    printString += ("  ├─────┼─────┼─────┤ \n");
                }
            }

            printString += ("  └─────┴─────┴─────┘ \n\n");
            printString += message;

        }

        if (type == printType.MESSAGE) {
            printString = message;
        }

        System.out.print(printString);

    }


    private void replayMatch() {

        moveNo = 0;
        board.emptyBoard();

        for (var move : moveLog) {
            if (move != -1) {


                try {
                    Thread.sleep(REPLAY_MOVE_DELAY);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

                print(printType.MESSAGE, "> Move " + (moveNo + 1) + "\n");
                board.setTick(move);
                print(printType.BOARD, "");

                moveNo++;
            }
        }

        print(printType.MESSAGE, stateMessage(checkGameState()));


    }

    enum gameType {
        PVP,
        KI_RANDOM,
        KI_STRONG,
        REPLAY
    }

    enum inputType {
        GAME,
        MENU
    }

    enum printType {
        WARNING,
        BOARD,
        BOARDINFO,
        MESSAGE
    }

}