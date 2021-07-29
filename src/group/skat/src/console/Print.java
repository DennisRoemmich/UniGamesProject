package console;

import controller.SkatController;
import engine.*;
import engine.enums.GameMode;

public class Print {
    
    private static final String ACTIVE = "WARNING;ERROR;CONSOLE;INFO";

    private static boolean windows;
    
    private Print() {}

    public static void setWINDOWS(boolean b) {

        windows = b;
    }

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

        var string = (String) obj;

        /* change symbols, bc on windows they're not used */
        if (windows) {

            var symbols = new String[][]{{"⎼", "️·♣·", "·♠·", "·♥·", "·♦·", "⌲", "⌯", "⎨", "⎬", "⋯", "⌾", "♢", "◇", "◈", "⎺"},
                                         {"-", ":C:", ":S:", ":H:", ":D:", ">", "-", "{", "}", "-", "|", "·", "o", "x", "-"}};

            for (var i = 0; i < symbols[0].length; i++) {

                string = string.replace(symbols[0][i], symbols[1][i]);
            }
        }

        System.out.print(string);
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

        var hearer = auction.getHearer();

        if (hearer == null) {
            return "\n\n\n      Do you want to make the game? (y/n)\n";
        }

        var hearerIndex = hearer.getGameIndex();

        var auctionLevel = auction.getAuctionValue();
        var nextAuctionLevel = auction.getNextAuctionValue();



        if (!game.getPlayerAt(perspective).isBidding()) {

            returnString.append("\n\n\n      YOU PASSED! Wait for the other players.\n");

        } else if (auction.getInactivePlayer().getGameIndex() == perspective) {

            returnString.append("\n\n\n      The other player are bidding. Wait for your turn.\n");

        } else if (auction.getQuestioner().getGameIndex() == perspective) {

            returnString.append("\n\n\n      Do you want to RAISE on " + nextAuctionLevel + " against " + controller.getSkatSet().getPlayingPlayerName(hearerIndex) + "? (y/n)\n");
            returnString.append("\n\n                           ⎨ " + nextAuctionLevel + " ⎬\n\n\n");

        } else { // player is being asked

            returnString.append(controller.getSkatSet().getPlayingPlayerName(questionerIndex) + " has raised to " + auctionLevel + "!\nDo you want to call? (y/n)\n");
            returnString.append("\n                               ⎨ " + auctionLevel + " ⎬\n\n");
        }



