package main;

import rummikub_game.Rummikub;

public class Main {

    public static void main(String[] args) {

        Rummikub rummi = new Rummikub(4, 1);
        System.out.print(rummi.getCurrentMove());


    }
}
