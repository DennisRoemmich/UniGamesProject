package main;

import rummikub_game.Board;
import rummikub_game.Rack;
import rummikub_game.Rummikub;
import rummikub_game.Tile;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        // create rummikub game

        var rummi = new Rummikub(4, 0);

        // testing

        test(rummi);
    }

    private static void test(Rummikub rummi) {

        // draw rack of players after handout

        for (var p = 0; p < 4; p++) {
            System.out.println("Rack of Player " + (p+1) + ":");
            printPlayerRack(rummi, p);
            newLine(1);

/**     // draw each sorted players rack

        rummi.getPlayerAt(p).getRack().sortForGroup();
        printPlayerRack(rummi, p);

        rummi.getPlayerAt(p).getRack().sortForRun();
        printPlayerRack(rummi, p);

        newLine(1);
        //**/
        }

        // move a tile from current players sketchRack to sketchBoard position (4,4)

        rummi.moveTileFromCurrentRackToBoard(new Point(0,0), new Point(4,4));
        rummi.moveTileFromCurrentRackToBoard(new Point(0,1), new Point(4,5));
        rummi.moveTileFromCurrentRackToBoard(new Point(0,2), new Point(4,6));

        newLine(1);

        // draw the board

        printBoard(rummi);

        newLine(1);

        // check board and print rack

        System.out.print("Is valid? : " + rummi.getSketchBoard().isValid());
        newLine(2);

        printPlayerSketchRack(rummi, 0);

        rummi.getPlayerAt(0).getSketchRack().sortForRun();

        printPlayerSketchRack(rummi, 0);
    }

    private static void newLine(int n) {

        for (var i = 0; i < n; i++) {

            System.out.println();
        }
    }

    private static void printPlayerRack(Rummikub rummi, int player) {

        for (var i = 0; i < Rack.GRID_HEIGHT; i++) {
            for (var o = 0; o < Rack.GRID_WIDTH; o++) {
                var point = new Point(i, o);
                System.out.print(rummi.getPlayerAt(player).getRack().pointToGridTile(point).toString());
            }
        //    newLine(1);
        }
        newLine(1);
    }

    private static void printPlayerSketchRack (Rummikub rummi,int player){

        for (var i = 0; i < Rack.GRID_HEIGHT; i++) {
            for (var o = 0; o < Rack.GRID_WIDTH; o++) {
                var point = new Point(i, o);
                System.out.print(rummi.getPlayerAt(player).getSketchRack().pointToGridTile(point).toString());
            }
        //    newLine(1);
        }
        newLine(1);
    }

    private static void printBoard (Rummikub rummi) {

        for(int i = 0; i < Board.GRID_HEIGHT; i++){
            for(int o = 0; o < Board.GRID_WIDTH; o++){
                Point point = new Point(i, o);
                System.out.print(rummi.getSketchBoard().getGridTileAt(point).toString());
            }
            newLine(1);
        }
    }
}
