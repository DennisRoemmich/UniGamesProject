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

	public abstract void executeMove(JSONObject move);

	public void callPresenterUpdate() {
		if (mPresenter != null) {
			mPresenter.refreshOutput();
		}
	}

	/* Logs & Settings */

	public abstract JSONObject metaSettingsToJSon();

	public abstract JSONObject gameSettingsToJSon();

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

	public final void replayLog(GameLog log) {
		newGame();
		restoreMetaSettings(log.getMetaSettings());
		restoreGameSettings(log.getGameSettings());
		for (JSONObject move : log.getMoveLog()) {
			executeMove(move);
			callPresenterUpdate();
		}
		mGameLog = log;
	}

	public final void undoLastMove() {
		undoLastMoves(1);
	}

	public final void undoLastMoves(int amount) {
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
	@SuppressWarnings("unchecked")
	protected JSONObject createReply(boolean isValid, String replyID) {
		JSONObject failReply = new JSONObject();
		failReply.put("isValid", isValid);
		failReply.put("replyID", replyID);
		return failReply;
	}

	// Also not requied to be used, just for convenience
	@SuppressWarnings("unchecked")
	protected JSONObject createRequestJSon(String typeName) {
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
