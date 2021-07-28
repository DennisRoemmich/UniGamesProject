package controller;

import console.enums.ConsoleActionType;
import controller.enums.ActionType;
import engine.Trump;
import org.json.simple.JSONObject;

/**
 * constructor class for all the moves possible in the game logic
 */
public class SkatMove extends GameMove {

    private static final String TYPE_KEY = "type";
    private static final String INDEXFROM_KEY = "indexFrom";
    private static final String INDEXTO_KEY = "indexTo";
    private static final String TRUMP_KEY = "trump";
    private static final String TOBERELOGGED_KEY = "toBeRelogged";


    private int mIndexFrom;
    private int mIndexTo;
    /** This value is used to be able to play moves from log without logging them again but actually it shouldn't be that way*/
    private boolean mToBeRelogged = false;

   // private ActionType type;
    public Trump mTrump;


    /* CONSTRUCTOR */

    /**
     * constructor for json-object-input
     * @param obj jsnobject
     */
    public SkatMove(JSONObject obj) {

        mType = ActionType.valueOf((String) obj.get(TYPE_KEY));
        mToBeRelogged = (boolean) obj.get(TOBERELOGGED_KEY);

        if ( mType.usesIndices() ) {

            mIndexFrom = Integer.parseInt((String) obj.get(INDEXFROM_KEY));
            mIndexTo = Integer.parseInt((String) obj.get(INDEXTO_KEY));

        }

        if ( mType.usesIndex() ) {

            mIndexFrom = Integer.parseInt((String) obj.get(INDEXFROM_KEY));

        }

        if ( mType.usesTrump() ) {

            mTrump = new Trump(obj);

        }

    }

    /**
     * general constructor
     * @param type type
     */
    public SkatMove(ActionType type) {

        this.mType = type;

    }

    /**
     * constructor for card-plays
     * @param from index of played card
     */
    public SkatMove(int from) {

        this.mType = ActionType.PLAY_CARD;
        mIndexFrom = from;

    }

    /**
     * constructor for card-switches (on hand or on skat)
     * @param type move on hand or on skat
     * @param from index of card to move
     * @param to index of target
     */
    public SkatMove(ActionType type, int from, int to) {

        this.mType = type;
        this.mIndexFrom = from;
        this.mIndexTo = to;

    }

    /**
     * constructor for declaring trump
     * @param trump trump
     */
    public SkatMove(Trump trump) {

        this.mType = ActionType.SET_TRUMP;
        this.mTrump = trump;

    }

    /**
     * general constructor for consoleActionTypes
     * @param consType consoleType
     */
    public SkatMove(ConsoleActionType consType) {

        this.mConsoleType = consType;

    }

    /**
     * constructor for card-play in console
     * @param consType consoleActionTypes
     * @param indexSelection index of played card
     */
    public SkatMove(ConsoleActionType consType, int indexSelection) {

        this.mConsoleType = consType;
        this.mIndexFrom = indexSelection;

    }

    /* GETTER */

    public int getIndexFrom() {

        return mIndexFrom;
    }

    public int getIndexTo() {

        return mIndexTo;
    }

    /* OTHER */

    @Override
    public JSONObject toJSON() {

        var obj = new JSONObject();

        obj.put(TYPE_KEY, mType.toString());
        obj.put(TOBERELOGGED_KEY, Boolean.toString(mToBeRelogged));

        if ( mType.usesIndices() ) {

            obj.put(INDEXFROM_KEY, Integer.toString(mIndexFrom) );
            obj.put(INDEXTO_KEY, Integer.toString(mIndexTo));

        }

        if ( mType.usesIndex() ) {

            obj.put(INDEXFROM_KEY, Integer.toString(mIndexFrom) );

        }

        if ( mType.usesTrump() ) {

            obj.put(TRUMP_KEY,  mTrump.toJSON());

        }


        return obj;

    }

}







