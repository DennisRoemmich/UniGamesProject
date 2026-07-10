package engine;

import console.Print;
import engine.enums.CardColor;
import engine.enums.CardValue;
import engine.enums.GameMode;

public class Card {

    private CardColor color;
    private CardValue value;

    /* CONSTRUCTOR */

    public Card(CardColor color, CardValue value) {

        this.color = color;
        this.value = value;
    }

    /* GETTER */

    public CardColor getCardColor() {

        return color;
    }

    public int getColorValue() {

        return color.getCardColorValue();
    }

    public CardValue getCardValue() {

        return value;
    }

    public int getPoints() {

        return value.getCardValue();
    }

    public int getStrength(Trump trump, CardColor color) {

        var colorStrength = this.color.getColorStrength(trump, color);
        var valueStrength = this.value.getValueStrength(trump);

        Print.debug("SORT_TEST", "colorS: " + colorStrength + " valueS: " + valueStrength);

        if (trump.getGameMode() == GameMode.SUIT && value == CardValue.JACK) {

            return trump.getColor().getColorStrength(trump, color) + value.getValueStrength(trump);
        }

        return colorStrength + valueStrength;
    }

    /* OTHER */

    public boolean isTrump(Trump trump) {

        return switch ( trump.getGameMode() ) {

            case SUIT -> value == CardValue.JACK || color == trump.getColor();
            case GRAND -> value == CardValue.JACK;
            case NULL -> false;
        };
    }

}
