package core.npc;

import console.Controller;
import core.*;
import core.pieces.ChessPiece;
import core.positioning.Square;
import framework.CallCounter;
import framework.Player;
import framework.PrintToConsole;
import framework.TimeKeeper;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AiPlayer implements Player {

	protected Controller mController;

	public AiPlayer(Controller controller) {
		this.mController = controller;
	}

	//Random dummy AI
	@Override
    public JSONObject requestMove(JSONObject dataType) {
		return getBestMove(2).toJSon();
    }

    protected ChessMove getBestMove(int depth) {
		boolean isWhite = mController.getGame().getCurrentColor().isWhite();

		var possibleMoves =  mController.getGame().getPossibleMoves();
		ChessMove bestMove = possibleMoves.get(0);

		double bestScore = isWhite ? -100000 : 100000;
		int i = 0;
		for(ChessMove move : possibleMoves) {
			double rating = AiRatingEngine.rateMove(mController.getGame(), move, depth);
			if(isWhite ? (rating > bestScore) : (rating < bestScore)) {
				bestMove = move;
				bestScore = rating;
			}
		}
		return bestMove;
	}
}