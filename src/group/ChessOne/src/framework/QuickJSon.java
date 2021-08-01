package framework;

import org.json.simple.JSONObject;

/**
 * Quick method to create a JSON
 *
 * @author Jan de Boer, Dennis Roemmich
 */
public final class QuickJSon {

    private QuickJSon() {
        //Unused
    }

    @SuppressWarnings("unchecked")
    public static JSONObject create(Object key, Object value) {
        JSONObject object = new JSONObject();
        object.put(key, value);
        return object;
    }


    @SuppressWarnings("unchecked")
    public static JSONObject createReply(String value) {
        JSONObject object = new JSONObject();
        object.put("reply", value);
        return object;
    }
}

