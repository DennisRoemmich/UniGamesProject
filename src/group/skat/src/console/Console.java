package console;

import console.enums.ConsoleActionType;
import console.enums.ConsoleState;
import controller.SkatController;
import controller.SkatMove;
import controller.enums.ActionType;
import engine.SkatGame;
import engine.Trump;
import engine.enums.CardColor;
import engine.enums.GamePhase;
import framework.GameController;
import framework.Player;
import org.json.simple.JSONObject;

import java.util.Scanner;

public class Console implements Player {

    /* CONST */

    static final int BORDER_WIDTH = 70;

    static final String WELCOME_MESSAGE = "Welcome to Skat!";
    static final String HELP_MESSAGE = "[Q] - Quit game    ·    [U] - Undo move\n[S] - Sort         ·    [1-12] - Select Card ";
    static final String INSTR_START = "Welcome to Skat!";

    static final String WARNING_DEL = "!: ";
    static final String DEFAULT_DEL = ":: ";
    static final String INPUT_DEL = ":↢ ";
    static final String INSTR_DEL = "⌯: ";
    static final String DEBUG_DEL = "☕︎: ";
    static final String NL = "\n";

    /* VARS */

    SkatController controller;
    int indexCardSelected = -1;

    /** this is the index of the player inside the Game. If set to -1 the Console will always use the currentPlayer as perspective, making it hotseat*/
    int playerGameIndex = -1;

    /* CONSTRUCTOR */

    /** use this Constructor for a dynamic perspective of the console (hotseat)*/
    public Console(SkatController controller ){

        this.controller = controller;

        gameLoop();

    }

    /** use this Constructor for a fixed perspective of the console */
    public Console(SkatController controller, int playerGameIndex ){

        this.controller = controller;
        this.playerGameIndex = playerGameIndex;

        gameLoop();

    }

    /* OTHER */


    private void gameLoop(){

        SkatMove move;

        do{
            display();

            move = getValidConsoleMove();

            // if we have a waiting situation... collect movements on hand? Update on game?

                switch (move.getConsoleType()) {

                    case HELP -> {
                        printHardBorder();
                        printSoftBorder("Instructions");
                        print(HELP_MESSAGE, INSTR_DEL);
                        printSoftBorder("Debug Information");
                        printDebugInfo();
                        printHardBorder();
                        println(1);
                    }

                    case CARD_SELECTION -> indexCardSelected = move.getIndexFrom();

                    case SKATMOVE -> controller.makeMove(move);

                    default -> throw new IllegalStateException("Unexpected value: " + move.getConsoleType());
                }



        }while (move.getConsoleType() != ConsoleActionType.QUIT );

    }

    private SkatMove getValidConsoleMove(){

        SkatMove consoleMove = null;

        var input = getValidInput();

        if (input.equals("q")){
            return new SkatMove(ConsoleActionType.QUIT);
        }

        if (input.equals("h")){
            return new SkatMove(ConsoleActionType.HELP);
        }

        switch (getState()){

            case NOT_STARTED -> consoleMove = new SkatMove(ActionType.NEW_GAME);

            case AUCTION_ASKING, AUCTION_HEARING -> {
                if (input.equals("y")){
                    consoleMove = new SkatMove(ActionType.RAISE_OR_ACCEPT);
                }else{
                    consoleMove = new SkatMove(ActionType.PASS);
                }
            }
            case DECLARE_SKAT -> {

                if(input.equals("a")){  // accept current skat

                    consoleMove = new SkatMove(ActionType.DROP_SKAT);

                } else if (indexCardSelected >= 0){ // switch cards on hand+skat

                    consoleMove = new SkatMove(ActionType.ON_SKATHAND, indexCardSelected, Integer.parseInt(input));
                    indexCardSelected = -1;

                } else { // select card

                    consoleMove = new SkatMove(ConsoleActionType.CARD_SELECTION, Integer.parseInt(input));

                }

            }
            case DECLARE_TRUMP -> {
                var trump = new Trump(CardColor.valueOf((input + "s").toUpperCase()));
                consoleMove = new SkatMove(trump);
            }
            case PLAYING_YOUR_MOVE -> {

                if(input.equals("a")){  // play selected card

                    consoleMove = new SkatMove(indexCardSelected);
                    indexCardSelected = -1;

                } else if (indexCardSelected >= 0){ // switch cards on hand

                    consoleMove = new SkatMove(ActionType.ON_HAND, indexCardSelected, Integer.parseInt(input));
                    indexCardSelected = -1;

                } else { // select card

                    consoleMove = new SkatMove(ConsoleActionType.CARD_SELECTION, Integer.parseInt(input));
                }

            }
            case PLAYING_NOT_YOUR_MOVE -> {

                if (indexCardSelected >= 0){ // switch cards on hand

                    consoleMove = new SkatMove(ActionType.ON_HAND, indexCardSelected, Integer.parseInt(input));
                    indexCardSelected = -1;

                } else { // select card

                    indexCardSelected = Integer.parseInt(input);
                }

            }
            case GAME_FINISHED -> {

                if( input == "y"){
                    consoleMove = new SkatMove(ActionType.NEW_GAME);
                } else {
                    consoleMove = new SkatMove(ConsoleActionType.QUIT);
                }

            }
            case AUCTION_WATCHING, WAIT_FOR_DECLARER -> consoleMove = null;
        }

        return consoleMove;

    }

