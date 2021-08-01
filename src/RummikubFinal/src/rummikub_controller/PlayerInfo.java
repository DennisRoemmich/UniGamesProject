package rummikub_controller;

/**
 * this class bundles some info about a player
 */
public class PlayerInfo {

    private String mName;
    private int mLastScore;
    private int mTotalScore;


    public PlayerInfo(String name) {

        this.mName = name;
    }

    public String getName() {

        return mName;
    }

    public int getTotalScore() {

        return mTotalScore;
    }

    public void addToTotalScore(int score) {

        mTotalScore += score;
    }

    public void setLastScore(int score) {

        mLastScore = score;
    }

    public int getLastScore() {

        return mLastScore;
    }
}
