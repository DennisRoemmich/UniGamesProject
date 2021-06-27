package rummikub_controller;

public class PlayerInfo {

    private String name;
    private int lastScore;
    private int totalScore;

    public PlayerInfo() {


    }

    public PlayerInfo(String name) {

        this.name = name;
    }

    public String getName() {

        return name;
    }

    public int getTotalScore() {

        return totalScore;
    }

    public void addToTotalScore(int score) {

        totalScore += score;
    }

    public void setLastScore(int score) {

        lastScore = score;
    }

    public int getLastScore() {

        return lastScore;
    }
}
