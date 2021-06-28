package frameworkchess;

import org.json.simple.JSONObject;

public interface Player {
	JSONObject requestMove(JSONObject inputType);
}
