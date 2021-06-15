package framework;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.ListIterator;

public abstract class GameController {
	private GameLog gameLog;
	protected List<Player> players;

	/**
	 * Reset all game data
	 */
	public abstract void newGame();
	/**
	 * Every call of executeMove with a valid move should lead to a call of logMove()
	 * @param move: Move as JSON Object
 	 */
	public abstract void executeMove(JSONObject move);
	public abstract JSONObject metaSettingsToJSON();
	public abstract JSONObject gameSettingsToJSON();

	public void loadGame(GameLog gameLog) {
		newGame();
		this.gameLog = new GameLog(gameLog);
		this.gameLog.resetMoveLog();
		for(Object move : gameLog.getMoveLog()) {
			executeMove((JSONObject) move);
		}
	}

	public GameController() {
		gameLog = new GameLog("game1");
	}

	public final void undoLastMove() {
		undoLastMoves(1);
	}

	public final void undoLastMoves(int amount) {
		gameLog.removeLastMoves(amount);
		loadGame(gameLog);
		executeMoves(gameLog.getMoveLog());
	}

	public void logMove(JSONObject move) {
		if(gameLog != null) {
			gameLog.logMove(move);
			saveGame();
		}
	}

	public final void executeMoves(JSONArray moves) {
		for(Object move : moves) {
			executeMove((JSONObject) move);
		}
	}

	public final void saveGame() {
		if(gameLog != null) {
			FileController.saveJSON(gameLog.getCompleteJSONObject(), gameLog.getID());
		}
	}

	public void newGameLog(){

		gameLog = new GameLog("abc");

		// ? save old GameLog Object

		// create new ID with timestamp
		// get settings JSON
		// -> create new GameLog object
		// this.moveLog = newLog;

	}



}