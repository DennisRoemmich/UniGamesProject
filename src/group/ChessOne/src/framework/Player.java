package framework;

import org.json.simple.JSONObject;

/**
 * Interface for all Player classes und subclasses
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public interface Player {
	void requestMove(JSONObject moveType);
}
