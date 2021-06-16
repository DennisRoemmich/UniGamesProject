package framework;

import org.json.simple.JSONObject;
import java.util.List;

public abstract class GameController {
	private GameLog mGameLog;
	protected List<Player> mPlayers;
	
	protected GameController() {
		mGameLog = new GameLog("game1");
	}

	public abstract void executeMove(JSONObject move);
	//public abstract void newGame();
	//public abstract JSONObject metaSettingsToJSON();
	//public abstract JSONObject gameSettingsToJSON();
	//public abstract void loadGame(GameLog gameLog);



	/*public final void undoLastMove() {
		undoLastMoves(1);
	}

	public final void undoLastMoves(int amount) {
		gameLog.removeLastMoves(amount);
		loadGame(gameLog);
		executeMoves(gameLog.getMoveLog());
	}*/

	public void logMove(JSONObject move) {
		mGameLog.logMove(move);
		saveGame();
	}

	public final void executeMoves(List<JSONObject> moves) {
		for (JSONObject move : moves) {
			executeMove(move);
		}
	}

	public final void saveGame() {
		if (mGameLog != null) {
			FileController.saveJSon(mGameLog.getCompleteJSonObject(), mGameLog.getID());
		}
	}

	public void newGameLog() {

		mGameLog = new GameLog("abc");

		// ? save old GameLog Object

		// create new ID with timestamp
		// get settings JSON
		// -> create new GameLog object
		// this.moveLog = newLog;

	}



}
