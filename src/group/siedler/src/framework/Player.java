package framework;

import org.json.simple.JSONObject;

/**
 * The player interface implemented by the Controller.
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public interface Player {
	JSONObject requestMove(JSONObject inputType);
}
