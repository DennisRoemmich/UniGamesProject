package engine;

import org.json.simple.JSONObject;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;

public interface GameOwner {
    Optional<Chess> getGame();
    BlockingQueue<JSONObject> getMoveQueue();
}
