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

        maikDrawRacks();

        rummi.getPlayerAt(0).getSketchRack().sortForRun();

        maikDrawRack(0);

        maikFinishMove(0, true);

        maikFinishMove(1, false);

        maikDrawRack(2);

        maikMoveTile(2, new Point(0,3), new Point(0, 10));

        maikMoveTile(2, new Point(0, 6), new Point(0, 3));

        rummi.getPlayerAt(2).getRack().sortForGroup();

        maikDrawRack(2);

        rummi.getPlayerAt(2).getRack().moveTile(new Point(0, 7), new Point(0, 2));

        maikDrawRack(2);

        rummi.getPlayerAt(2).getRack().moveTile(new Point(0, 2), new Point(0, 7));

        maikDrawRack(2);

        rummi.getPlayerAt(2).getRack().moveTile(new Point(0, 10), new Point(0, 2));

        maikDrawRack(2);

        rummi.getPlayerAt(2).getRack().moveTile(new Point(0, 4), new Point(0, 8));

        maikDrawRack(2);



        var n = 15;
        while (n > 0) {
            rummi.currentPlayerTakeTile();
            n--;
        }
        maikDrawRack(2);

        rummi.getPlayerAt(2).getRack().sortForGroup();

        maikDrawRack(2);

        rummi.getPlayerAt(2).getRack().moveTile(new Point(0, 4), new Point(1, 14));

        maikDrawRack(2);

        rummi.getPlayerAt(2).getRack().moveTile(new Point(0, 8), new Point(1, 5));

        maikDrawRack(2);

        rummi.getPlayerAt(2).getRack().sortForGroup();

        maikDrawRack(2);

        rummi.getPlayerAt(2).getRack().moveTile(new Point(0, 4), new Point(1, 2));

        maikDrawRack(2);

        rummi.getPlayerAt(2).getRack().moveTile(new Point(0, 4), new Point(0, 3));

        maikDrawRack(2);

        rummi.getPlayerAt(2).getRack().moveTile(new Point(1, 12), new Point(0, 3));

        maikDrawRack(2);

        rummi.getPlayerAt(2).getRack().removeTile(new Point(1, 4));

        maikDrawRack(2);

        maikFinishMove(2, true);

    }

    private void maikDrawRacks() {

        var testName = "Draw Racks:";
        StringBuilder builder = new StringBuilder();

        for (var p = 0; p < 4; p++) {

            builder.append("Rack of Player " + (p+1) + ": \n" + rummi.getPlayerAt(p).getRack().toString(true));
        }

        printTest(testName, builder.toString());
    }

    private void maikDrawRack(int p) {

        var testName = "Draw Rack";
        StringBuilder builder = new StringBuilder();

        builder.append("Rack of Player " + (p+1) + ": \n" + rummi.getPlayerAt(p).getRack().toString(true));

        printTest(testName, builder.toString());
    }

    private void maikFinishMove(int p, boolean m) {

        var testName = "Player " + (p+1) + ": Finish Move";
        StringBuilder builder = new StringBuilder();

        if (rummi.finishMove()) {

            builder.append(rummi.getPlayerAt(p).getRack().toString(true));
            if (m) {

                rummi.getPlayerAt(p).getRack().sortForGroup();

            } else {

                rummi.getPlayerAt(p).getRack().sortForRun();
            } //
            builder.append("\n" + rummi.getPlayerAt(p).getRack().toString(true)); //*/
        }

        printTest(testName, builder.toString());
    }

    private void maikMoveTile(int p, Point m, Point t) {

        var testName = "Player " + (p+1) + ": Move Tile : " + (m.x + m.y + 1) + " => " + (t.x + t.y + 1);
        StringBuilder builder = new StringBuilder();

        builder.append(rummi.getPlayerAt(p).getRack().moveTile(m, t));
        builder.append("\n" + rummi.getPlayerAt(p).getRack().toString(true));

        printTest(testName, builder.toString());
    }

    private void maikSorting(int p){

        rummi.getPlayerAt(p).getRack().sortForGroup();
        printPlayerRack(p);

        rummi.getPlayerAt(p).getRack().sortForRun();
        printPlayerRack(p);

        printNewLines(1);

    }

    /* FERNANDA */

    public void fer(){


        printTest("SetTest", "", "testing Set isValid method");
        printBoard();
        System.out.println(rummi.getSketchBoard().isValid());
    }

    /* ANDREAS */

    public void andreas(){

        //andreasTileMoving();
        //andreasDrawRacks();
        //andreasBoardTest();
        andreasSetTest();

    }

    private void andreasSetTest(){

        rummi.moveTileFromCurrentRackToBoard(new Point(0,0), new Point(1, 1));
        rummi.moveTileFromCurrentRackToBoard(new Point(0,1), new Point(1, 2));
        rummi.moveTileFromCurrentRackToBoard(new Point(0,2), new Point(1, 3));

        rummi.moveTileFromCurrentRackToBoard(new Point(0,3), new Point(3, 3));
        rummi.moveTileFromCurrentRackToBoard(new Point(0,4), new Point(3, 4));
        rummi.moveTileFromCurrentRackToBoard(new Point(0,5), new Point(3, 5));

        var builder = new StringBuilder();


        builder.append(rummi.getSketchBoard().toString(true) + "\n\n");
        builder.append("Fancy:\n" + rummi.getCurrentPlayer().getSketchRack().toString(true) + "\n");
        builder.append("Normal:\n" + rummi.getCurrentPlayer().getSketchRack().toString() + "\n");

        builder.append("Board valid? : " + rummi.getSketchBoard().isValid() + "\n");

        printTest("SetTest", builder.toString());


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

        testString += getNewlines(1);


        testString += getNewlines(1);

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

        rummi.moveTileFromCurrentRackToBoard(new Point(0,2), new Point(3,6));
        rummi.moveTileFromCurrentRackToBoard(new Point(0,3), new Point(3,7));
        rummi.moveTileFromCurrentRackToBoard(new Point(0,4), new Point(3,8));

        rummi.moveTileFromCurrentRackToBoard(new Point(0, 6), new Point(8, 3));
    }


    /* HELPER */


    /**
     * returns String with n newlines
     */
    private static String getNewlines(int n) {

        return getCharTimes(n, '\n');
    }

    /**
     * returns String with n times the char c
     */
    private static String getCharTimes(int n, char c) {

        var returnString = "";

        for (var i = 0; i < n; i++) {
            returnString += c;
        }

        return returnString;
    }


    private static String rackString(Rack rack) {

        var returnString = "";

        for (var i = 0; i < Rack.GRID_HEIGHT; i++) {
            for (var o = 0; o < Rack.GRID_WIDTH; o++) {
                var point = new Point(i, o);
                returnString += rack.pointToGridTile(point).toString();
            }
            //    newLine(1);
        }
        returnString += getNewlines(1);

        return returnString;

    }

    // use rackString(Rack rack) directly?
    private static String playerRackString(int player) {

        return rackString(rummi.getPlayerAt(player).getRack());

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

    /** use the String-functions instead plss */
    private static void printNewLines(int n) {

        System.out.print(getCharTimes(n, '\n'));

    }

    /** use the String-functions instead plss */
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


    /** use the String-functions instead plss */
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

    /** use the String-functions instead plss */
    private static void printBoard () {

        for(int i = 0; i < Board.GRID_HEIGHT; i++){
            for(int o = 0; o < Board.GRID_WIDTH; o++){
                Point point = new Point(i, o);
                System.out.print(rummi.getSketchBoard().getGridTileAt(point).toString());
            }
            printNewLines(1);
        }
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

        String line = getCharTimes(10 + name.length()+1, '-');
        System.out.print("\n\n" + line + "\n [TEST] : " + name + description + " \n" + line + "\n\n" + test);

    }



}
