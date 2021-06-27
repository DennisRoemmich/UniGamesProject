package rummikub_controller;

public class Player {

    String name;
    int totalPoints = 0;

    Player(String name){

        this.name = name;

    }

    public void addScore(int point){

        totalPoints += point;

    }

}
