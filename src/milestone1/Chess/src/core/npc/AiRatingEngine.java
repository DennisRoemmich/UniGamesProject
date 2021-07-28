package core.npc;

import core.*;
import core.pieces.ChessPiece;
import core.positioning.Square;
import framework.CallCounter;
import framework.PrintToConsole;
import framework.TimeKeeper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AiRatingEngine {
    public static ChessMove getBestMove(Chess game, int depth, long maxTime) {
        boolean isWhite = game.getCurrentColor().isWhite();

        var possibleMoves =  new LinkedList<>(game.getPossibleMoves());
        Collections.shuffle(possibleMoves);
        ChessMove bestMove = possibleMoves.pop();
        double bestScore = rateMoveRecursively(game, bestMove, depth, maxTime);

        for(ChessMove move : possibleMoves) {
            double rating = AiRatingEngine.rateMoveRecursively(game, move, depth, maxTime);
            if(isWhite ? (rating > bestScore) : (rating < bestScore)) {
                bestMove = move;
                bestScore = rating;
            }
            if(TimeKeeper.isTimePointOver(maxTime)) {
                return bestMove;
            }
        }

        return bestMove;
    }

    public static double rateMove(Chess game, ChessMove move) {
        Chess gameClone = new Chess(game);
        double rating = -1 * rateSituation(gameClone);
        gameClone.makeMoveWithoutValidation(move);
        rating += rateSituation(gameClone);
        return rating;
    }

    public static double rateMoveRecursively(Chess game, ChessMove move, int depth, long maxTime) {
        double rating = rateMove(game, move);
        if(depth > 1) {
            Chess gameClone = new Chess(game);
            gameClone.makeMove(move);
            rating += rateSituationRecursively(game, depth - 1, maxTime);
        }
        return rating;
    }

    public static double rateSituation(Chess game) {
        return switch(GameOverDetector.checkForMate(game)) {
            case CHECKMATE -> 100000 * game.getCurrentColor().getContrary().getScoreFactor();
            case NONE -> getPieceValueRating(game) + getPositionRating(game);
            default -> 0;
        };
    }

    public static double rateSituationRecursively(Chess game, int depth, long maxTime) {
        double rating = rateSituation(game);
        if(depth > 1) {
            var possibleMoves = game.getPossibleMoves();
            Collections.shuffle(possibleMoves);
            for(ChessMove nextMove : possibleMoves) {
                rating += rateMoveRecursively(game, nextMove, depth, maxTime);
                if(TimeKeeper.isTimePointOver(maxTime)) {
                    return rating;
                }
            }
        }
        return rating;
    }

    public static double getPieceValueRating(Chess game) {
        double rating = 0;
        for(ChessPiece piece : game.getBoard().getPieces()) {
            rating += piece.getSignedValue() * 10;
        }
        return rating;
    }

    public static double getPositionRating(Chess game) {
        double rating = 0;
        for(PositionedPiece piece : game.getBoard().getPositionedPieces()) {
            rating += SquareRating.rate(piece) * piece.getPiece().getColor().getScoreFactor();
        }
        return rating;
    }
}
