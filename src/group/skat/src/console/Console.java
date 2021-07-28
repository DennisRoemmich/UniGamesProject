package console;

import console.enums.ConsoleActionType;
import javaFX.enums.GUIState;
import controller.SkatController;
import controller.SkatMove;
import controller.enums.ActionType;
import engine.SkatGame;
import engine.SkatPlayer;
import engine.Trump;
import engine.enums.CardColor;
import engine.enums.GameMode;
import engine.enums.GamePhase;
import framework.Player;
import org.json.simple.JSONObject;
import test.Test;

import java.util.Scanner;

/*
 *TODO :
 * - [S]ort Hand
 * - Always be able to move on hand
 * - Make this class less ugly
 * - display end of the game
 * - test class and GameClass
 *
 */

public class Console implements Player {

    /* CONST */

    static final int BORDER_WIDTH = 90;

    static final String WELCOME_MESSAGE = "SKAT - CONSOLE VERSION";
    static final String MOVE_NOT_VALID_MESSAGE = "\nOops - you can't play this card. Maybe look on your hand again and THINK this time!";
    static final String HELP_MESSAGE = """
                                       
                                            [Q] - Quit game    ·    [U] - Undo move          ·    [A] - Accept selection    
                                            [S] - Sort         ·    [1-12] - Select Card     .    [S] - Sort Hand                       
                                       """;
    static final String INSTR_START = "Welcome to Skat!";

    static final String WARNING_DEL = "!: ";
    static final String DEFAULT_DEL = ":: ";
    static final String INPUT_DEL = "::⌲ ";
    static final String INSTR_DEL = "⌯: ";
    static final String DEBUG_DEL = "☕︎: ";
    static final String NL = "\n";

    /* VARS */

    boolean mSuitGame = false;

    SkatController mController;
    int mIndexCardSelected = -1;

    /** this is the index of the player inside the Game. If set to -1 the Console will always use the currentPlayer as perspective, making it hotseat*/
    int mPlayerGameIndex = -1;

    /* CONSTRUCTOR */

    /** use this Constructor for a dynamic perspective of the console (hotseat)*/
    public Console(SkatController controller) {

        this.mController = controller;

        var test = new Test(controller);
        // test.consoleSetUp();

        gameLoop();
    }

    /** use this Constructor for a fixed perspective of the console */
    public Console(SkatController controller, int playerGameIndex) {

        this.mController = controller;
        this.mPlayerGameIndex = playerGameIndex;

        gameLoop();
    }

    /* OTHER */


    private void gameLoop() {

        SkatMove move;

        do {
            display();

            move = getValidConsoleMove();

            // if we have a waiting situation... collect movements on hand? Update on game?

                switch (move.getConsoleType()) {

                    case HELP:

                        printHardBorder();
                        printSoftBorder("Instructions");
                        println(1);
                        print(HELP_MESSAGE, INSTR_DEL);
                        printSoftBorder("Debug Information");
                        printDebugInfo();
                        println(1);
                        break;

                    case CARD_SELECTION:

                        mIndexCardSelected = move.getIndexFrom();
                        break;

                    case SKATMOVE:

                        if (!mController.makeMove(move)) {

                            printSoftBorder();
                            print(MOVE_NOT_VALID_MESSAGE, WARNING_DEL);
                            printSoftBorder();

                        }

                        mIndexCardSelected = -1;
                        break;

                    case DECLARE_SUIT:

                        mSuitGame = true;
                        break;

                    default: break;
                }


        } while (move.getConsoleType() != ConsoleActionType.QUIT );

    }

