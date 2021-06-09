package sample;

import org.json.simple.JSONObject;

/**
 * UI object interface that represents the player's moves.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public interface Player {
    JSONObject requestMove();
}
