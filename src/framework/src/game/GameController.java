package game;

import java.util.List;

public abstract class GameController {
	private GameLog _log;
	private List<Player> _player;

	public GameController() {
		throw new UnsupportedOperationException();
	}

	public GameController(java.util.List<JSONObject> aMoves) {
		throw new UnsupportedOperationException();
	}

	public final void undoLastMove() {
		throw new UnsupportedOperationException();
	}

	public final void undoLastMoves(int aAmount) {
		throw new UnsupportedOperationException();
	}

	public abstract void executeMove(JSONObject aMove);

	/**
	 * @param aMoves The moves that shall be executed
	 */
	public final void executeMoves(java.util.List<JSONObject> aMoves) {
		System.out.println("Test");
	}

	public void resetLog() {
		throw new UnsupportedOperationException();
	}
}