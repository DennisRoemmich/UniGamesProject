package engine;

import org.json.simple.JSONObject;

import java.util.Optional;

/**
 * GameOwner handles incoming moves.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public interface GameOwner {
    Optional<Chess> getGame();
    
    void addMoveToQueue(JSONObject move);
}
