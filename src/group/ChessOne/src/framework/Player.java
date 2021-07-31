package src.framework;

import org.json.simple.JSONObject;

import java.util.concurrent.BlockingQueue;

public interface Player {
	BlockingQueue<JSONObject> getRequestQueue();
	void requestMove(JSONObject moveType);
}
