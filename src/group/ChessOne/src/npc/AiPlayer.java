package npc;

import engine.Controller;
import engine.board.ChessMove;
import framework.Player;
import org.json.simple.JSONObject;

public class AiPlayer implements Player {

	protected Controller mController;
	private static final int DEPTH = 2;

	public AiPlayer(Controller controller) {
		this.mController = controller;
	}

	@Override
    public JSONObject requestMove(JSONObject dataType) {
		return getBestMove(DEPTH).toJSon();
    }

    protected ChessMove getBestMove(int depth) {
		long endTime = System.currentTimeMillis() + 10000L;
		return AiRatingEngine.getBestMove(mController.getGame(), depth, endTime);
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
}
