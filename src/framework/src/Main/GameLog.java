package Main;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GameLog {
	private String mID;
	private List<JSONObject> mMoveLog = new ArrayList<JSONObject>();
	private JSONObject mMetaSettings;
	private JSONObject mGameSettings;

	public GameLog(String id, JSONObject aMetaSettings, JSONObject aGameSettings, List<JSONObject> aMoves) {
		throw new UnsupportedOperationException();
	}

	public void logMove(JSONObject aMove) {
		mMoveLog.add(aMove);
	}

	public void removeLastMove() {
		mMoveLog.remove(mMoveLog.size() - 1);
	}

	public void removeLastMoves(int aAmount) {
		for(int i = 0; i < aAmount; i++ ) {
			removeLastMove();
		}
 	}

	public String getID() {
		return mID;
	}

	public List<JSONObject> getMoveLog() {
		return this.mMoveLog;
	}

	public JSONObject getmMetaSettings() {
		return mMetaSettings;
	}

	public JSONObject getmGameSettings() {
		return mGameSettings;
	}

}