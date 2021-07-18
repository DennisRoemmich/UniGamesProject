package helper;

import org.json.simple.JSONObject;

public class QuickJSON {

    public static JSONObject create(String key, String value) {
        JSONObject object = new JSONObject();
        object.put(key, value);
        return object;
    }

}
