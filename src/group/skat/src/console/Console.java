package console;

import console.enums.ConsoleState;
import controller.SkatController;
import controller.SkatMove;
import engine.SkatGame;
import engine.enums.GamePhase;
import framework.GameController;
import framework.Player;
import org.json.simple.JSONObject;

import java.util.Scanner;

public class Console implements Player {

    /* CONST */

    static final String WELCOME_MESSAGE = "Welcome to Skat!";
    static final String HELP_MESSAGE = " Instructions\n[Q] - Quit game    ·    [U] - Undo move\n[S] - Sort    ·    [1-12] - Move Card ";
    static final String INSTR_START = "Welcome to Skat!";

    static final String WARNING_DEL = "!: ";
    static final String DEFAULT_DEL = ":: ";
    static final String INPUT_DEL = "::↠";
    static final String INSTR_DEL = "?: ";
    static final String NL = "\n";

    /* VARS */

    SkatController controller;

    /* CONSTRUCTOR */

    Console(SkatController controller ){

        this.controller = controller;

    }

    /* OTHER */


    private void gameLoop(){

        display();


    }

    private JSONObject getValidConsoleMove(){

         // switch ( getInput() )

        return null;

    }

    private String getInput(){

        print("\n", INPUT_DEL);

        String input;
        Scanner in = new Scanner(System.in);

        input = in.nextLine();

        while (validSkatMove(input) != null){ // !

            println("This is not a valid input. For Help type [H]elp.", WARNING_DEL);
            print("\n", INPUT_DEL);
            input = in.nextLine();

        }

        return input;

    }

    private SkatMove validSkatMove(String input){

        int number;
        try { number = Integer.parseInt(input); } catch (Exception e){ number = 0; }

        var valid = switch (getState()){

            case SHOW_INSTRUCTIONS, WAIT_FOR_DECLARER, AUCTION_WATCHING -> false;

            case NOT_STARTED -> true;

            case AUCTION_ASKING, AUCTION_HEARING, GAME_FINISHED ->  input.equalsIgnoreCase("y") || input.equalsIgnoreCase("n");

            case DECLARE_SKAT -> input.equalsIgnoreCase("a") || input.equalsIgnoreCase("accept") || (number >= 0 && number < 13);

            case DECLARE_TRUMP -> input.equalsIgnoreCase("diamond") || input.equalsIgnoreCase("heart") || input.equalsIgnoreCase("spade") || input.equalsIgnoreCase("club");

            case PLAYING_YOUR_MOVE, PLAYING_NOT_YOUR_MOVE -> (number >= 1 && number < 11);

        };

        valid = input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit");
        valid = input.equalsIgnoreCase("h") || input.equalsIgnoreCase("help");

        return null;

    }

    private void display(){

        switch (getState()){

            case SHOW_INSTRUCTIONS -> {

            }

            case NOT_STARTED -> {

                if (game() == null || game().getGamePhase() == GamePhase.NOT_STARTED) {


                    println(WELCOME_MESSAGE); // PlayerName
                    println(HELP_MESSAGE, INSTR_DEL);
                    println("Start Game?");
                    println("[S]tart Game", INSTR_DEL);

                }

            }
            case AUCTION_WATCHING -> {
            }
            case AUCTION_ASKING -> {
            }
            case AUCTION_HEARING -> {
            }
            case WAIT_FOR_DECLARER -> {
            }
            case DECLARE_SKAT -> {
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


        return ConsoleState.NOT_STARTED;

    }

    private void print(Object obj){

        println(obj, DEFAULT_DEL);

    }

    private void print(Object obj, String del){

        try {

            var str = (String)obj;
            str.replace("\n",del + "\n");

        } catch (Exception e){
            Print.console(obj);
        }

    }

    private void println(Object obj){

        println(obj, DEFAULT_DEL);

    }

    private void println(Object obj, String del){

        Print.console(del);
        Print.console(obj);
        Print.console("\n");

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


