package Main;

import org.json.simple.JSONObject;

import java.util.List;
import java.util.ListIterator;

public abstract class GameController {
	private GameLog moveLog;
	private List<Player> players;

	public abstract void executeMove(JSONObject move);

	public abstract void resetGame();

	public abstract void addAIPlayer();

	public abstract JSONObject metaSettingsToJSON();
	public abstract JSONObject gameSettingsToJSON();

	public GameController() {

	}

	public final void undoLastMove() {
		undoLastMoves(1);
	}

	public final void undoLastMoves(int amount) {
		moveLog.removeLastMoves(amount);
		resetGame();
		executeMoves(moveLog.getMoveLog());
	}

	public final void executeMoves(List<JSONObject> moves) {
		for(JSONObject move : moves) {
			executeMove(move);
		}
	}

	public void newGameLog(){

		// ? save old GameLog Object

		// create new ID with timestamp
		// get settings JSON
		// -> create new GameLog object
		// this.moveLog = newLog;

	}



}