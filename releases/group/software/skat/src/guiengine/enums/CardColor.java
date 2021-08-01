package guiengine.enums;

import guiengine.Trump;

/**
 * enum for the color of a card
 */
public enum CardColor {

    CLUBS(12),
    SPADES(11),
    HEARTS(10),
    DIAMONDS(9);

    final int value;

    /* CONSTRUCTOR */

    CardColor(int value) {

        this.value = value;
    }

    /* OTHER */

    public int getCardColorValue() {

        return value;
    }

    /**
     * evaluates the strenght of a cardcolor
     * @param trump current trump
     * @param trickColor current color of trick
     * @return strenght of card
     */
    public int getColorStrength(Trump trump, CardColor trickColor) {

        var clubsValue = 30;
        var spadesValue = 20;
        var heartsValue = 10;
        var diamondsValue = 0;

        if (trickColor != null) {

            if (trickColor == CLUBS) {

                clubsValue = 40;

            } else if (trickColor == SPADES) {

                spadesValue = 40;

            } else if (trickColor == HEARTS) {

                heartsValue = 40;

            } else if (trickColor == DIAMONDS) {

                diamondsValue = 40;
            }
        }

        if (trump.getGameMode() == GameMode.SUIT) {

            if (trump.getColor() == CLUBS) {

                clubsValue = 50;

            } else if (trump.getColor() == SPADES) {

                spadesValue = 50;

            } else if (trump.getColor() == HEARTS) {

                heartsValue = 50;

            } else if (trump.getColor() == DIAMONDS) {

                diamondsValue = 50;
            }
        }

        return switch ( this ) {

            case CLUBS -> clubsValue;
            case SPADES -> spadesValue;
            case HEARTS -> heartsValue;
            case DIAMONDS -> diamondsValue;
        };
    }

    /**
     * returns symbol of color for console-print
     * @return symbol
     */
    public String getSymbol() {

        return switch ( this ) {

            case CLUBS -> "️·♣·";
            case SPADES -> "·♠·";
            case HEARTS -> "·♥·";
            case DIAMONDS -> "·♦·";

        };
    }
}
