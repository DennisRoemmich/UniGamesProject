package guiengine;

/**
 * class for skat players
 */
public class SkatPlayer {

    protected Hand mHand;
    protected Tricks mTricks;

    private boolean mIsBidding;
    private boolean mIsAsking;
    private boolean mIsDeclarer;

    protected int mGameIndex;

    private int mFinalScore;

    /* CONSTRUCTOR */

    public SkatPlayer(Trump trump, int gameIndex) {

        this.mGameIndex = gameIndex;

        mHand = new Hand(trump);
        mTricks = new Tricks();

        mIsBidding = true;
        mIsDeclarer = false;

        mFinalScore = 0;
    }

    /* GETTER */

    /**
     * @return hand of player
     */
    public Hand getHand() {

        return mHand;
    }

    /**
     * @return tricks of player
     */
    public Tricks getTricks() {

        return mTricks;
    }

    /**
     * @return true if player has not already passed in auction, else false
     */
    public boolean isBidding() {

        return mIsBidding;
    }

    /**
     * @return true if player is questioner in auction
     */
    public boolean isAsking() {

        return mIsAsking;
    }

    /**
     * @return true if player has won the auction and is declarer, false if not
     */
    public boolean isDeclarer() {

        return mIsDeclarer;
    }

    /**
     * @return value of all tricks
     */
    public int getTricksScore() {

        return mTricks.getValue();
    }

    /**
     * @return amount of tricks
     */
    public int getTricksAmount() {

        return mTricks.getSize();
    }

    /**
     * @return final score of player
     */
    public int getFinalScore() {

        return mFinalScore;
    }

    /**
     * @return index of player (0-2)
     */
    public int getGameIndex() {

        return mGameIndex;
    }

    /* SETTER */

    public void setBidding(boolean b) {

        mIsBidding = b;
    }

    public void setAsking(boolean b) {

        mIsAsking = b;
    }

    public void setDeclarer(boolean b) {

        mIsDeclarer = b;
    }

    public void setTricks(Tricks tricks) {

        this.mTricks = tricks;
    }

    public void setFinalScore(int score) {

        mFinalScore = score;
    }

    /* OTHER */


}
