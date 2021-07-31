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

    private BlockingQueue<AiRatingTask> mTaskQueue = new LinkedBlockingQueue<>();
    private AiRatingTask mTask;

    public AiRatingEngine(AiRatingTask task) {
        this.mTask = task;
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
            rating = rateSituationRecursively(task.getGame(), task.getDepth(), task.getTimeout());
            result = new AiRatingResult(task.getGame(), rating);
        }
        task.getReplyQueue().add(result);
    }



    public static ChessMove getBestMove(Chess game, int depth, long maxTime) {
        boolean isWhite = game.getCurrentColor().isWhite();

        var possibleMoves =  new LinkedList<>(game.getPossibleMoves());
        ChessMove bestMove = possibleMoves.pop();
        double bestScore = rateMoveRecursively(game, bestMove, depth, maxTime);

        BlockingQueue<AiRatingResult> ratingsQueue = new LinkedBlockingQueue<>();


        for (ChessMove move : possibleMoves) {
            Chess gameClone = new Chess(game);
            AiRatingTask ratingTask = new AiRatingTask(ratingsQueue, gameClone, depth, maxTime, move);
            AiRatingEngine ratingEngine = new AiRatingEngine(ratingTask);
            Thread subEngineThread = new Thread(ratingEngine);
            ratingEngine.getTaskQueue().add(ratingTask);
            subEngineThread.start();
            PrintToConsole.println("New task added @move " + move);
        }

        int handledReplys = 0;
        while (handledReplys < possibleMoves.size()) {
            try {
                AiRatingResult result = ratingsQueue.take();
                PrintToConsole.println("Handle result of task @move " + result.getMove());
                if (isWhite ? (result.getRating() > bestScore) : (result.getRating() < bestScore) ) {
                    bestScore = result.getRating();
                    bestMove = result.getMove().get();
                }
                handledReplys++;
            } catch (InterruptedException e) {
                return bestMove;
            }

        }
        PrintToConsole.println("Best move is " + bestMove + " with " + bestScore);
        return bestMove;
    }

    private static double rateMove(Chess game, ChessMove move) {
        return rateMoveRecursively(game, move, 1, 5000L);
    }

    private static double rateMoveRecursively(Chess game, ChessMove move, int depth, long maxTime) {
        PrintToConsole.println("Rating " + move + " @depth " + depth);
        Chess gameClone = new Chess(game);
        double rating = rateSituationRecursively(gameClone, depth, maxTime);
        gameClone.makeMove(move);
        rating = rateSituationRecursively(gameClone, depth, maxTime);
        return rating;
    }

    public static double rateSituation(Chess game) {
        switch (GameOverDetector.checkForMate(game)) {
            case CHECKMATE -> { return 100000 * game.getCurrentColor().getContrary().getScoreFactor(); }
            case NONE -> {
                double pieceRating = getPieceValueRating(game);
                double positionRating = getPositionRating(game);
                return pieceRating + positionRating;
            }
            default -> { return 0; }
        }
    }

    public static double rateSituationRecursively(Chess game, int depth, long maxTime) {
        PrintToConsole.println("Rating move " + game.getCurrentMove() + " of " + game.getCurrentColor() + " @depth " + depth);
        boolean isWhite = game.getCurrentColor().isWhite();
        double rating = rateSituation(game);
        int handledReplys = 1; // One result was instanly handled
        BlockingQueue<AiRatingResult> resultsQueue = new LinkedBlockingQueue<>();

        if (depth > 1) {
            var possibleMoves = game.getPossibleMoves();
            for (ChessMove move : possibleMoves) {
                rating += 0;//AiRatingEngine.rateMoveRecursively(game, move, depth - 1, )
            }
            while (handledReplys < 7) {
                try {
                    while (resultsQueue.poll() == null) {
                    }
                    AiRatingResult result = resultsQueue.take();
                    if (result.isValid()) {
                        PrintToConsole.println("Handling result for " + result.getMove() + " with rating " + result.getRating() + " @depth " + depth);
                        rating += result.getRating();
                    }
                    handledReplys++;
                } catch (InterruptedException e) {
                    return rating;
                }

            }
        }
        return rating;
    }

    public static double getPieceValueRating(Chess game) {
        double rating = 0;
        for (ChessPiece piece : game.getBoard().getPieces()) {
            rating += piece.getSignedValue() * 10;
        }
        return rating;
    }

    public static double getPositionRating(Chess game) {
        double rating = 0;
        for (PositionedPiece piece : game.getBoard().getPositionedPieces()) {
            rating += SquareRating.rate(piece) * piece.getPiece().getColor().getScoreFactor();
        }
        return rating;
    }

    public BlockingQueue<AiRatingTask> getTaskQueue() {
        return mTaskQueue;
    }
}
