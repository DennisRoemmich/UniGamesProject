package moves;

import helper.QuickJSon;
import org.json.simple.JSONObject;

/**
 * Defines the two different main move types
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public enum MoveType {
    ROLL_DICES, OPTIONAL;

    public JSONObject getJSon() {
        return QuickJSon.create("type", this.toString());
    }
}
