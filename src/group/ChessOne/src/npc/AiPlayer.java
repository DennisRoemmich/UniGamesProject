package npc;

import engine.Chess;
import engine.Controller;
import engine.board.ChessMove;
import chessframework.Player;
import org.json.simple.JSONObject;

/**
 * Implementation of the AI player
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class AiPlayer implements Player {

	protected Controller mController;
	private static final int DEPTH = 3;
	private AiRatingEngine mRatingEngine = new AiRatingEngine();
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
			return mRatingEngine.getBestMove(gameClone, depth, mShouldPrint);
		}
		throw new IllegalStateException();
	}

	public void setShouldPrint(boolean mShouldPrint) {
		this.mShouldPrint = mShouldPrint;
	}
}
