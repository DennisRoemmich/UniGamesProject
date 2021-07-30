package framework;

import org.json.simple.JSONObject;

import java.util.concurrent.BlockingQueue;

public interface Player {
	BlockingQueue<JSONObject> getRequestQueue();
	void setEngineQueue(BlockingQueue<JSONObject> engineQueue);
}
