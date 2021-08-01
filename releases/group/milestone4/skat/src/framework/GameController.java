package framework;

import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public abstract class GameController {

	protected GameLog mGameLog;
	protected boolean mIsGameRunning;
	protected List<Player> mPlayers;
	protected Presenter mPresenter;
	
	protected GameController() {
		createNewGameLog();
	}

	protected abstract JSONObject executeMove(JSONObject move);

	public void callPresenterUpdate() {
		if (mPresenter != null) {
			mPresenter.refreshOutput();
		}
	}

	// Das sollte noch umbennent werden denke ich
	// InGame Handling of Moves with automatic logging
	protected final JSONObject handleMove(JSONObject move) {

		JSONObject reply = executeMove(move);
		boolean isMoveValid;
		try {
			isMoveValid  = (boolean) reply.get("isValid");
		} catch (Exception e) {
			// Das sollte höchstens in Entwicklungsphasen auftreten, das try catch kann zum ende also vielleicht weg
			return createReply(false, "Invalid Reply");
		}
		if(isMoveValid) {
			logMove(move);
		}
		return reply;
	}

	/* Logs & Settings */

	public abstract JSONObject metaSettingsToJSON();

	public abstract JSONObject gameSettingsToJSON();

	public abstract void restoreMetaSettings(JSONObject metaSettings);

	public abstract void restoreGameSettings(JSONObject gameSettings);

	public void logMove(JSONObject move) {
		mGameLog.logMove(move);
		saveGame();
	}

	public void createNewGameLog() {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyMMdd_HHmmss");
		LocalDateTime now = LocalDateTime.now();
		mGameLog = new GameLog(dateFormatter.format(now));
	}

	/* Replay & undo */

	// TOD-O : Wartezeit als Parameter
	public final void replayLog(GameLog log) {
		newGame();
		restoreMetaSettings(log.getMetaSettings());
		restoreGameSettings(log.getGameSettings());

		System.out.println("There are " + log.getMoveLog().size() + " Objects in here");

		for (JSONObject move : log.getMoveLog()) {
			executeMove(move);
			callPresenterUpdate();
			// TOD-O : Wait funktion für langsames Schritt für Schritt abspielen
		}
		mGameLog = log;
	}

	public final void undoLastMove() {
		undoLastMoves(1);
	}

	public final void undoLastMoves(int amount) {

		if( amount > mGameLog.getMoveLog().size()){
			return;
		}

		mGameLog.removeLastMoves(amount);
		replayLog(mGameLog);
	}

	/* Game Handling / Meta */

	public abstract void newGame();

	public void quitGame() {
		saveGame(); // Usally it's already saved
		mIsGameRunning = false;
	}

	public final void saveGame() {
		if (mGameLog != null) {
			FileController.saveJSon(mGameLog.getCompleteJSonObject(), mGameLog.getID());
		}
	}

	/* Helper methods */

	// This template offers easy replies with an ID process it in the UI
	// It's not required to be used.
	protected JSONObject createReply(boolean isValid, String replyID) {
		JSONObject failReply = new JSONObject();
		failReply.put("isValid", isValid);
		failReply.put("replyID", replyID);
		return failReply;
	}

	// Also not requied to be used, just for convenience
	protected JSONObject createRequestJSON(String typeName) {
		JSONObject request = new JSONObject();

		request.put("type", typeName);
		return request;
	}

	/* Getter and Setter */

	public Presenter getPresenter() {
		return mPresenter;
	}

	public void setPresenter(Presenter mPresenter) {
		this.mPresenter = mPresenter;
	}
}


/*

	* Wo ist addPlayer(Player player);
	* player.setController , zumindest bei uns benötigt


 */