        return returnString.toString();

    }


    public static String trickToString(Trick trick, SkatController controller) {

        var set = controller.getSkatSet();

        var returnString = new StringBuilder();
        final var marginSize = 10;
        final var upperArrow = "⎼⎼⎼⎼\\\\";
        final var lowerArrow = "⎺⎺⎺⎺//";
        final var arrowMargin = (marginSize-upperArrow.length()) / 2 + 5;

        String message;
        var declarer = controller.getSkatSet().getSkatSetPlayerAt(controller.getGame().getDeclarer().getGameIndex());
        if (controller.getGame().getTrump().getGameMode() == GameMode.SUIT) {

            var clr = controller.getGame().getTrump().getColor();

            if (clr != null) {

                message = declarer.getName() + clr.getSymbol();

            } else {

                message = " ? ";
            }
        } else {

            message = declarer.getName() + " · " + controller.getGame().getTrump().getGameMode().toString();
        }

        returnString.append(times(25, "⋯"));
        returnString.append("  " + message + "  ");
        returnString.append(times(45 - (message.length() + 4), "⋯"));
        returnString.append("\n");


        if (trick == null || trick.getSize() == 0) {

            return trickToStringHelp(controller, declarer);

        } else {

            trickToStringHelp(arrowMargin, upperArrow, returnString, trick, lowerArrow, set);
        }

        returnString.append(times(70, "⋯"));

        if (trick.getSize() == 3) {

            var endCart = "\n";

            var winnerIndex = controller.getGame().getPlayerAt(controller.getGame().getCurrentLeaderIndex()).getGameIndex();
            var winner = controller.getSkatSet().getSkatSetPlayerAt(winnerIndex).getName();

            endCart += times(18, " ") + winner + " has won the last trick!\n\n";

            returnString.append(endCart);
        }

        return returnString.toString();

    }

    private static String trickToStringHelp(SkatController controller, SkatSetPlayer declarer) {

        var trump = "";
        if (controller.getGame().getTrump().getGameMode() == GameMode.SUIT) {

            trump = controller.getGame().getTrump().getColor().getSymbol();

        } else {

            trump = controller.getGame().getTrump().getGameMode().toString();
        }

        return times(20, " ") + declarer.getName() + " plays " + trump + "\n\n" + times(20, " ") + "Play the first card!\n";
    }

    private static void trickToStringHelp(int arrowMargin, String upperArrow, StringBuilder returnString, Trick trick, String lowerArrow, SkatSet set) {

        var del = times(arrowMargin, " ") +  upperArrow + times(arrowMargin, " ");

        for ( var i = 0; i < 5; i++ ) {

            returnString.append("     ");

            for ( var o = 0; o < trick.getSize(); o++) {

                var arrowPart = "";

                if ( o != trick.getSize() && i != 4 && o != 2) {


                    arrowPart = switch (i) {

                        case 0, 3 -> times(del.length(), " ");

                        case 1 -> del;

                        case 2 -> times(arrowMargin, " ") + lowerArrow + times(arrowMargin, " ");

                        default -> throw new IllegalStateException("Unexpected value: " + i);
                    };

                }

                if ( i != 4 ) {

                    returnString.append(cardToString(trick.getCardAt(o),i, arrowPart, "0", false));

                } else {

                    var game = set.getCurrentSkatGame();
                    var playerIndex = (game.getCurrentLeaderIndex() + o) % 3 ;
                    var name = set.getSkatPlayerName(playerIndex);
                    if (trick.getSize() == 3) {
                        var lastWinnerTrickIndex = game.getPlayerAt(game.getCurrentLeaderIndex()).getTricks().getTrickAt(game.getPlayerAt(game.getCurrentLeaderIndex()).getTricksAmount() - 1).getWinnerIndex();
                        var playerIndexPlus = (game.getCurrentLeaderIndex() - lastWinnerTrickIndex + o + 3) % 3;
                        name = set.getSkatPlayerName(playerIndexPlus);
                    }
                    var nameMargin = 5-name.length();

                    returnString.append(times(nameMargin, " ")).append(name).append(times(del.length()+nameMargin, " "));

                }

            }

            returnString.append("\n");

        }
    }

    public static String skatToString(Card[] cards, int indexSelected) {

        var returnString = new StringBuilder();
        var margin = times(8, " ") + " ⌾ " + times(8, " ");

        for (var i = 0; i < 6; i++) {

            returnString.append("                    ");

            var o = 0;
            for (Card card : cards) {
                var marg = margin;
                if (o == 1) {
                    marg = "";
                }
                if (i > 3) {
                    marg = times(19, " ");
                }
                returnString.append(cardToString(card, i, marg, Integer.toString(o+11), o == indexSelected));
                o++;
            }

            returnString.append("\n");

        }

        return returnString.toString();

    }

    public static String handToString(Hand playerHand, String message) {

        return handToString(playerHand, message, -1);
    }

    public static String handToString(Hand playerHand, String message, int indexSelected) {


        if (playerHand.isEmpty()) {
            return "\n~  Players hand\n  is empty. ~\n";
        }

        var returnString = new StringBuilder();

        if (!message.equals("")) {

            returnString.append(times(20, "⋯"));
            returnString.append("  " + message + "  ");
            returnString.append(times(40-(message.length() + 4), "⋯"));
            returnString.append("\n");

        }



        for (var i = 0; i < 6; i++) {

            returnString.append("       ");

            var o = 0;
            for (Card card : playerHand.getCardsArray()) {

                if (o < 10) {
                    returnString.append(cardToString(card, i, " ", Integer.toString(o + 1), o == indexSelected));
                }
                o++;

            }

            returnString.append("\n");

        }

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

        if (selected) {
            selector = "·◈·";
        } else {
            selector = " ◇ ";
        }

        return switch (line) {

            case -1 -> "╔═══╗\n║" + card.getCardValue().getSymbol() + "║\n║" + card.getCardColor().getSymbol() + "️║\n╚═══╝\n";

            case 0 -> "╔═══╗";

            case 1 -> "║" + card.getCardValue().getSymbol() + "║";

            case 2 -> "║" + card.getCardColor().getSymbol() + "║";

            case 3 -> "╚═══╝";

            case 4 -> times((5 - subTitle.length())/2," ") + subTitle + times(((5 - subTitle.length())/2) + ((5 - subTitle.length())/2) % 2," ");

            case 5 -> " " + selector + " ";

            default -> throw new IllegalStateException("Unexpected value: " + line);

        } + margin;

    }

    public static String resultToString(SkatController controller) {

        var string = new StringBuilder();

        var set = controller.getSkatSet();
        var game = controller.getGame();
        var result =game.getGameResult();
        var lastTrick = game.getPlayerAt(game.getCurrentLeaderIndex()).getTricks().getTrickAt(game.getPlayerAt(game.getCurrentLeaderIndex()).getTricksAmount() - 1);
        string.append(trickToString(lastTrick, controller));

        String trump;
        if (result.getTrump().getGameMode() == GameMode.SUIT) {
            trump = result.getTrump().getColor().getSymbol();
        } else {
            trump = result.getTrump().getGameMode().toString();
        }

        var decTricks = game.getDeclarer().getTricks().getSize();
        var decPoints = game.getDeclarer().getTricks().getValue();

        string.append("\n" + times(10, " ") + "Declarer has " + decTricks + " tricks with value of " + decPoints + "!");

        if (game.getDeclarer().getFinalScore() > 0) {
            string.append("\n" + times(10, " ") + set.getPlayingPlayerName(game.getDeclarer().getGameIndex()) + " has won a game of " + trump + "\n\n");
        } else {
            string.append("\n" + times(10, " ") + set.getPlayingPlayerName(game.getDeclarer().getGameIndex()) + " has lost a game of " + trump + "\n\n");
        }
        string.append(times(10, " ") + "He gets " + game.getDeclarer().getFinalScore() + " points!");

        string.append(times(10, " ") + "\n\nAfter Game No " + (set.currentGameNo() + 1) + " of " + set.getGameAmount() + ", these are the standings: \n\n\n");

        for (var a = 0; a < set.getSkatSetPlayerAmount(); a++) {

            string.append(times(10, " ") + set.getSkatPlayerName(a) + ": " + set.getSkatSetPlayerAt(a).getTotalScore() + " points\n\n");
        }

        return string.toString();
    }



}