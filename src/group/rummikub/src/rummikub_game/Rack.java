package rummikub_game;

import java.awt.*;

public class Rack {

    public static final int GRID_HEIGHT = 2;
    public static final int GRID_WIDTH = 15;

    private final GridTile[][] grid;
    private int size;

    /**
     * Constructor
     */
    public Rack() {

        grid = new GridTile[GRID_HEIGHT][GRID_WIDTH];
        for (var i = 0; i < GRID_HEIGHT * GRID_WIDTH; i++) {

            grid[i / GRID_WIDTH][i % GRID_WIDTH] = new GridTile();
            grid[i / GRID_WIDTH][i % GRID_WIDTH].setPosition(i);
        }
        size = 0;
    }

    /**
     * @return size of Rack
     */
    public int getSize() {

        return size;
    }

    /**
     * @return true if empty
     * @return false if not empty
     */
    public boolean isEmpty() {

        return size == 0;
    }

    /**
     * @return sum of all Tiles in rack
     */
    public int getSum(){

        var sum = 0;

        for (var i = 0; i < GRID_HEIGHT * GRID_WIDTH; i++) {

            sum = sum + positionToGridTile(i).getTile().getValue();
        }

        return sum;
    }

    /**
     * @return grid of Rack
     */
    public GridTile[][] getGrid() {

        return grid;
    }

    /**
     * converts Point into position
     * @param point
     * @return position
     */
    public int pointToPosition(Point point) {

        return point.x + point.y;
    }

    /**
     * converts point to GridTile
     * @param point
     * @return GridTile
     */
    public GridTile pointToGridTile(Point point) {

        return grid[point.x][point.y];
    }

    /**
     * converts GridTile to point
     * @param gridTile
     * @return point
     */
    public Point gridTileToPoint(GridTile gridTile) {

        return new Point(gridTile.getPosition() / GRID_WIDTH, gridTile.getPosition() % GRID_WIDTH);
    }

    /**
     * converts position to GridTile
     * @param position
     * @return GridTile
     */
    public GridTile positionToGridTile(int i) {

        return grid[i / GRID_WIDTH][i % GRID_WIDTH];
    }

    /**
     * adds a Tile to the Rack
     * @param tile
     */
    public void addTile(Tile tile) {

        for (var i = 0; i < GRID_HEIGHT * GRID_WIDTH; i++) {

            if (positionToGridTile(i).isEmpty()) {

                positionToGridTile(i).setTile(tile);
                size++;

                return;
            }
        }
    }

    /**
     * adds several Tiles to the Rack
     * @param tiles
     */
    public void addTiles(Tile[] tiles) {

        for (Tile tile : tiles) {

            addTile(tile);
        }
    }

    /**
     * removes a Tile from the Rack
     * @param position
     */
    public void removeTile(Point pos) {

        pointToGridTile(pos).removeTile();
        size--;
    }

    /**
     * moves a Tile to an other GridTile
     * @param from-position
     * @param to-position
     * @return boolean if happened
     */
    public boolean moveTile(Point from, Point to) {

        var toMove = pointToGridTile(from);
        var target = pointToGridTile(to);

        if (!toMove.isEmpty()) {

            if (!target.isEmpty()) {

                if (getFirstEmpty(pointToPosition(to)) != null) {

                    var gridTile = getFirstEmpty(pointToPosition(to));
                    var nextGridTile = positionToGridTile(gridTile.getPosition());

                    while (gridTile != target) {

                        nextGridTile = positionToGridTile(nextGridTile.getPosition() - 1);

                        gridTile.setTile(nextGridTile.getTile());
                        gridTile = positionToGridTile(gridTile.getPosition() - 1);
                    }
                } else {

                    swap(toMove, target);

                    return true;
                }
            }
            target.setTile(toMove.getTile());
            toMove.removeTile();

            return true;
        }
        return false;
    }

    /**
     * returns the first empty GridTile
     * @param position
     * @return empty GridTile
     */
    private GridTile getFirstEmpty(int pos) {

        for (int i = pos + 1; i < GRID_HEIGHT * GRID_WIDTH; i++) {

            if (positionToGridTile(i).isEmpty()) {

                return positionToGridTile(i);
            }
        }
        return new GridTile();
    }

    /**
     * returns first non-empty GridTile
     * @param position
     * @return non-empty GridTile
     */
    private GridTile getFirstNonEmpty(int pos) {

        for (int i = pos + 1; i < GRID_HEIGHT * GRID_WIDTH; i++) {

            if (!positionToGridTile(i).isEmpty()) {

                return positionToGridTile(i);
            }
        }
        return new GridTile();
    }

