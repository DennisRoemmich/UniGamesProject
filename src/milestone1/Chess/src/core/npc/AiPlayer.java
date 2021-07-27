package core.npc;

import console.Controller;
import core.Chess;
import core.ChessMove;
import core.Color;
import core.GameOverDetector;
import core.pieces.ChessPiece;
import framework.CallCounter;
import framework.Player;
import framework.PrintToConsole;
import framework.TimeKeeper;
import org.json.simple.JSONObject;

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
			double rating = rateMove(mController.getGame(), move, depth);
			if(isWhite ? (rating > bestScore) : (rating < bestScore)) {
				bestMove = move;
				bestScore = rating;
			}
		}
		return bestMove;
	}

	protected double rateMove(Chess game, ChessMove chessMove, int depth) {
		if(depth == 2) {
			CallCounter.registerCall("rateMove", true);
		}
		PrintToConsole.println("--> Rating " + " @time " + TimeKeeper.timeToString());
		Chess gameClone = new Chess(game);
		PrintToConsole.println("Cloned game @time " + TimeKeeper.timeToString());
		double score = -1 * rateSituation(gameClone);
		PrintToConsole.println("Rated current situation @time " + TimeKeeper.timeToString());
		gameClone.makeMove(chessMove);
		//PrintToConsole.println("Made move @time " + TimeKeeper.timeToString());
		score += rateSituation(gameClone);
		//PrintToConsole.println("Rated situation after move @time " + TimeKeeper.timeToString());
		if(depth > 1) {
			//PrintToConsole.println("Starting recursion loop @time " + TimeKeeper.timeToString());
			var possibleMoves = gameClone.getPossibleMoves();
			double numberOfPossibleMoves = possibleMoves.size();
			for(ChessMove nextMove : possibleMoves) {
				score += rateMove(gameClone, nextMove, depth - 1);
			}
		}
		return score;
	}

    protected double rateSituation(Chess game) {
		double score = 0;
		switch(GameOverDetector.checkForMate(game)) {
			case CHECKMATE:
				score -= 10000 * game.getCurrentColor().getScoreFactor();
				break;
			case STALEMATE, DRAW:
				score = 0;
				break;
			case NONE:
				for(ChessPiece piece : game.getBoard().getPieces()) {
					score += piece.getSignedValue();
				}
				break;
		}
		return score;
	}
}
