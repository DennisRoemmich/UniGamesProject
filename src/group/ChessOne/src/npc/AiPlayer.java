package npc;

import engine.Chess;
import engine.Controller;
import engine.board.ChessMove;
import framework.Player;
import org.json.simple.JSONObject;

import java.util.concurrent.BlockingQueue;

public class AiPlayer implements Player {

	protected Controller mController;
	private static final int DEPTH = 3;
	private AiRatingEngine ratingEngine = new AiRatingEngine();
	private boolean mShouldPrint = false;

	public AiPlayer(Controller controller) {
		this.mController = controller;
	}

	public AiPlayer(Controller controller, boolean shouldPrint) {
		this.mController = controller;
		this.mShouldPrint = shouldPrint;
	}

    public void requestMove(JSONObject dataType) {
		mController.addMoveToQueue(getBestMove(DEPTH).toJSon());
    }

    protected ChessMove getBestMove(int depth) {
		var game = mController.getGame();
		if (game.isPresent()) {
			Chess gameClone = new Chess(game.get());
			long endTime = System.currentTimeMillis() + 10000L;
			return ratingEngine.getBestMove(gameClone, depth, endTime, mShouldPrint);
		}
		throw new IllegalStateException();
	}

	protected void sleep(long millis) {
		if (millis > 0) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public BlockingQueue<JSONObject> getRequestQueue() {
		return null;
	}

	public void setShouldPrint(boolean mShouldPrint) {
		this.mShouldPrint = mShouldPrint;
	}
}
