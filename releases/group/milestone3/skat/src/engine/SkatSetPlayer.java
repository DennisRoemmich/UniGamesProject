package engine;

public class SkatSetPlayer {

    private String name;
    private int totalScore;

    private boolean isPlaying;
    private SkatPlayer skatPlayer;

    /* CONSTRUCTOR */

    public SkatSetPlayer(String name) {

        this.name = name;
        totalScore = 0;

        isPlaying = false;
    }

    /* GETTER */

    public String getName() {

        return name;
    }

    public int getTotalScore() {

        return totalScore;
    }

    /* SETTER */

    public void setPlaying(boolean b) {

        isPlaying = b;
    }

    public void setSkatPlayer(SkatPlayer player) {

        skatPlayer = player;
    }

    public void addToScore(int score) {

        totalScore += score;
    }
}