    private SkatMove getValidConsoleMove() {

        SkatMove consoleMove = null;

        var input = getValidInput();

        if (input.equals("q") || input.equals("h") || input.equals("s")) {

            return getValidConsoleMoveHelp(input);
        }

        int number;
        try { number = Integer.parseInt(input); } catch (Exception e) { number = 0; }

        if (number != 0) {

            return getValidConsoleMoveHelpTwo(input);
        }

        var trump = new Trump(CardColor.valueOf((input).toUpperCase()));
        var mode = GameMode.valueOf(input.toUpperCase());

        switch (getState()) {

            case NOT_STARTED:

                consoleMove = new SkatMove(ActionType.NEW_GAME);
                break;

            case AUCTION_ASKING, AUCTION_HEARING:

                if (input.equals("y")) {

                    consoleMove = new SkatMove(ActionType.RAISE_OR_ACCEPT);

                } else {

                    consoleMove = new SkatMove(ActionType.PASS);
                }
                break;

            case DECLARE_SKAT:

                consoleMove = new SkatMove(ActionType.DROP_SKAT);
                break;

            case DECLARE_TRUMPCOLOR:

                consoleMove = new SkatMove(trump);
                break;

            case DECLARE_TRUMPTYPE: {

                if (mode != GameMode.SUIT) {

                    consoleMove = new SkatMove(trump);

                } else {

                    consoleMove = new SkatMove(ConsoleActionType.DECLARE_SUIT);
                }
                break;

            }

            case PLAYING_YOUR_MOVE:

                consoleMove = new SkatMove(mIndexCardSelected);
                break;

            case GAME_FINISHED: {

                if (input.equals("y")) {

                    consoleMove = new SkatMove(ActionType.NEW_GAME);

                } else {

                    consoleMove = new SkatMove(ConsoleActionType.QUIT);
                }
                break;
            }

            default: break;
        }

        return consoleMove;

    }

    private SkatMove getValidConsoleMoveHelp(String input) {

        if (input.equals("q")) {

            return new SkatMove(ConsoleActionType.QUIT);

        } else if (input.equals("h")) {

            return new SkatMove(ConsoleActionType.HELP);

        } else {

            return new SkatMove(ActionType.SORT);
        }
    }

    private SkatMove getValidConsoleMoveHelpTwo(String input) {

        if (mIndexCardSelected == -1) {

            return new SkatMove(ConsoleActionType.CARD_SELECTION, Integer.parseInt(input)-1);

        } else {

            return new SkatMove(ActionType.ON_SKATHAND, mIndexCardSelected, Integer.parseInt(input)-1);

        }
    }

    /** this functions returns a non-ambiguous input that is valid in the current (Console)GameState */
    private String getValidInput() {

        print("\n", INPUT_DEL);

        String input;
        var in = new Scanner(System.in);

        input = (in.nextLine()).toLowerCase();

        while (!isValidInput(input)) { // !

            print(WARNING_DEL + "This is not a valid input. For Help type [H]elp.");
            print("\n", INPUT_DEL);
            input = (in.nextLine()).toLowerCase();

        }

        int number;
        try { number = Integer.parseInt(input); } catch (Exception e) { number = 0; }


        if (input.isEmpty() || getState() == GUIState.NOT_STARTED) {

            input = "x";
        }

        if (!input.matches("diamonds|hearts|spades|clubs|suit|grand|null") && number == 0) {
            input = String.valueOf(input.charAt(0));
        }

        return input;

    }

    /** This function tells if a given input is valid in the current (Console)GameState  */
    private boolean isValidInput(String input) {

        if (input.matches("q|quit|h|help")) {
            return true;
        }

        var state = getState();
        int number;
        try { number = Integer.parseInt(input); } catch (Exception e) { number = 0; }

        if (((number >= 1 && number < 11) || input.equals("sort") ||input.equals("s") ) && state != GUIState.DECLARE_TRUMPCOLOR && state != GUIState.DECLARE_TRUMPTYPE) {
            return true;
        }



        return switch (state) {

            case NOT_STARTED -> true;

            case AUCTION_ASKING, AUCTION_HEARING, GAME_FINISHED ->  input.equals("y") || input.equals("n");

            case DECLARE_SKAT -> input.equals("a") || (number >= 1 && number < 13) ;

            case DECLARE_TRUMPCOLOR -> input.matches("diamonds|hearts|spades|clubs");

            case DECLARE_TRUMPTYPE -> input.matches("suit|grand|null");

            case PLAYING_YOUR_MOVE -> (input.equals("a") && mIndexCardSelected >= 0);

            default -> throw new IllegalStateException("Unexpected value: " + getState());
        };

    }

    int lastCurPlayerIndex = 0;

    private boolean playerDidChange() {

        if (game() == null || game().getCurrentPlayer() == null) {
            return false;
        }

        var index = getPlayerGameIndex();

        if (index != lastCurPlayerIndex) {
            lastCurPlayerIndex = index;
            return true;
        } else {
            return false;
        }

    }

