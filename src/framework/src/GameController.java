import org.json.simple.JSONObject;

import java.util.List;

public abstract class GameController {
	private GameLog mLog;
	private List<Player> mPlayer;

	public GameController() {
		throw new UnsupportedOperationException();
	}

	public GameController(List<JSONObject> aMoves) {
		throw new UnsupportedOperationException();
	}

	public final void undoLastMove() {
		throw new UnsupportedOperationException();
	}

	public final void undoLastMoves(int aAmount) {
		throw new UnsupportedOperationException();
	}

	public abstract void executeMove(JSONObject aMove);

	public final void executeMoves(List<JSONObject> aMoves) {
		for(JSONObject move : aMoves) {
			executeMove(move);
		}
	}

	public void resetLog() {
		throw new UnsupportedOperationException();
	}
}