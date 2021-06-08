package Tests;

import rummikub_game.Board;
import rummikub_game.Rack;
import rummikub_game.Rummikub;

import java.awt.*;

public class Test {

    private static Rummikub rummi;

    public Test(){

        rummi = new Rummikub(4, 0);

    }


    public void main(){

    }

    /* MAIK */

    public void maik(){

        printPlayerSketchRack(0);

        rummi.getPlayerAt(0).getSketchRack().sortForRun();

        printPlayerSketchRack(0);

        if (rummi.finishMove()) {

            printPlayerRack(0);
            rummi.getPlayerAt(0).getRack().sortForRun();
            printPlayerRack(0);
        }

        printNewLines(2);

        printPlayerRack(1);

        if (rummi.finishMove()) {

            printPlayerRack(1);
            rummi.getPlayerAt(1).getRack().sortForGroup();
            printPlayerRack(1);
        }

    }

    private void maikSorting(int p){

        rummi.getPlayerAt(p).getRack().sortForGroup();
        printPlayerRack(p);

        rummi.getPlayerAt(p).getRack().sortForRun();
        printPlayerRack(p);

        printNewLines(1);

    }

    /* FERNANDA */

    public void fernanda(){

    }

    /* ANDREAS */

    public void andreas(){

        // andreasTileMoving();
        andreasDrawRacks();
        andreasBoardTest();


    }

    private void andreasDrawRacks() {

        final String testName = "Draw Rack";
        String testString = "";

        // draw rack of players after handout

        for (var p = 0; p < 4; p++) {
            testString += ("Rack of Player " + (p+1) + ":\n" + playerRackString(p) + "\n");

            // prints every players rack sorted by group and run
            //    testSorting(p);

        }

        // moves tiles from current players sketchRack to sketchBoard position (4,4)
        //    testTileMoving();

        testString += newLines(1);


        testString += newLines(1);

        // check board and print rack

        testString += ("Is valid? : " + rummi.getSketchBoard().isValid());

        printTest(testName, testString);

    }

    private void andreasBoardTest(){

        final var testName = "BoardTest";
        final var testDesc = "is testing moveTileFromRackToBoard. Is using Board-, Player-, Rack-, Rummikub-Class";
        var testString = "";
        // draw the board

        testString += boardString();

        printTest(testName, testString, testDesc);

    }

    private static void andreasTileMoving() {

        rummi.moveTileFromCurrentRackToBoard(new Point(0,0), new Point(4,4));
        rummi.moveTileFromCurrentRackToBoard(new Point(0,1), new Point(4,5));
        rummi.moveTileFromCurrentRackToBoard(new Point(0,2), new Point(4,6));

        rummi.moveTileFromCurrentRackToBoard(new Point(0, 6), new Point(8, 3));
    }


    /* HELPER */

    private static void printNewLines(int n) {

        System.out.print(CharsTimes(n, '\n'));

    }

    private static String newLines(int n) {

        return CharsTimes(n, '\n');
    }

    private static String CharsTimes(int n, char c) {

        String returnString = "";

        for (var i = 0; i < n; i++) {
            returnString += c;
        }

        return returnString;
    }

    private static void printPlayerRack(int player) {

        for (var i = 0; i < Rack.GRID_HEIGHT; i++) {
            for (var o = 0; o < Rack.GRID_WIDTH; o++) {
                var point = new Point(i, o);
                System.out.print(rummi.getPlayerAt(player).getRack().pointToGridTile(point).toString());
            }
            //    newLine(1);
        }
        printNewLines(1);
    }

    private static String playerRackString(int player) {

        String returnString = "";

        for (var i = 0; i < Rack.GRID_HEIGHT; i++) {
            for (var o = 0; o < Rack.GRID_WIDTH; o++) {
                var point = new Point(i, o);
                returnString += rummi.getPlayerAt(player).getRack().pointToGridTile(point).toString();
            }
            //    newLine(1);
        }
        returnString += newLines(1);

        return returnString;

    }

    private static void printPlayerSketchRack (int player){

        for (var i = 0; i < Rack.GRID_HEIGHT; i++) {
            for (var o = 0; o < Rack.GRID_WIDTH; o++) {
                var point = new Point(i, o);
                System.out.print(rummi.getPlayerAt(player).getSketchRack().pointToGridTile(point).toString());
            }
            //    newLine(1);
        }
        printNewLines(1);
    }

    private static void printBoard () {

        for(int i = 0; i < Board.GRID_HEIGHT; i++){
            for(int o = 0; o < Board.GRID_WIDTH; o++){
                Point point = new Point(i, o);
                System.out.print(rummi.getSketchBoard().getGridTileAt(point).toString());
            }
            printNewLines(1);
        }
    }

    private static String boardString () {

        String returnString = "";

        for(int i = 0; i < Board.GRID_HEIGHT; i++){
            for(int o = 0; o < Board.GRID_WIDTH; o++){
                Point point = new Point(i, o);
                returnString += rummi.getSketchBoard().getGridTileAt(point).toString();
            }
            returnString += "\n";
        }

        return returnString;

    }

    /**
     * works with Intellij PlugIn "Grep Console"
     */
    private void printTest(String name, String test){

        printTest(name, test, "");

    }

    private void printTest(String name, String test, String withDescription){

        var description = "";

        if (!withDescription.equals("")) {
            description = "\n § " + withDescription;
        }

        String line = CharsTimes(10 + name.length()+1, '-');
        System.out.print("\n\n" + line + "\n [TEST] : " + name + description + " \n" + line + "\n\n" + test);

    }



}
