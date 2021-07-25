package moves;

import helper.QuickJSon;
import org.json.simple.JSONObject;

public enum MoveType {
    ROLL_DICES, OPTIONAL;

    public JSONObject getJSon() {
        return QuickJSon.create("type", this.toString());
    }
}
