package test;

import controller.SkatController;
import engine.*;
import engine.enums.GameMode;
import framework.GameController;
import framework.Player;
import org.json.simple.JSONObject;

public class Test {

    SkatController controller;
    SkatGame game;

    public Test(SkatController controller){

        this.controller = controller;
        this.game = controller.getGame();

        mainTest();

    }


    void mainTest(){

        andi();

    }

    void maik(){

    }

    void andi(){

        Print.times(60, "⋯");
        Print.console("\n");
        Print.console(handToString(game.getCurrentPlayer().getHand()));
        Print.times(60, "⋯");
        Print.console("\n");

        var trump = new Trump(GameMode.GRAND);
        game.getCurrentPlayer().getHand().sort(trump);

        Print.times(60, "⋯");
        Print.console("\n");
        Print.console(handToString(game.getCurrentPlayer().getHand()));
        Print.times(60, "⋯");


    }

    /* print Funktionen -> eigene Klasse */



    /* toString Funktionen */

    public static String handToString(Hand playerHand){


        if ( playerHand.isEmpty() ){
            return "\n  Players hand\n  is empty.\n";
        }

        var returnString = new StringBuilder();

        for ( var i = 0; i < 4; i++ ){

            returnString.append("  ");

            for ( Card card : playerHand.getCardsArray() ){

                returnString.append(cardToString(card, i));

            }

            returnString.append("\n");

        }

        return returnString.toString();

    }

    public static String cardToString(Card card){

        return cardToString(card, -1);

    }

    /**
     *
     * @param card the card that will get printed
     * @param line the line of the card that will get printed. Line printing has 4 lines (0-3). -1 will return the whole card.
     * @return
     */
    public static String cardToString(Card card, int line){

        if ( card == null || card.getCardValue() == null ){
            return "" ;
        }

        return switch ( line ){

            case -1 -> "╔═══╗\n║" + card.getCardValue().getSymbol() + "║\n║" + card.getCardValue().getSymbol() + "️║\n╚═══╝\n";

            case 0 -> "╔═══╗";

            case 1 -> "║" + card.getCardValue().getSymbol() + "║";

            case 2 -> "║" + card.getCardColor().getSymbol() + "️║";

            case 3 -> "╚═══╝";

            default -> throw new IllegalStateException("Unexpected value: " + line);

        };



    }


}















