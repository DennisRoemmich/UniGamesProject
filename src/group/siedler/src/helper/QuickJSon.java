package helper;

import org.json.simple.JSONObject;

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
}
