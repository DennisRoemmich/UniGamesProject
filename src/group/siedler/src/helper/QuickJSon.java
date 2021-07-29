package helper;

import org.json.simple.JSONObject;

/**
 * Quick method to create a JSON
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public final class QuickJSon {
	
	private QuickJSon() {
		//Unused
	}

    @SuppressWarnings("unchecked")
	public static JSONObject create(String key, String value) {
        JSONObject object = new JSONObject();
        object.put(key, value);
        return object;
    }


    public static JSONObject createReply(String value) {
        JSONObject object = new JSONObject();
        object.put("reply", value);
        return object;
    }
}
