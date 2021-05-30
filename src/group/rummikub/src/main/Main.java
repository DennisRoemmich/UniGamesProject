package main;

import rummikub_game.Board;
import rummikub_game.Rack;
import rummikub_game.Rummikub;

import java.awt.*;

public class Main {

    public static void main(String[] args) {



        /* SOME TESTING */

        // create rummikub game

        Rummikub rummi = new Rummikub(4, 1);

        // draw rack of player one after handout

        System.out.println("Rack of Player 2:");
        for(int i = 0; i < Rack.GRIDHEIGHT; i++){
            for(int o = 0; o < Rack.GRIDWIDTH; o++){
                Point point = new Point(i, o);
                System.out.print(rummi.getPlayerAt(1).getRack().pointToGridTile(point).toString());
            }
            System.out.println();
        }

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


    }
}
