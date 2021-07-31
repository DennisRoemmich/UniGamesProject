package npc;

import engine.Chess;

public class RatingHashPair {
    private int mGameHash;
    private double mRating;
    private int depth = 1;

    public RatingHashPair(int mGameHash, double rating, int depth) {
        this.mGameHash = mGameHash;
        this.mRating = rating;
        this.depth = depth;
    }

    public RatingHashPair(Chess game, double rating, int depth) {
        this.mGameHash = game.hashCode();
        this.mRating = rating;
        this.depth = depth;
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
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
