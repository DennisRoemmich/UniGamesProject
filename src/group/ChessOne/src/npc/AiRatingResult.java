package npc;

import engine.Chess;
import engine.board.ChessMove;

import java.util.Optional;

/**
 * Saves the rating result sorts them to their respective moves.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class AiRatingResult {
    private Chess mGame;
    private Optional<ChessMove> mMove;
    private double mRating;
    private boolean mIsValid = true;

    private AiRatingResult() {
        this.mIsValid = false;
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
        return mIsValid;
    }

}
