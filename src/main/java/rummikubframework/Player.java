package rummikubframework;

import org.json.simple.JSONObject;

public interface Player {
	JSONObject requestMove(JSONObject inputType);
	void setController(GameController controller);
}
