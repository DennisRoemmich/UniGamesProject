package engine;

import engine.enums.CardColor;
import engine.enums.CardValue;
import engine.enums.GameMode;

public class Trump {

    private final GameMode gameMode;
    private CardColor color;

    /* CONSTRUCTOR */

    public Trump(GameMode mode) {

        gameMode = mode;
    }

    public Trump(CardColor color) {

        gameMode = GameMode.SUIT;
        this.color = color;
    }

    /* GETTER */

    public GameMode getGameMode() {

        return gameMode;
    }

    public CardColor getColor() {

        return color;
    }

    /* ELSE */

    public boolean isTrump(Card card) {

        return switch ( gameMode ) {

            case SUIT -> card.getCardValue() == CardValue.JACK || card.getCardColor() == color;
            case GRAND -> card.getCardValue() == CardValue.JACK;
            case NULL -> false;
        };
    }
}
