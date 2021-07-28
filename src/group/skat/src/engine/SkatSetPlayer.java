package engine;

public class SkatSetPlayer {

    private String mName;
    private int mTotalScore;

    private boolean mIsPlaying;
    private SkatPlayer mSkatPlayer;

    /* CONSTRUCTOR */

    public SkatSetPlayer(String name) {

        this.mName = name;
        mTotalScore = 0;

        mIsPlaying = false;
    }

    /* GETTER */

    public String getName() {

        return mName;
    }

    public int getTotalScore() {

        return mTotalScore;
    }

    /* SETTER */

    public void setPlaying(boolean b) {

        mIsPlaying = b;
    }

    public void setSkatPlayer(SkatPlayer player) {

        mSkatPlayer = player;
    }

    public void addToScore(int score) {

        mTotalScore += score;
    }
}
