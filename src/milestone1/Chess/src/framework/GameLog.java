package framework;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameLog {
	private String mId;
	ArrayList<JSONObject> mMoveLog = new ArrayList<>();
	private JSONObject mMetaSettings;
	private JSONObject mGameSettings;

	public GameLog(String id) {
		this.mId = id;
		this.mMetaSettings = new JSONObject();
		this.mGameSettings = new JSONObject();
		this.mMoveLog = new ArrayList<>();
	}

	public GameLog(String id, JSONObject metaSettings, JSONObject gameSettings, List<JSONObject> moves) {
		this.mId = id;
		this.mMetaSettings = metaSettings;
		this.mGameSettings = gameSettings;
		this.mMoveLog = (ArrayList<JSONObject>) moves;
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
		rawComplete.put("moveLog", mMoveLog);
		rawComplete.put("gameSetting", mGameSettings);
		rawComplete.put("metaSetting", mMetaSettings);
		return new JSONObject(rawComplete);
	}

}
