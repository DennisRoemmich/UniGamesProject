package console;

import controller.SkatController;
import engine.*;
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

    /** perspective must be either 0 (forehand), 1(middlehand) or 2(rearhand) */
    public static String auctionToString(SkatController controller, int perspective) {

        var game = controller.getGame();
        var auction = controller.getGame().getAuction();

        var returnString = new StringBuilder();

        var questionerIndex = auction.getQuestioner().getGameIndex();
        var askedPlayerIndex = auction.getHearer().getGameIndex();
        var auctionLevel = auction.getAuctionValue();
        var nextAuctionLevel = auction.getNextAuctionValue();



        if (!game.getPlayerAt(perspective).isBidding()) { // is bidding true when player is forehand at the beginning?
            returnString.append("You passed! Wait for the other players.\n");
        } else if (auction.getInactivePlayer().getGameIndex() == perspective) {
            returnString.append("The other player are bidding. Wait for your turn.\n");
        } else if (auction.getQuestioner().getGameIndex() == perspective){
            returnString.append("Do you want to raise on " + nextAuctionLevel + " against " + controller.getSkatSet().getPlayingPlayerName(askedPlayerIndex) + "? (y/n)\n");
            returnString.append("\n       ⎨ " + nextAuctionLevel + " ⎬\n");
        } else { // player is being asked
            returnString.append(controller.getSkatSet().getPlayingPlayerName(questionerIndex) + " has raised to " + auctionLevel + "!\nDo you want to call? (y/n)\n");
            returnString.append("\n       ⎨ " + auctionLevel + " ⎬\n");
        }



        return returnString.toString();

    }

    private static String trickToString(Trick trick, SkatSet set, String message) {

        var returnString = new StringBuilder();
        final var marginSize = 20;
        final var upperArrow = "⎼⎼⎼⎼⟍⟍";
        final var lowerArrow = "⎺⎺⎺⎺⟋⟋";
        final var arrowMargin = marginSize-upperArrow.length() / 2;

        returnString.append(times(20, "⋯"));
        returnString.append("  " + message + "  ");
        returnString.append(times(40 - (message.length() + 4), "⋯"));
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

                        returnString.append(cardToString(trick.getCardAt(o),i, arrowPart, "0", false));

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
        return handToString(playerHand, message, -1);
    }

    private static String handToString(Hand playerHand, String message, int indexSelected) {


        if (playerHand.isEmpty()) {
            return "\n  Players hand\n  is empty.\n";
        }

        var returnString = new StringBuilder();

        returnString.append(times(20, "⋯"));
        returnString.append("  " + message + "  ");
        returnString.append(times(40-(message.length() + 4), "⋯"));
        returnString.append("\n");

        for (var i = 0; i < 6; i++) {

            returnString.append("     ");

            int o = 0;
            for (Card card : playerHand.getCardsArray()) {
                o++;
                returnString.append(cardToString(card, i, " ", Integer.toString(o), o == indexSelected));

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

        return cardToString(card, line, "", "",  false);

    }

    public static String cardToString(Card card, int line, String margin, String subTitle, boolean selected) {

        if (card == null || card.getCardValue() == null) {
            return "";
        }

        String selector;

        if(selected){
            selector = "·◈·";
        } else {
            selector = " ◇ ";
        }

        return switch (line) {

            case -1 -> "╔═══╗\n║" + card.getCardValue().getSymbol() + "║\n║" + card.getCardValue().getSymbol() + "️║\n╚═══╝\n";

            case 0 -> "╔═══╗";

            case 1 -> "║" + card.getCardValue().getSymbol() + "║";

            case 2 -> "║" + card.getCardColor().getSymbol() + "️║";

            case 3 -> "╚═══╝";

            case 4 -> "╚═══╝";

            case 5 -> " " + selector + " ";

            case 6 -> times((5 - subTitle.length())/2," ") + subTitle + times(((5 - subTitle.length())/2) + ((5 - subTitle.length())/2) % 2," ");

            default -> throw new IllegalStateException("Unexpected value: " + line);

        } + margin;

    }



}