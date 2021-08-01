package guiengine;

import guiconsole.Print;
import guiengine.enums.CardColor;
import guiengine.enums.CardValue;
import guiengine.enums.GameMode;

/**
 * class for the different cards
 */
public class Card {

    private CardColor mColor;
    private CardValue mValue;

    /* CONSTRUCTOR */

    public Card(CardColor color, CardValue value) {

        this.mColor = color;
        this.mValue = value;
    }

    /* GETTER */

    public CardColor getCardColor() {

        return mColor;
    }

    public int getColorValue() {

        return mColor.getCardColorValue();
    }

    public CardValue getCardValue() {

        return mValue;
    }

    public int getPoints() {

        return mValue.getCardValue();
    }

    /**
     * combines color strength and value strenght
     * @param trump trump
     * @param color color of current trick
     * @return strenght of card
     */
    public int getStrength(Trump trump, CardColor color) {

        var colorStrength = this.mColor.getColorStrength(trump, color);
        var valueStrength = this.mValue.getValueStrength(trump);

        Print.debug("SORT_TEST", "colorS: " + colorStrength + " valueS: " + valueStrength);

        if (trump.getGameMode() == GameMode.SUIT && mValue == CardValue.JACK) {

            return trump.getColor().getColorStrength(trump, color) + mValue.getValueStrength(trump);
        }

        return colorStrength + valueStrength;
    }

    /* OTHER */

    public boolean isTrump(Trump trump) {

        return switch ( trump.getGameMode() ) {

            case SUIT -> mValue == CardValue.JACK || mColor == trump.getColor();
            case GRAND -> mValue == CardValue.JACK;
            case NULL -> false;
        };
    }

}