    /** this functions returns a non-ambiguous input that is valid in the current (Console)GameState */
    private String getValidInput(){

        print("\n", INPUT_DEL);

        String input;
        Scanner in = new Scanner(System.in);

        input = (in.nextLine()).toLowerCase();

        while (!isValidInput(input)){ // !

            print("This is not a valid input. For Help type [H]elp.", WARNING_DEL);
            print("\n", INPUT_DEL);
            input = (in.nextLine()).toLowerCase();

        }


        if(!input.matches("diamond|heart|spade|club")){
            input = String.valueOf(input.charAt(0));
        }

        return input;

    }

    /** This function tells if a given input is valid in the current (Console)GameState  */
    private boolean isValidInput(String input){

        if(input.matches("q|quit|h|help")){
            return true;
        }


        int number;
        try { number = Integer.parseInt(input); } catch (Exception e){ number = 0; }

        return switch (getState()){

            case WAIT_FOR_DECLARER, AUCTION_WATCHING -> false;

            case NOT_STARTED -> true;

            case AUCTION_ASKING, AUCTION_HEARING, GAME_FINISHED ->  input.equals("y") || input.equals("n");

            case DECLARE_SKAT -> (number >= 0 && number < 13) || (input.equals("a") && indexCardSelected >= 0);

            case DECLARE_TRUMP -> input.matches("diamond|heart|spade|club");

            case PLAYING_YOUR_MOVE -> (number >= 1 && number < 11) || (input.equals("a") && indexCardSelected >= 0);

            case PLAYING_NOT_YOUR_MOVE -> (number >= 1 && number < 11);

        };

    }

    int lastCurPlayerIndex = 0;

    private boolean playerDidChange(){

        var index = getPlayerGameIndex();

        if (index != lastCurPlayerIndex){
            lastCurPlayerIndex = index;
            return true;
        } else {
            return false;
        }

    }

    private void display(){

        if (playerDidChange()) {
            var message = controller.getSkatSet().getPlayingPlayerName(getPlayerGameIndex()) + " [" + getPlayerGameIndex() + "]";
            printHardBorder(message);
            println(2);
        }

        switch (getState()){

            case NOT_STARTED -> {

                printHardBorder();
                println(WELCOME_MESSAGE); // PlayerName
                printSoftBorder();
                println(HELP_MESSAGE, INSTR_DEL);
                printSoftBorder();
                println("Enter any key to start the game.", INSTR_DEL);
                println(1);

            }
            case AUCTION_WATCHING, AUCTION_ASKING, AUCTION_HEARING -> print(Print.auctionToString(controller, getPlayerGameIndex()));

            case WAIT_FOR_DECLARER -> {

                /* be able to sort cards here and maybe other passive states*/
                println("Waiting for " + controller.getSkatSet().getPlayingPlayerName(game().getAuction().getAuctionWinner().getGameIndex()) + " to declare the game.");

            }
            case DECLARE_SKAT -> {

                /* Print.skatToString */

            }
            case DECLARE_TRUMP -> {
            }
            case PLAYING_YOUR_MOVE -> {
            }
            case PLAYING_NOT_YOUR_MOVE -> {
            }
            case GAME_FINISHED -> {
            }
        }

    }