    private void display() {

        if (getState() != GUIState.GAME_FINISHED && playerDidChange()) {

            printHardBorder();
            var message = getCurrentPlayerName() + " is now playing. [P" + getPlayerGameIndex() + "]";
            printSoftBorder(message);
            printHardBorder();
        }

        var hand = "Your Hand:";

        switch (getState()) {

            case NOT_STARTED:

                printHardBorder();
                printSoftBorder(WELCOME_MESSAGE); // PlayerName
                printHardBorder();
                printSoftBorder("Instructions");
                println(HELP_MESSAGE, INSTR_DEL);
                printSoftBorder();
                println("  ♢  Enter ANY KEY to start the game.", INSTR_DEL);

                break;

            case AUCTION_WATCHING, AUCTION_ASKING, AUCTION_HEARING:

                printHardBorder();
                println(2);
                print(Print.auctionToString(mController, getPlayerGameIndex()));
                printHand();

                break;

            case WAIT_FOR_DECLARER:

                printHardBorder();
                /* be able to sort cards here and maybe other passive states*/
                println("Waiting for " + getCurrentPlayerName() + " to declare the game.");

                break;

            case DECLARE_SKAT:

                printHardBorder();
                printSoftBorder("DECLARE SKAT");
                println(2);
                print(Print.skatToString(getPlayer().getHand().getSkat(), mIndexCardSelected -10), DEFAULT_DEL);
                printSoftBorder(hand);
                println(2);
                print(Print.handToString(getPlayer().getHand(),"", mIndexCardSelected));
                print("\n [A]ccept or swap cards [1 - 12]");

                break;

            case DECLARE_TRUMPTYPE:

                printHardBorder();
                printSoftBorder("CHOOSE GAME TYPE");
                println("""
                        
                        
                             SUIT     ·     GRAND     ·     NULL
                             
                        """);
                printSoftBorder(hand);
                println(2);
                print(Print.handToString(getPlayer().getHand(),"", mIndexCardSelected));

                break;

            case DECLARE_TRUMPCOLOR:

                printHardBorder();
                printSoftBorder("CHOOSE GAME COLOR");
                println("""
                                 
                                           
                             Clubs ♣    ·    Spades ♠    ·    Hearts ♥    ·    Diamonds ♦
                             
                        """);
                printSoftBorder(hand);
                println(2);
                print(Print.handToString(getPlayer().getHand(),"", mIndexCardSelected));

                break;

            case PLAYING_YOUR_MOVE:

                printHardBorder();
                println(1);
                if (game().getCurrentTrick().getSize() != 0 || game().getCurrentRoundNo() == 0) {
                    print(Print.trickToString(game().getCurrentTrick(), mController));
                } else {
                    print(Print.trickToString(game().getPlayerAt(game().getCurrentLeaderIndex()).getTricks().getTrickAt(game().getPlayerAt(game().getCurrentLeaderIndex()).getTricksAmount() - 1), mController));
                }
                printSoftBorder(hand);
                println(2);
                print(Print.handToString(getPlayer().getHand(),"", mIndexCardSelected));
                print("\n Select cards [1 - 10] and [a]ccept, switch or [s]ort.");

                break;

            case GAME_FINISHED:

                print(Print.resultToString(mController));
                print("\n\nWant to start the next game?\n\n");

                break;

            default: break;
        }

    }

    private String getCurrentPlayerName() {
        return mController.getSkatSet().getPlayingPlayerName(getPlayer().getGameIndex());
    }

    private GUIState getState() {

        if (game() == null) {

            return GUIState.NOT_STARTED;
        }

        switch (game().getGamePhase()) {

            case NOT_STARTED:

                return GUIState.NOT_STARTED;

            case AUCTION:

                return getStateHelp(GamePhase.AUCTION);

            case DECLARING:

                return getStateHelp(GamePhase.DECLARING);

            case PLAYING:

                if (game().getCurrentPlayer().getGameIndex() == getPlayerGameIndex()) {

                    return GUIState.PLAYING_YOUR_MOVE;

                } else {

                    return GUIState.PLAYING_NOT_YOUR_MOVE;
                }

            case ENDED:

                return GUIState.GAME_FINISHED; // TODO : hier weitermachen

                // if set enden -> GUIState.SetFinished


            case ABORTED:

                return GUIState.GAME_ABORTED;

        }

        return GUIState.GAME_FINISHED;
    }

