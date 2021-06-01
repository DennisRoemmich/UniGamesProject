package main;

import rummikub_game.Board;
import rummikub_game.Rack;
import rummikub_game.Rummikub;

import java.awt.*;

public class Main {

    public static void main(String[] args) {



        /* SOME TESTING */

        // create rummikub game

        Rummikub rummi = new Rummikub(4, 0);

        // draw rack of players one after handout

        for (int p = 0; p < 4; p++) {
            System.out.println("Rack of Player " + (p+1) + ":");
            for (int i = 0; i < Rack.GRID_HEIGHT; i++) {
                for (int o = 0; o < Rack.GRID_WIDTH; o++) {
                    Point point = new Point(i, o);
                    System.out.print(rummi.getPlayerAt(p).getRack().pointToGridTile(point).toString());
                }
                System.out.println();
            }
        }
        rummi.getPlayerAt(0).getRack().sortForRun();
        rummi.getPlayerAt(1).getRack().sortForRun();
        rummi.getPlayerAt(2).getRack().sortForGroup();
        rummi.getPlayerAt(3).getRack().sortForRun();
        for (int p = 0; p < 4; p++) {
            System.out.println("Rack of Player " + (p+1) + ":");
            for (int i = 0; i < Rack.GRID_HEIGHT; i++) {
                for (int o = 0; o < Rack.GRID_WIDTH; o++) {
                    Point point = new Point(i, o);
                    System.out.print(rummi.getPlayerAt(p).getRack().pointToGridTile(point).toString());
                }
                System.out.println();
            }
        }
/**
        // move a tile from rack to board

        rummi.moveTileFromCurrentRackToBoard(new Point(0,0), new Point(5,5));

        // draw the board

        System.out.println();System.out.println();System.out.println();

        for(int i = 0; i < Board.GRID_HEIGHT; i++){
            for(int o = 0; o < Board.GRID_WIDTH; o++){
                Point point = new Point(i, o);
                System.out.print(rummi.getBoard().getGridTileAt(point).toString());
            }
            System.out.println();
        }
 **/

    }

}
