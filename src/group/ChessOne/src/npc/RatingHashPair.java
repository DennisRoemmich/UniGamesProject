package npc;

import engine.Chess;

/**
 * Assigns a hash to a rating.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class RatingHashPair {
    private int mGameHash;
    private double mRating;
    private int mDepth = 1;

    public RatingHashPair(int mGameHash, double rating, int depth) {
        this.mGameHash = mGameHash;
        this.mRating = rating;
        this.mDepth = depth;
    }

    public RatingHashPair(Chess game, double rating, int depth) {
        this.mGameHash = game.hashCode();
        this.mRating = rating;
        this.mDepth = depth;
    }

    public int getGameHash() {
        return mGameHash;
    }

    public void setGameHash(int gameHash) {
        this.mGameHash = gameHash;
    }

    public double getRating() {
        return mRating;
    }

    public void setRating(double rating) {
        this.mRating = rating;
    }

    public int getDepth() {
        return mDepth;
    }

    public void setDepth(int depth) {
        this.mDepth = depth;
    }
}