    private GUIState getStateHelp(GamePhase state) {

        if (state == GamePhase.AUCTION) {

            if ( game().getAuction().getQuestioner().getGameIndex() == getPlayerGameIndex()) {

                return GUIState.AUCTION_ASKING;
            }
            if ( game().getAuction().getHearer().getGameIndex() == getPlayerGameIndex()) {

                return GUIState.AUCTION_HEARING;
            }

        } else {

            if (game().getAuction().getAuctionWinner().getGameIndex() == getPlayerGameIndex()) {

                if (!game().skatIsDropped()) {

                    return GUIState.DECLARE_SKAT;

                } else if (mSuitGame) {

                    return GUIState.DECLARE_TRUMPCOLOR;

                } else {

                        return GUIState.DECLARE_TRUMPTYPE;
                }

            }
        }
        return GUIState.AUCTION_WATCHING;
    }


    private SkatPlayer getPlayer() {
        if (mPlayerGameIndex == -1) {
            return game().getCurrentPlayer();
        } else {
            return game().getPlayerAt(mPlayerGameIndex);
        }
    }

    private int getPlayerGameIndex() {

        if ( game() == null ) {
            return 0;
        }

        if (mPlayerGameIndex == -1) {
            return game().getCurrentPlayer().getGameIndex();
        } else {
            return mPlayerGameIndex;
        }

    }

    /* PRINT */

    private void printHand() {

        printSoftBorder("YOUR HAND");
        println(2);
        print(Print.handToString(getPlayer().getHand(),"", mIndexCardSelected));

    }

    private void printDebugInfo() {

        println("current State.........: " + getState().toString(), DEBUG_DEL);
        println("current Player........: " + Integer.toString(getPlayerGameIndex()), DEBUG_DEL);
        println("current Player Name...: " + getCurrentPlayerName(), DEBUG_DEL);

        if (game().getGamePhase() == GamePhase.AUCTION) {
            println("isAuctioneer..........: " + (game().getPlayerAt(getPlayerGameIndex()) == game().getAuction().getCurrentAuctioneer()), DEBUG_DEL);
            println("isBidding.............: " + (game().getPlayerAt(getPlayerGameIndex()).isBidding()), DEBUG_DEL);
            println("isQuestioner..........: " + (game().getPlayerAt(getPlayerGameIndex()) == game().getAuction().getQuestioner()), DEBUG_DEL);
            println("isHearer..............: " + (game().getPlayerAt(getPlayerGameIndex()) == game().getAuction().getHearer()), DEBUG_DEL);
        }




    }

    private void printSoftBorder() {

        printSoftBorder("");

    }


    private void printSoftBorder(String title) {

        if (!title.equals("")) {
            title = "  " + title + "  ";
        }

        println(Print.times(10, "⋯") + title + Print.times(BORDER_WIDTH-(10+title.length()), "⋯"), DEFAULT_DEL);

    }

    private void printHardBorder() {

        printHardBorder("");

    }

    private void printHardBorder(String title) {

        if (!title.equals("")) {
            title = "  " + title + "  ";
        }

        var borderWidth = BORDER_WIDTH-title.length();

        println(Print.times(borderWidth/2, "═") + title + Print.times(borderWidth/2 + (borderWidth/2), "═"), DEFAULT_DEL);

    }

    private void print(Object obj) {

        print(obj, DEFAULT_DEL);

    }

    private void print(Object obj, String del) {

        try {

            var str = (String)obj;
            str = str.replace("\n","\n" + del);
            Print.console(str);

        } catch (Exception e) {
            Print.console(obj);
        }

    }

    private void println(int num) {

        print(Print.times(num, "\n"), DEFAULT_DEL);

    }

    private void println(Object obj) {

        println(obj, DEFAULT_DEL);

    }

    private void println(Object obj, String del) {

        print("\n", del);
        print(obj, del);


    }

    private SkatGame game() {

        return mController.getGame();

    }


    @Override
    public JSONObject requestMove(JSONObject inputType) {
        return null;
    }

}


