package npc;

import engine.Controller;
import engine.board.ChessMove;
import framework.Player;
import framework.TimeKeeper;
import org.json.simple.JSONObject;

public class AiPlayer implements Player {

	protected Controller mController;
	private static final int depth = 2;

	public AiPlayer(Controller controller) {
		this.mController = controller;
	}

	@Override
    public JSONObject requestMove(JSONObject dataType) {
		return getBestMove(depth).toJSon();
    }

    protected ChessMove getBestMove(int depth) {
		long endTime = System.currentTimeMillis() + 10000L;
		var move = AiRatingEngine.getBestMove(mController.getGame(), depth, endTime);
		//sleep(TimeKeeper.remainingUntilTimePoint(endTime) - 9000);
		return move;
	}

	protected void sleep(long millis) {
		if(millis > 0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}