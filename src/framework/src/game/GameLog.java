package game;

public class GameLog {
	private List<JSONObject> _moveLog = new ArrayList<JSONObject>();
	private JSONObject _metaSettings;
	private JSONObject _gameSettings;

	public GameLog(JSONObject aMetaSettings, JSONObject aGameSettings, List<JSONObject> aMoves) {
		throw new UnsupportedOperationException();
	}

	public void logMove(JSONObject aMove) {
		throw new UnsupportedOperationException();
	}

	public void removeLastMove() {
		throw new UnsupportedOperationException();
	}

	public void removeLastMoves(int aAmount) {
		throw new UnsupportedOperationException();
	}

	public List<JSONObject> getMoveLog() {
		return this._moveLog;
	}
}