package core.npc;

import console.Controller;
import core.Chess;
import core.ChessMove;
import core.Color;
import framework.Player;
import framework.PrintToConsole;
import framework.TimeKeeper;
import org.json.simple.JSONObject;

import java.util.stream.Collectors;

public class AiPlayer implements Player {

	protected Controller mController;

	public AiPlayer(Controller controller) {
		this.mController = controller;
	}
	
	//Random dummy AI
	@Override
    public JSONObject requestMove(JSONObject dataType) {
		return getBestMove(1).toJSon();
    }

    protected ChessMove getBestMove(int depth) {
		boolean isWhite = mController.getGame().getCurrentColor().isWhite();

		var possibleMoves =  mController.getGame().getPossibleMoves();
		ChessMove bestMove = possibleMoves.get(0);

		double bestScore = isWhite ? -100000 : 100000;
		int i = 0;
		for(ChessMove move : possibleMoves) {
			double rating = rateMove(mController.getGame(), move, depth);
			if(isWhite ? (rating > bestScore) : (rating < bestScore)) {
				bestMove = move;
				bestScore = rating;
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
		score += game.getBoard().getPieces(Color.WHITE).stream().collect(Collectors.summingDouble(p -> p.getType().getValue()));
		score -= game.getBoard().getPieces(Color.BLACK).stream().collect(Collectors.summingDouble(p -> p.getType().getValue()));
		return score;
	}
}