    /**
     * closes the gapas in the Rack
     */
    private void closeGaps() {

        for (var i = 0; i < GRID_HEIGHT * GRID_WIDTH; i++) {

            if (positionToGridTile(i).isEmpty() && (getFirstNonEmpty(i) != null)) {

                swap(positionToGridTile(i), getFirstNonEmpty(i));
            }
        }
    }

    /**
     * swaps tiles of two GridTiles
     * @param GridTile 1
     * @param GridTile 2
     */
    private void swap(GridTile uno, GridTile dos) {

        var help = uno.getTile();

        uno.setTile(dos.getTile());
        dos.setTile(help);

        if (uno.getTile() == null) {

            uno.removeTile();
        }
        if (dos.getTile() == null) {

            dos.removeTile();
        }
    }

                                        // WHOLE SORTIEREN HAS TO BE DONE

    public void sortForGroup() {

        this.closeGaps();

        var sorted = new GridTile[size];

        for (var i = 0; i < sorted.length; i++) {

            sorted[i] = grid[i / 15][i % 15];
        }

        sortGroup(sorted);

        for (var i = 0; i < GRID_HEIGHT * GRID_WIDTH; i++) {

            grid[i / 15][i % 15].setTile(sorted[i].getTile());
        }
    }

    public void sortForRun() {

        this.closeGaps();

        var sorted = new GridTile[size];

        for (var i = 0; i < sorted.length; i++) {

            sorted[i] = grid[i / 15][i % 15];
        }

        sortRun(sorted);

        for (var i = 0; i < GRID_HEIGHT * GRID_WIDTH; i++) {

            grid[i / 15][i % 15].setTile(sorted[i].getTile());
        }
    }

    private void sortRun(GridTile[] grid) {

        int minValue;
        var minPos = 0;

        for (var i = 0; i < grid.length; i++) {

            minValue = grid[0].getTile().getValue();

            for (var j = i; j < grid.length; j++) {

                if (grid[j].getTile().getValue() < minValue) {

                    minValue = grid[j].getTile().getValue();
                    minPos = j;
                }
            }
            this.swap(grid[i], grid[minPos]);
        }

        var iterator = 0;
        var old = 0;
        for (var i = 1; i < 13; i++) {

            while (grid[iterator].getTile().getValue() == i) {

                iterator++;
            }
            var num = new GridTile[i - old];
            for (var j = 0; j < num.length; j++) {


            }
        }
    }

    /** sry, ist jzt echt hässlich und lang geworden, wird noch geändert, änderungsvorschläge erwünscht
     * sorts a grid for group
     * @param grid sorted by group
     */
    private void sortGroup(GridTile[] grid) {

        var maxPos = 0;

        for (var i = 0; i < grid.length; i++) {

            for (var j = i; j < grid.length; j++) {

                if (grid[i].getTile().compareTo(grid[j].getTile()) == 1) {

                    maxPos = j;
                }
            }
            swap(grid[i], grid[maxPos]);
        }

        var iterator = 0;
        GridTile[] black;
        GridTile[] blue;
        GridTile[] red;
        GridTile[] yellow;

        while (grid[iterator].getTile().getTileColor() == TileColor.BLACK) {

            iterator++;
        }
        black = new GridTile[iterator];
        for (var i = 0; i < black.length; i++) {

            black[i] = grid[i];
        }
        while (grid[iterator].getTile().getTileColor() == TileColor.BLUE) {

            iterator++;
        }
        blue = new GridTile[iterator];
        for (var i = 0; i < blue.length; i++) {

            blue[i] = grid[black.length + i];
        }
        while (grid[iterator].getTile().getTileColor() == TileColor.RED) {

            iterator++;
        }
        red = new GridTile[iterator];
        for (var i = 0; i < red.length; i++) {

            red[i] = grid[black.length + blue.length + i];
        }
        while (grid[iterator].getTile().getTileColor() == TileColor.YELLOW) {

            iterator++;
        }
        yellow = new GridTile[iterator];
        for (var i = 0; i < yellow.length; i++) {

            yellow[i] = grid[black.length + blue.length + red.length + i];
        }

        sortRun(black);
        sortRun(blue);
        sortRun(red);
        sortRun(yellow);

        for (var j = 0; j < black.length; j++) {
            grid[j] = black[j];
        }
        for (var k = 0; k < blue.length; k++) {
            grid[black.length + k] = blue[k];
        }
        for (var l = 0; l < blue.length; l++) {
            grid[black.length + blue.length + l] = red[l];
        }
        for (var m = 0; m < blue.length; m++) {
            grid[black.length + blue.length + red.length + m] = yellow[m];
        }
    }
}
