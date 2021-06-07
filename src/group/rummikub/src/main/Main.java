package main;

import rummikub_game.Board;
import rummikub_game.Rack;
import rummikub_game.Rummikub;

import java.awt.*;

public class Main {

    // create rummikub game

    private static Rummikub rummi = new Rummikub(4, 0);

    public static void main(String[] args) {

        // testing

        test();
    }

    private static void test() {

        // draw rack of players after handout

        for (var p = 0; p < 4; p++) {
            System.out.println("Rack of Player " + (p+1) + ":");
            printPlayerRack(p);
            newLine(1);

            // prints every players rack sorted by group and run
        //    testSorting(p);

        }

        // moves tiles from current players sketchRack to sketchBoard position (4,4)
    //    testTileMoving();

        newLine(1);

        // draw the board

        printBoard();

        newLine(1);

        // check board and print rack

        System.out.print("Is valid? : " + rummi.getSketchBoard().isValid());
        newLine(2);

        testMaik1();
    }

    private static void testSorting(int p) {

        rummi.getPlayerAt(p).getRack().sortForGroup();
        printPlayerRack(p);

        rummi.getPlayerAt(p).getRack().sortForRun();
        printPlayerRack(p);

        newLine(1);
    }

    private static void testTileMoving() {

        rummi.moveTileFromCurrentRackToBoard(new Point(0,0), new Point(4,4));
        rummi.moveTileFromCurrentRackToBoard(new Point(0,1), new Point(4,5));
        rummi.moveTileFromCurrentRackToBoard(new Point(0,2), new Point(4,6));

        rummi.moveTileFromCurrentRackToBoard(new Point(0, 6), new Point(8, 3));
    }

    private static void testMaik1() {

        printPlayerSketchRack(0);

        rummi.getPlayerAt(0).getSketchRack().sortForRun();

        printPlayerSketchRack(0);

        if (rummi.finishMove()) {

            printPlayerRack(0);
            rummi.getPlayerAt(0).getRack().sortForRun();
            printPlayerRack(0);
        }

        newLine(2);

        printPlayerRack(1);

        if (rummi.finishMove()) {

            printPlayerRack(1);
            rummi.getPlayerAt(1).getRack().sortForGroup();
            printPlayerRack(1);
        }
    }

    private static void newLine(int n) {

        for (var i = 0; i < n; i++) {

            System.out.println();
        }
    }

    private static void printPlayerRack(int player) {

        for (var i = 0; i < Rack.GRID_HEIGHT; i++) {
            for (var o = 0; o < Rack.GRID_WIDTH; o++) {
                var point = new Point(i, o);
                System.out.print(rummi.getPlayerAt(player).getRack().pointToGridTile(point).toString());
            }
        //    newLine(1);
        }
        newLine(1);
    }

    private static void printPlayerSketchRack (int player){

        for (var i = 0; i < Rack.GRID_HEIGHT; i++) {
            for (var o = 0; o < Rack.GRID_WIDTH; o++) {
                var point = new Point(i, o);
                System.out.print(rummi.getPlayerAt(player).getSketchRack().pointToGridTile(point).toString());
            }
        //    newLine(1);
        }
        newLine(1);
    }

    private static void printBoard () {

        for(int i = 0; i < Board.GRID_HEIGHT; i++){
            for(int o = 0; o < Board.GRID_WIDTH; o++){
                Point point = new Point(i, o);
                System.out.print(rummi.getSketchBoard().getGridTileAt(point).toString());
            }
            newLine(1);
        }
    }
}
