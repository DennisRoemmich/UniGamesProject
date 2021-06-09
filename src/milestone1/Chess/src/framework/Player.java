package framework;

import org.json.simple.JSONObject;

public interface Player {
	JSONObject requestMove(JSONObject inputType);
}