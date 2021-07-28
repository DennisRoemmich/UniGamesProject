package engine;

/**
 * extra class for the result of a game
 */
public class GameResult {

    private SkatPlayer[] mPlayers;
    private int[] mPoints;

    private SkatPlayer mDeclarer;
    private Trump mTrump;

    private boolean mIsAborted;
    private boolean mIsFinished;

    private int mGameValue;

    /* CONSTRUCTOR */

    public GameResult(SkatPlayer[] players, SkatPlayer declarer, Trump trump) {

        this.mPlayers = players;
        mPoints = new int[3];

        this.mDeclarer = declarer;
        this.mTrump = trump;

        mIsAborted = false;
        mIsFinished = false;
    }

    /* GETTER */

    /**
     * @return index of declarer
     */
    public int getDeclarerIndex() {

        for (var i = 0; i < mPlayers.length; i++) {

            if (mPlayers[i] == mDeclarer) {

                return i;
            }
        }
        return -1;
    }

    public Trump getTrump() {

        return mTrump;
    }

    public boolean isAborted() {

        return mIsAborted;
    }

    public boolean isFinished() {

        return mIsFinished;
    }

    public int getGameValue() {

        return mGameValue;
    }

    /* ELSE */

    public void setDeclarer() {

        for (SkatPlayer player : mPlayers) {

            if (player.isDeclarer()) {

                mDeclarer = player;
            }
        }
    }

    /**
     * sets up all infos if game has ended
     */
    public void gameHasEnded() {

        for (var i = 0; i < mPoints.length; i++ ) {

            mPoints[i] = mPlayers[i].getFinalScore();
        }
        mIsFinished = true;
        mGameValue = mDeclarer.getFinalScore();
    }

    /**
     * sets up all infos if game has been abortet
     * @param isAborted true if aborted, false if not
     */
    public void setAborted(boolean isAborted) {

        this.mIsAborted = isAborted;
    }

    /**
     * @return true, if declarer did win, false if not
     */
    public boolean declarerDidWin() {

        return mDeclarer.getFinalScore() > 60;
    }
}
