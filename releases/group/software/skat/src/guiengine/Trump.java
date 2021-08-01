package guiengine;

import guiengine.enums.CardColor;
import guiengine.enums.CardValue;
import guiengine.enums.GameMode;
import org.json.simple.JSONObject;

/**
 * class for trump
 */
public class Trump {

    private static final String GAMEMODE_KEY = "gameMode";
    private static final String COLOR_KEY = "color";

    private GameMode mGameMode;
    private CardColor mColor;

    /* CONSTRUCTOR */

    /**
     * empty constructor
     */
    public Trump() {

    }

    /**
     * constructor for grand and null games
     * @param mode mode
     */
    public Trump(GameMode mode) {

        mGameMode = mode;
    }

    /**
     * constructor for suit games
     * @param color color to be trump in the playing phase
     */
    public Trump(CardColor color) {

        mGameMode = GameMode.SUIT;
        this.mColor = color;
    }

    /**
     * constructor from json-object
     * @param obj jsonobject
     */
    public Trump(JSONObject obj) {

        mGameMode = GameMode.valueOf((String) obj.get(GAMEMODE_KEY));

        if ( mGameMode == GameMode.SUIT ) {

            mColor = CardColor.valueOf((String) obj.get(COLOR_KEY));

        }

    }

    /* GETTER */

    /**
     * @return game mode
     */
    public GameMode getGameMode() {

        return mGameMode;
    }

    /**
     * @return trump color
     */
    public CardColor getColor() {

        return mColor;
    }

    /**
     * @return value of trump, needed to calculate game value
     */
    public int getTrumpValue() {

        if (mGameMode != GameMode.SUIT) {

            return mGameMode.getModeValue();

        } else {

            return mColor.getCardColorValue();
        }
    }

    /* SETTER */

    public void setGameMode(GameMode gameMode) {

        this.mGameMode = gameMode;
    }

    public void setColor(CardColor color) {

        this.mColor = color;
    }

    /* OTHER */

    /**
     * @param card card
     * @return true if card is trump, false if not
     */
    public boolean isTrump(Card card) {

        return switch (mGameMode) {

            case SUIT -> card.getCardValue() == CardValue.JACK || card.getCardColor() == mColor;
            case GRAND -> card.getCardValue() == CardValue.JACK;
            case NULL -> false;
        };
    }

    /**
     * @return trump as jsonobject
     */
    public JSONObject toJSON() {

        var obj = new JSONObject();

        obj.put(GAMEMODE_KEY, mGameMode.toString());

        if ( mGameMode == GameMode.SUIT ) {

            obj.put(COLOR_KEY, mColor.toString());

        }

        return obj;

    }


}








