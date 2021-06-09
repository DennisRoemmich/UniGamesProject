package framework;

import org.json.simple.JSONObject;

import java.util.List;
import java.util.ListIterator;

public abstract class GameController {
	private GameLog gameLog;
	protected List<Player> players;

	public abstract void executeMove(JSONObject move);
	//public abstract void newGame();
	//public abstract JSONObject metaSettingsToJSON();
	//public abstract JSONObject gameSettingsToJSON();
	//public abstract void loadGame(GameLog gameLog);

	public GameController() {
		gameLog = new GameLog("game1");
	}

	/*public final void undoLastMove() {
		undoLastMoves(1);
	}

	public final void undoLastMoves(int amount) {
		gameLog.removeLastMoves(amount);
		loadGame(gameLog);
		executeMoves(gameLog.getMoveLog());
	}*/

	public void logMove(JSONObject move) {
		gameLog.logMove(move);
		saveGame();
	}

	public final void executeMoves(List<JSONObject> moves) {
		for(JSONObject move : moves) {
			executeMove(move);
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