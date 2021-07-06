package controller;

import controller.enums.ActionType;
import engine.Trump;
import org.json.simple.JSONObject;

public class SkatMove extends GameMove {

    private static final String TYPE_KEY = "type";
    private static final String INDEXFROM_KEY = "indexFrom";
    private static final String INDEXTO_KEY = "indexTo";
    private static final String TRUMP_KEY = "trump";
    private static final String TOBERELOGGED_KEY = "toBeRelogged";



    private int indexFrom;
    private int indexTo;
    /** This value is used to be able to play moves from log without logging them again but actually it shouldn't be that way*/
    private boolean toBeRelogged = false;

   // private ActionType type;
    public Trump trump;


    /* CONSTRUCTOR */

    public SkatMove(JSONObject obj) {

        type = ActionType.valueOf((String) obj.get(TYPE_KEY));
        toBeRelogged = (boolean) obj.get(TOBERELOGGED_KEY);

        if (type.usesIndices() ) {

            indexFrom = Integer.parseInt((String) obj.get(INDEXFROM_KEY));
            indexTo = Integer.parseInt((String) obj.get(INDEXTO_KEY));

        }

        if ( type == ActionType.SET_TRUMP ) {

            trump = new Trump(obj);

        }

    }

    public SkatMove(ActionType type) {

        this.type = type;

    }

    public SkatMove(int from) {

        this.type = ActionType.PLAY_CARD;
        indexFrom = from;

    }

    public SkatMove(ActionType type, int from, int to) {

        this.type = type;
        this.indexFrom = from;
        this.indexTo = to;

    }

    public SkatMove(Trump trump) {

        this.type = ActionType.SET_TRUMP;
        this.trump = trump;

    }


    /* GETTER */

    public int getIndexFrom() {

        return indexFrom;
    }

    public int getIndexTo() {

        return indexTo;
    }

    /* OTHER */

    @Override
    public JSONObject toJSON(){

        var obj = new JSONObject();

        obj.put(TYPE_KEY, type.toString());
        obj.put(TOBERELOGGED_KEY, Boolean.toString(toBeRelogged));

        if ( type.usesIndices() ) {

            obj.put(INDEXFROM_KEY, Integer.toString(indexFrom) );
            obj.put(INDEXTO_KEY, Integer.toString(indexTo));

        }

        if ( type == ActionType.SET_TRUMP ) {

            obj.put(TRUMP_KEY,  trump.toJSON());

        }


        return obj;

    }

}







