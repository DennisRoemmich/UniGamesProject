package framework;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public abstract class GameController {
	private GameLog mGameLog;
	protected List<Player> mPlayers;

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

	public final void loadGame(GameLog gameLog) {
		newGame();
		this.mGameLog = new GameLog(gameLog);
		for(Object move : gameLog.getMoveLog()) {
			executeMove((JSONObject) move);
		}
	}

	public GameController() {
		mGameLog = new GameLog("game1");
	}

	public final void undoLastMove() {
		undoLastMoves(1);
	}

	public final void undoLastMoves(int amount) {
		mGameLog.removeLastMoves(amount);
		GameLog newLog = new GameLog(mGameLog);
		loadGame(newLog);
	}

	public void logMove(JSONObject move) {
		if(mGameLog != null) {
			mGameLog.logMove(move);
			saveGame();
		}
	}

	public final void executeMoves(JSONArray moves) {
		for(Object move : moves) {
			executeMove((JSONObject) move);
		}
	}

	public final void saveGame() {
		if(mGameLog != null) {
			FileController.saveJSON(mGameLog.getCompleteJSONObject(), mGameLog.getID());
		}
	}

	public void newGameLog(){

		mGameLog = new GameLog("abc");

		// ? save old GameLog Object

		// create new ID with timestamp
		// get settings JSON
		// -> create new GameLog object
		// this.moveLog = newLog;

	}



}