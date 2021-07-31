package npc;

import engine.*;
import engine.analysis.GameOverDetector;
import engine.board.ChessMove;
import engine.pieces.ChessPiece;
import engine.pieces.PositionedPiece;
import framework.PrintToConsole;
import framework.TimeKeeper;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public final class AiRatingEngine implements Runnable {

    private AiRatingTask mTask;
    private AiRatingStorage ratingStorage;

    public AiRatingEngine(AiRatingTask task, AiRatingStorage ratingStorage) {
        this.mTask = task;
        this.ratingStorage = ratingStorage;
    }

    public AiRatingEngine(AiRatingTask task) {
        this.mTask = task;
        this.ratingStorage = new AiRatingStorage();
    }

    public AiRatingEngine() {
        this.ratingStorage = new AiRatingStorage();
    }

    @Override
    public void run() {
        executeTaks(mTask);
    }

    private void executeTaks(AiRatingTask task) {
        var move = task.getMoveToRate();

        if (task.getDepth() < 1 || task.getTimeout() < 1) {
            return;
        }

        double rating;
        AiRatingResult result;
        if (move.isPresent()) {
            rating = rateMoveRecursively(task.getGame(), move.get(), task.getDepth(), task.getTimeout());
            result = new AiRatingResult(task.getGame(), move.get(), rating);
        } else {
            rating = getRatingFromStorage(task.getGame(), task.getDepth());
            result = new AiRatingResult(task.getGame(), rating);
        }
        task.getReplyQueue().add(result);
    }


    public ChessMove getBestMove(Chess game, int depth, long maxTime) {
        boolean isWhite = game.getCurrentColor().isWhite();

        var possibleMoves = new LinkedList<>(game.getPossibleMoves());

        Optional<ChessMove> bestMove = Optional.empty();
        double bestRating = 0;

        for (ChessMove move : possibleMoves) {
            var rating = rateMoveRecursively(game, move, depth, maxTime);

            if (bestMove.isEmpty() || (isWhite ? (rating > bestRating) : (rating < bestRating))) {
                bestRating = rating;
                bestMove = Optional.of(move);
            }

            PrintToConsole.println("@move " + move + " was rated with " + rating);
        }

        PrintToConsole.println("Best move is " + bestMove + " with " + bestRating);
        return bestMove.get();
    }

    private double rateMove(Chess game, ChessMove move) {
        return rateMoveRecursively(game, move, 1, 5000L);
    }

    private double rateMoveRecursively(Chess game, ChessMove move, int depth, long maxTime) {
        PrintToConsole.println("Rating " + move + " @depth " + depth);
        Chess gameClone = new Chess(game);
        double rating = rateSituation(game);
        gameClone.makeMove(move);
        rating = getRatingFromStorage(gameClone, depth - 1) - rating;
        return rating;
    }

    public double rateSituation(Chess game) {
        switch (GameOverDetector.checkForMate(game)) {
            case CHECKMATE -> {
                return 100000 * game.getCurrentColor().getContrary().getScoreFactor();
            }
            case NONE -> {
                double pieceRating = getPieceValueRating(game);
                double positionRating = getPositionRating(game);
                return pieceRating + positionRating;
            }
            default -> {
                return 0;
            }
        }
    }

    public double rateSituationRecursively(Chess game, int depth, long maxTime) {
        PrintToConsole.println("Rating move " + game.getCurrentMove() + " of " + game.getCurrentColor() + " @depth " + depth);

        if (depth <= 1) {
            return rateSituation(game);
        } else {
            double rating = rateSituation(game);
            var possibleMoves = game.getPossibleMoves();
            for (ChessMove move : possibleMoves) {
                Chess gameClone = new Chess(game);
                gameClone.makeMoveWithoutValidation(move);
                var storedRating = getRatingFromStorage(gameClone, depth - 1) / possibleMoves.size();
                rating += storedRating;
            }
            return rating;
        }
    }

    public static double getPieceValueRating(Chess game) {
        double rating = 0;
        for (ChessPiece piece : game.getBoard().getPieces()) {
            rating += piece.getSignedValue();
        }
        return rating * 5;
    }

    public static double getPositionRating(Chess game) {
        double rating = 0;
        for (PositionedPiece piece : game.getBoard().getPositionedPieces()) {
            rating += SquareRating.rate(piece) * piece.getPiece().getColor().getScoreFactor();
        }
        return rating;
    }

    private double getRatingFromStorage(Chess game, int depth) {
        if (ratingStorage.isRatingStored(game, depth)) {
            return ratingStorage.getRating(game, depth);
        } else {
            var rating = rateSituationRecursively(game, depth, 5000L);
            ratingStorage.addRating(game, rating, depth);
            return rating;
        }
    }
}
