package engine;

import engine.enums.CardColor;
import engine.enums.GameMode;

public class Trump {

    private final GameMode gameMode;
    private CardColor color;

    /* CONSTRUCTOR */

    public Trump(GameMode mode) {

        gameMode = mode;
        color = null;
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
}
