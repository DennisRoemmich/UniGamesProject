package main;

import rummikub_game.Board;
import rummikub_game.Rack;
import rummikub_game.Rummikub;
import rummikub_game.Tile;

import java.awt.*;

public class Main {

    public static void main(String[] args) {


        /* SOME TESTING */

        // create rummikub game

        var rummi = new Rummikub(4, 0);

        // draw rack of players one after handout

        for (var p = 0; p < 4; p++) {
            System.out.println("Rack of Player " + (p+1) + ":");
            for (var i = 0; i < Rack.GRID_HEIGHT; i++) {
                for (var o = 0; o < Rack.GRID_WIDTH; o++) {
                    var point = new Point(i, o);
                    System.out.print(rummi.getPlayerAt(p).getRack().pointToGridTile(point).toString());
                }
            }
//                                      TESTING FOR SORTING
                System.out.println();

            rummi.getPlayerAt(p).getRack().sortForGroup();
            for (var i = 0; i < Rack.GRID_HEIGHT; i++) {
                for (var o = 0; o < Rack.GRID_WIDTH; o++) {
                    Point point = new Point(i, o);
                    System.out.print(rummi.getPlayerAt(p).getRack().pointToGridTile(point).toString());
                }
            //    System.out.println();
            }
            System.out.println();

            rummi.getPlayerAt(p).getRack().sortForRun();
            for (var i = 0; i < Rack.GRID_HEIGHT; i++) {
                for (var o = 0; o < Rack.GRID_WIDTH; o++) {
                    Point point = new Point(i, o);
                    System.out.print(rummi.getPlayerAt(p).getRack().pointToGridTile(point).toString());
                }
            //    System.out.println();
            }
            System.out.println();
        }

        // move a tile from current players sketchRack to sketchBoard position (4,4)

        rummi.moveTileFromCurrentRackToBoard(new Point(0,0), new Point(4,4));
        rummi.moveTileFromCurrentRackToBoard(new Point(0,1), new Point(4,5));
        rummi.moveTileFromCurrentRackToBoard(new Point(0,2), new Point(4,6));

        // draw the board

        System.out.println();System.out.println();System.out.println();

        for(int i = 0; i < Board.GRID_HEIGHT; i++){
            for(int o = 0; o < Board.GRID_WIDTH; o++){
                Point point = new Point(i, o);
                System.out.print(rummi.getSketchBoard().getGridTileAt(point).toString());
            }
            System.out.println();
        }

        // draw the rack

        System.out.print("\n\n\n");

        for (var i = 0; i < Rack.GRID_HEIGHT; i++) {
            for (var o = 0; o < Rack.GRID_WIDTH; o++) {
                var point = new Point(i, o);
                System.out.print(rummi.getCurrentPlayer().getSketchRack().pointToGridTile(point).toString());
            }
            System.out.println();
        }


        System.out.print("\n\n Is valid? :" + rummi.getSketchBoard().isValid());

    }

}
