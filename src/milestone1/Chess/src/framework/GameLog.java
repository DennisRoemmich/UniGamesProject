package framework;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.List;

public class GameLog {
	private String mId;
	private JSONArray mMoveLog = new JSONArray();
	private JSONObject mMetaSettings;
	private JSONObject mGameSettings;

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
		JSONObject complete = new JSONObject();
		complete.put("moveLog", mMoveLog);
		complete.put("gameSetting", mGameSettings);
		complete.put("metaSetting", mMetaSettings);
		return complete;
	}

}
