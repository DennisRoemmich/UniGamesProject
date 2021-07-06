package test;

import engine.Card;
import engine.Hand;
import engine.SkatSet;
import engine.Trick;
import framework.Player;

public class Print {


    private static final String ACTIVE = "WARNING;ERROR;CONSOLE";

    public static void debug(String key, Object obj) {

        if (ACTIVE.contains(key)) {

            print("§" + key + " : " + obj + "\n");

        }

    }

    public static void console(Object obj) {

        var key = "CONSOLE";

        if (ACTIVE.contains(key)) {

            print(obj);

        }

    }

    public static String times(int times, Object obj) {

        var str = new StringBuilder();

        for (var i = 0; i < times; i++) {
            str.append(obj.toString());
        }

        return str.toString();

    }

    private static void print(Object obj) {

        System.out.print(obj);

    }


    public static void hand(Hand playerHand, String message) {

        print(handToString(playerHand, message));

    }


    private static String trickToString(Trick trick, SkatSet set, String message) {

        var returnString = new StringBuilder();
        final var marginSize = 20;
        final var upperArrow = "⎼⎼⎼⎼⟍⟍";
        final var lowerArrow = "⎺⎺⎺⎺⟋⟋";
        final var arrowMargin = marginSize-upperArrow.length() / 2;

        returnString.append(times(20, "⋯"));
        returnString.append("  " + message + "  ");
        returnString.append(times(40-(message.length() + 4), "⋯"));
        returnString.append("\n");

        if (trick == null || trick.getSize() == 0) {
            return "\n  No card played yet. It's [players] move.\n";
        } else {

            for ( var i = 0; i < 5; i++ ) {

                returnString.append("     ");

                for ( var o = 0; o < trick.getSize(); o++){

                    var arrowPart = "";

                    if ( o != trick.getSize() && i != 4 ){

                        arrowPart = switch ( i ) {

                            case 0,3 -> times(marginSize, " ");

                            case 1 -> times(arrowMargin + (arrowMargin % 2), " ") +  upperArrow + times(arrowMargin, " ");

                            case 2 -> times(arrowMargin + (arrowMargin % 2), " ") +  lowerArrow + times(arrowMargin, " ");

                            default -> throw new IllegalStateException("Unexpected value: " + i);
                        };

                    }

                    if ( i != 4 ){

                        returnString.append(cardToString(trick.getCardAt(o),i, arrowPart));

                    } else {

                        var nameMargin = 5 + marginSize;
                        var game = set.getCurrentSkatGame();
                        var playerIndex = (game.getCurrentLeaderIndex() + o) % 3 ;
                        returnString.append(times(nameMargin + (nameMargin % 2), " ")).append(set.getSkatPlayerName(playerIndex)).append(times(nameMargin, " "));

                    }

                }

                returnString.append("\n");

            }

        }

        returnString.append(times(60, "⋯"));

        return returnString.toString();

    }




    private static String handToString(Hand playerHand, String message) {


        if (playerHand.isEmpty()) {
            return "\n  Players hand\n  is empty.\n";
        }

        var returnString = new StringBuilder();

        returnString.append(times(20, "⋯"));
        returnString.append("  " + message + "  ");
        returnString.append(times(40-(message.length() + 4), "⋯"));
        returnString.append("\n");

        for (var i = 0; i < 4; i++) {

            returnString.append("     ");

            for (Card card : playerHand.getCardsArray()) {

                returnString.append(cardToString(card, i));

            }

            returnString.append("\n");

        }

        returnString.append(times(60, "⋯"));

        return returnString.toString();

    }

    public static String cardToString(Card card) {

        return cardToString(card, -1);

    }

    /**
     * @param card the card that will get printed
     * @param line the line of the card that will get printed. Line printing has 4 lines (0-3). -1 will return the whole card.
     * @return
     */
    public static String cardToString(Card card, int line) {

        return cardToString(card, line, "");

    }

    public static String cardToString(Card card, int line, String margin) {

        if (card == null || card.getCardValue() == null) {
            return "";
        }

        return switch (line) {

            case -1 -> "╔═══╗\n║" + card.getCardValue().getSymbol() + "║\n║" + card.getCardValue().getSymbol() + "️║\n╚═══╝\n";

            case 0 -> "╔═══╗";

            case 1 -> "║" + card.getCardValue().getSymbol() + "║";

            case 2 -> "║" + card.getCardColor().getSymbol() + "️║";

            case 3 -> "╚═══╝";

            default -> throw new IllegalStateException("Unexpected value: " + line);

        };

    }



}