package core.aiplayer;

import console.ConsoleMain;
import console.Controller;
import core.Chess;
import core.ChessMove;
import core.pieces.ChessPiece;
import core.positioning.File;
import core.positioning.Rank;
import core.positioning.Square;
import framework.Player;
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
		ChessMove bestMove = mController.getGame().getPossibleMoves().get(0);
		int colorTwist = 1;//mController.amIplayerWhite(this) ? 1 : -1;
		double bestScore = -100000;
		for(ChessMove move : mController.getGame().getPossibleMoves()) {
			if(rateMove(mController.getGame(), move, depth) * colorTwist > bestScore * colorTwist) {
				bestMove = move;
				bestScore = rateMove(mController.getGame(), move, depth);
			}
		}
		return bestMove;
	}

	protected double rateMove(Chess game, ChessMove chessMove, int depth) {
		Chess gameClone = new Chess(game);
		double score = -1 * rateSituation(gameClone);
		gameClone.makeMove(chessMove);
		score += rateSituation(gameClone);
		if(depth > 1) {
			var possibleMoves = gameClone.getPossibleMoves();
			double numberOfPossibleMoves = possibleMoves.size();
			for(ChessMove nextMove : possibleMoves) {
				score += rateMove(gameClone, nextMove, depth - 1) / numberOfPossibleMoves;
			}
		}
		return score;
	}

    protected double rateSituation(Chess game) {
		double score = 0;


		return score;
	}
}
