package engine;

public class SkatSetPlayer {

    private String mName;
    private int mTotalScore;

    private SkatPlayer mSkatPlayer;

    /* CONSTRUCTOR */

    public SkatSetPlayer(String name) {

        this.mName = name;
        mTotalScore = 0;
    }

    /* GETTER */

    public String getName() {

        return mName;
    }

    public int getTotalScore() {

        return mTotalScore;
    }

    /* SETTER */

    public void setSkatPlayer(SkatPlayer player) {

        mSkatPlayer = player;
    }

    public void addToScore(int score) {

        mTotalScore += score;
    }
}
