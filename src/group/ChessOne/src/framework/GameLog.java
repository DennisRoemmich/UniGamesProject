package framework;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * GameLog for the save/load, undo and replay feature.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class GameLog {
	private final String mId;
	ArrayList<JSONObject> mMoveLog = new ArrayList<>();
	private final JSONObject mMetaSettings;
	private final JSONObject mGameSettings;

	public static final String ID_KEY = "id";
	public static final String MOVE_LOG_KEY = "moveLog";
	public static final String META_SETTINGS_KEY = "metaSettings";
	public static final String GAME_SETTINGS_KEY = "gameSettings";

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
		String id = (String) log.get(ID_KEY);
		JSONObject metaSettings = (JSONObject) log.get(META_SETTINGS_KEY);
		JSONObject gameSettings = (JSONObject) log.get(GAME_SETTINGS_KEY);
		@SuppressWarnings("unchecked")
		List<JSONObject> moveLog = (List<JSONObject>) log.get(MOVE_LOG_KEY);
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
		rawComplete.put(MOVE_LOG_KEY, mMoveLog);
		rawComplete.put(GAME_SETTINGS_KEY, mGameSettings);
		rawComplete.put(META_SETTINGS_KEY, mMetaSettings);
		return new JSONObject(rawComplete);
	}

	public JSONObject getLastMove() {
		if (mMoveLog.isEmpty()) {
			return new JSONObject();
		} else {
			return mMoveLog.get(mMoveLog.size() - 1);
		}
	}

}
