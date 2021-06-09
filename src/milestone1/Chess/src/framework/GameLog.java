package framework;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GameLog {
	private String id;
	private JSONArray moveLog = new JSONArray();
	private JSONObject metaSettings;
	private JSONObject gameSettings;

	public GameLog(String id, JSONObject aMetaSettings, JSONObject aGameSettings, List<JSONObject> aMoves) {
		throw new UnsupportedOperationException();
	}

	public void logMove(JSONObject aMove) {
		moveLog.add(aMove);
	}

	public void removeLastMove() {
		moveLog.remove(moveLog.size() - 1);
	}

	public void removeLastMoves(int aAmount) {
		for(int i = 0; i < aAmount; i++ ) {
			removeLastMove();
		}
 	}

	public String getID() {
		return id;
	}

	public List<JSONObject> getMoveLog() {
		return this.moveLog;
	}

	public JSONObject getMetaSettings() {
		return metaSettings;
	}

	public JSONObject getGameSettings() {
		return gameSettings;
	}

	public JSONObject getCompleteJSONObject(){
		JSONObject complete = new JSONObject();
		complete.put("moveLog", moveLog);
		complete.put("gameSetting", gameSettings);
		complete.put("metaSetting", metaSettings);
		return complete;
	}

}