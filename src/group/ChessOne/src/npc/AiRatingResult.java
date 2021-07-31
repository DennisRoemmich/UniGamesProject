package npc;

import engine.Chess;
import engine.board.ChessMove;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AiRatingResult {
    private Chess mGame;
    private Optional<ChessMove> mMove;
    private double mRating;
    private boolean isValid = true;

    private AiRatingResult() {
        this.isValid = false;
    }

    public AiRatingResult(Chess mGame, ChessMove move, double rating) {
        this.mGame = mGame;
        this.mMove = Optional.of(move);
        this.mRating = rating;
    }

    public AiRatingResult(Chess mGame, double rating) {
        this.mGame = mGame;
        this.mMove = Optional.empty();
        this.mRating = rating;
    }

    public Chess getGame() {
        return mGame;
    }

    public Optional<ChessMove> getMove() {
        return mMove;
    }

    public double getRating() {
        return mRating;
    }

    public static AiRatingResult getIllegalResult() {
        return new AiRatingResult();
    }

    public boolean isValid() {
        return isValid;
    }

}
