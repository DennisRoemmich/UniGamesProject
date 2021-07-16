package moves;

import helper.QuickJSON;
import org.json.simple.JSONObject;

public enum MoveType {
    ROLL_DICES, OPTIONAL;

    public JSONObject getJSON() {
        return QuickJSON.create("type", this.toString());
    }
}
