package framework;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GameLog {

	private String mId;
	private JSONArray mMoveLog = new JSONArray();
	private JSONObject mMetaSettings;
	private JSONObject mGameSettings;


	public static final String idKey = "id";
	public static final String metaSettingsKey = "metaSettings";
	public static final String gameSettingsKey = "gameSettings";
	public static final String moveLogKey = "moveLog";

	public GameLog(String id) {
		this.mId = id;
		this.mMetaSettings = new JSONObject();
		this.mGameSettings = new JSONObject();
		this.mMoveLog = new JSONArray();
	}

	public GameLog(String id, JSONObject metaSettings, JSONObject gameSettings, JSONArray moves) {
		this.mId = id;
		this.mMetaSettings = metaSettings;
		this.mGameSettings = gameSettings;
		this.mMoveLog = moves;
	}

	public GameLog(GameLog log) {
		this.mId = log.mId;
		this.mGameSettings = log.mGameSettings;
		this.mMetaSettings = log.mMetaSettings;
		this.mMoveLog = log.mMoveLog;
	}
	public GameLog(JSONObject completeJSon) {
		this.mId = (String) completeJSon.get(idKey);
		this.mMetaSettings = (JSONObject) completeJSon.get(metaSettingsKey);
		this.mGameSettings = (JSONObject) completeJSon.get(gameSettingsKey);
		this.mMoveLog = (JSONArray) completeJSon.get(moveLogKey);
	}

	public void logMove(JSONObject aMove) {
		mMoveLog.add(aMove);
	}

	public void resetMoveLog() {
		mMoveLog = new JSONArray();
	}

	public void removeLastMove() {
		if(mMoveLog.isEmpty()) return;
		mMoveLog.remove(mMoveLog.size() - 1);
	}

	public void removeLastMoves(int aAmount) {
		for(int i = 0; i < aAmount; i++ ) {
			removeLastMove();
		}
 	}

	public String getID() {
		return mId;
	}

	public JSONArray getMoveLog() {
		return this.mMoveLog;
	}

	public JSONObject getMetaSettings() {
		return mMetaSettings;
	}

	public JSONObject getGameSettings() {
		return mGameSettings;
	}

	public JSONObject getCompleteJSONObject(){
		JSONObject complete = new JSONObject();
		complete.put("moveLog", mMoveLog);
		complete.put("gameSetting", mGameSettings);
		complete.put("metaSetting", mMetaSettings);
		return complete;
	}

}