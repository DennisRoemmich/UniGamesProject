package siedlerFramework;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameLog {
	private final String mId;
	ArrayList<JSONObject> mMoveLog = new ArrayList<>();
	private final JSONObject mMetaSettings;
	private final JSONObject mGameSettings;

	public static String idKey = "id";
	public static String moveLogKey = "moveLog";
	public static String metaSettingsKey = "metaSettings";
	public static String gameSettingsKey = "gameSetttings";

	public GameLog(String id) {
		this.mId = id;
		this.mMetaSettings = new JSONObject();
		this.mGameSettings = new JSONObject();
		this.mMoveLog = new ArrayList<>();
	}

	public GameLog(String id, JSONObject metaSettings, JSONObject gameSettings, List<JSONObject> moveLog) {
		this.mId = id;
		this.mMetaSettings = metaSettings;
		this.mGameSettings = gameSettings;
		this.mMoveLog = (ArrayList<JSONObject>) moveLog;
	}

	public static GameLog valueOf(JSONObject log) {
		String id = (String) log.get(idKey);
		JSONObject metaSettings = (JSONObject) log.get(metaSettingsKey);
		JSONObject gameSettings = (JSONObject) log.get(gameSettingsKey);
		List<JSONObject> moveLog = (List<JSONObject>) log.get(moveLogKey);
		return new GameLog(id, metaSettings, gameSettings, moveLog);
	}

	public void logMove(JSONObject aMove) {
		mMoveLog.add(aMove);
		
	}

	public void removeLastMove() {
		mMoveLog.remove(mMoveLog.size() - 1);
	}

	public void removeLastMoves(int aAmount) {
		for (int i = 0; i < aAmount; i++ ) {
			removeLastMove();
		}
 	}

	public String getID() {
		return mId;
	}

	public List<JSONObject> getMoveLog() {
		return this.mMoveLog;
	}

	public JSONObject getMetaSettings() {
		return mMetaSettings;
	}

	public JSONObject getGameSettings() {
		return mGameSettings;
	}

	public JSONObject getCompleteJSonObject() {
		HashMap<String, Object> rawComplete = new HashMap<>();
		rawComplete.put(moveLogKey, mMoveLog);
		rawComplete.put(gameSettingsKey, mGameSettings);
		rawComplete.put(metaSettingsKey, mMetaSettings);
		return new JSONObject(rawComplete);
	}

}