    private ConsoleState getState(){

        if(game() == null){
            return ConsoleState.NOT_STARTED;
        }

        switch ( game().getGamePhase() ){

            case NOT_STARTED -> {
                return ConsoleState.NOT_STARTED;
            }
            case AUCTION -> {
                if ( game().getAuction().getQuestioner().getGameIndex() == getPlayerGameIndex()) {
                    return ConsoleState.AUCTION_ASKING;
                }
                if ( game().getAuction().getHearer().getGameIndex() == getPlayerGameIndex()) {
                    return ConsoleState.AUCTION_HEARING;
                }

                return ConsoleState.AUCTION_WATCHING;

            }
            case DECLARING -> {

                if (game().getAuction().getAuctionWinner().getGameIndex() == getPlayerGameIndex()){

                    if (!game().skatIsDropped()){
                        return ConsoleState.DECLARE_SKAT;
                    } else {
                        return ConsoleState.DECLARE_TRUMP;
                    }

                }

            }
            case PLAYING -> {

                if(game().getCurrentPlayer().getGameIndex() == getPlayerGameIndex()){
                    return ConsoleState.PLAYING_YOUR_MOVE;
                } else {
                    return ConsoleState.PLAYING_NOT_YOUR_MOVE;
                }

            }
        }

        return ConsoleState.GAME_FINISHED;

    }



    private int getPlayerGameIndex(){

        if( game() == null ){
            return 0;
        }

        if(playerGameIndex == -1){
            return game().getCurrentPlayer().getGameIndex();
        } else {
            return playerGameIndex;
        }

    }

    /* PRINT */

    private void printDebugInfo(){

        println("current State.........: " + getState().toString(), DEBUG_DEL);
        println("current Player........: " + Integer.toString(getPlayerGameIndex()), DEBUG_DEL);
        println("current Player Name...: " + controller.getSkatSet().getPlayingPlayerName(game().getCurrentPlayer().getGameIndex()), DEBUG_DEL);

        if(game().getGamePhase() == GamePhase.AUCTION){
            println("isAuctioneer..........: " + String.valueOf(game().getPlayerAt(getPlayerGameIndex()) == game().getAuction().getCurrentAuctioneer()), DEBUG_DEL);
            println("isBidding.............: " + String.valueOf(game().getPlayerAt(getPlayerGameIndex()).isBidding()), DEBUG_DEL);
            println("isQuestioner..........: " + String.valueOf(game().getPlayerAt(getPlayerGameIndex()) == game().getAuction().getQuestioner()), DEBUG_DEL);
            println("isHearer..............: " + String.valueOf(game().getPlayerAt(getPlayerGameIndex()) == game().getAuction().getHearer()), DEBUG_DEL);
        }




    }

    private void printSoftBorder(){

        printSoftBorder("");

    }


    private void printSoftBorder(String title){

        if (!title.equals("")){
            title = "  " + title + "  ";
        }

        println(Print.times(10, "⋯") + title + Print.times(BORDER_WIDTH-(10+title.length()), "⋯"), DEFAULT_DEL);

    }

    private void printHardBorder(){

        printHardBorder("");

    }

    private void printHardBorder(String title){

        if (!title.equals("")){
            title = "  " + title + "  ";
        }

        var borderWidth = BORDER_WIDTH-title.length();

        println(Print.times(borderWidth/2, "═") + title + Print.times(borderWidth/2 + (borderWidth/2)%1, "═"), DEFAULT_DEL);

    }

    private void print(Object obj){

        print(obj, DEFAULT_DEL);

    }

    private void print(Object obj, String del){

        try {

            var str = (String)obj;
            str = str.replace("\n","\n" + del);
            Print.console(str);

        } catch (Exception e){
            Print.console(obj);
        }

    }

    private void println(int num){

        print(Print.times(num, "\n"), DEFAULT_DEL);

    }

    private void println(Object obj){

        println(obj, DEFAULT_DEL);

    }

    private void println(Object obj, String del){

        print("\n", del);
        print(obj, del);


    }

    private SkatGame game(){

        return controller.getGame();

    }



    @Override
    public JSONObject requestMove(JSONObject inputType) {
        return null;
    }

    @Override
    public void setController(GameController controller) {

    }
}


