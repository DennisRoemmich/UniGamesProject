package src.npc;

import engine.Chess;
import engine.Controller;
import engine.board.ChessMove;
import framework.Player;
import org.json.simple.JSONObject;

import java.util.concurrent.BlockingQueue;

public class AiPlayer implements Player, Runnable {

	protected Controller mController;
	private static final int DEPTH = 2;

	public AiPlayer(Controller controller) {
		this.mController = controller;
	}

    public void requestMove(JSONObject dataType) {
		mController.getMoveQueue().add(getBestMove(DEPTH).toJSon());
    }

    protected ChessMove getBestMove(int depth) {
		var game = mController.getGame();
		if (game.isPresent()) {
			Chess gameClone = new Chess(game.get());
			long endTime = System.currentTimeMillis() + 10000L;
			return AiRatingEngine.getBestMove(gameClone, depth, endTime);
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
	public void run() {

	}

	@Override
	public BlockingQueue<JSONObject> getRequestQueue() {
		return null;
	}
}
