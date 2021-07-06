package engine;

import engine.enums.CardColor;
import engine.enums.CardValue;
import engine.enums.GameMode;
import org.json.simple.JSONObject;

public class Trump {

    private static final String GAMEMODE_KEY = "gameMode";
    private static final String COLOR_KEY = "color";

    private GameMode gameMode;
    private CardColor color;

    /* CONSTRUCTOR */

    public Trump() {

    }

    public Trump(GameMode mode) {

        gameMode = mode;
    }

    public Trump(CardColor color) {

        gameMode = GameMode.SUIT;
        this.color = color;
    }

    public Trump(JSONObject obj) {

        gameMode = GameMode.valueOf((String) obj.get(GAMEMODE_KEY));

        if ( gameMode == GameMode.SUIT ){

            color = CardColor.valueOf((String) obj.get(COLOR_KEY));

        }

    }

    /* GETTER */

    public GameMode getGameMode() {

        return gameMode;
    }

    public CardColor getColor() {

        return color;
    }

    public int getTrumpValue() {

        if (gameMode != GameMode.SUIT) {

            return gameMode.getModeValue();

        } else {

            return color.getCardColorValue();
        }
    }

    /* SETTER */

    public void setGameMode(GameMode gameMode) {

        this.gameMode = gameMode;
    }

    public void setColor(CardColor color) {

        this.color = color;
    }

    /* OTHER */

    public boolean isTrump(Card card) {

        return switch ( gameMode ) {

            case SUIT -> card.getCardValue() == CardValue.JACK || card.getCardColor() == color;
            case GRAND -> card.getCardValue() == CardValue.JACK;
            case NULL -> false;
        };
    }

    public JSONObject toJSON(){

        var obj = new JSONObject();

        obj.put(GAMEMODE_KEY, gameMode.toString());

        if ( gameMode == GameMode.SUIT ){

            obj.put(COLOR_KEY, color.toString());

        }

        return obj;

    }


}








