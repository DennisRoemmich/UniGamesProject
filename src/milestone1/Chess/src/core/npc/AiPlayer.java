package core.npc;

import console.Controller;
import core.*;
import framework.Player;
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
		return AiRatingEngine.getBestMove(mController.getGame(), depth, endTime);
	}
}