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

	public GameLog(String id) {
		this.id = id;
		this.metaSettings = new JSONObject();
		this.gameSettings = new JSONObject();
		this.moveLog = new JSONArray();
	}

	public GameLog(String id, JSONObject metaSettings, JSONObject gameSettings, JSONArray moves) {
		this.id = id;
		this.metaSettings = metaSettings;
		this.gameSettings = gameSettings;
		this.moveLog = moves;
	}

	public GameLog(GameLog log) {
		this.id = log.id;
		this.gameSettings = log.gameSettings;
		this.metaSettings = log.metaSettings;
		this.moveLog = log.moveLog;
	}

	public void logMove(JSONObject aMove) {
		moveLog.add(aMove);
	}

	public void resetMoveLog() {
		moveLog = new JSONArray();
	}

	public void removeLastMove() {
		if(moveLog.isEmpty()) return;
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

	public JSONArray getMoveLog() {
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