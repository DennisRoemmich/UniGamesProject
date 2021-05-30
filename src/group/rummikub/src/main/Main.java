package main;

import rummikub_game.Rack;
import rummikub_game.Rummikub;

import java.awt.*;

public class Main {

    public static void main(String[] args) {



        /* SOME TESTING */

        // create rummikub game

        Rummikub rummi = new Rummikub(4, 1);

        // draw rack of player one after handout

        System.out.println("Rack of Player 1:");
        for(int i = 0; i < Rack.GRIDHEIGHT; i++){
            for(int o = 0; o < Rack.GRIDWIDTH; o++){
                Point point = new Point(i, o);
                System.out.print(rummi.getPlayerAt(0).getRack().pointToGridTile(point).toString());
            }
            System.out.println();
        }



    }
}
