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
     * @return true if empty, false if not empty
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
     * @param point to convert
     * @return position
     */
    public int pointToPosition(Point point) {

        return point.x + point.y;
    }

    /**
     * converts point to GridTile.
     * @param point to convert
     * @return GridTile
     */
    public GridTile pointToGridTile(Point point) {

        return grid[point.x][point.y];
    }

    /**
     * converts GridTile to point
     * @param gridTile toconvert
     * @return point
     */
    public Point gridTileToPoint(GridTile gridTile) {

        return new Point(gridTileToPosition(gridTile) / GRID_WIDTH, gridTileToPosition(gridTile) % GRID_WIDTH);
    }

    /**
     * converts position to GridTile
     * @param position to convert
     * @return GridTile
     */
    public GridTile positionToGridTile(int position) {

        return grid[position / GRID_WIDTH][position % GRID_WIDTH];
    }

    /**
     *
     * @param gridTile to convert
     * @return position
     */
    public int gridTileToPosition(GridTile gridTile) {

        for (var i = 0; i < GRID_HEIGHT * GRID_WIDTH; i++) {

            if (grid[i / GRID_WIDTH][i % GRID_WIDTH] == gridTile) {

                return i;
            }
        }
        return -1;
    }

    /**
     * adds a Tile to the Rack
     * @param tile tile to be added
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
     * @param tiles tiles array
     */
    public void addTiles(Tile[] tiles) {

        for (Tile tile : tiles) {

            addTile(tile);
        }
    }

    /**
     * removes a Tile from the Rack
     * @param pos position
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

        var m = gridTileToPosition(toMove);
        var t = gridTileToPosition(target);

        if (!toMove.isEmpty()) {

            if (!target.isEmpty()) {

                var e = gridTileToPosition(getFirstEmpty(t));

                if (m < t) {

                    moveMT(t, e);
                    swap(toMove, target);

                } else if (m > t) {

                    moveTM(m, t, e);
                }
            } else {

                swap(toMove, target);
            }

            return true;
        }
        return false;
    }

    public String toString(){

        return toString(false);

    }

    public String toString(boolean wide){

        var strB = new StringBuilder();

        for (var i = 0; i < Rack.GRID_HEIGHT; i++) {

            for (var o = 0; o < Rack.GRID_WIDTH; o++) {

                var point = new Point(i, o);
                strB.append(pointToGridTile(point).toString(wide));

            }

            strB.append("\n");

        }

        return strB.toString();

    }

    /**
     * help-method for moveTile()
     * @param t target position
     * @param e empty position
     */
    private void moveMT(int t, int e) {

        if (e != -1) {

            var n = e - t;

            for (var i = 0; i < n; i++) {

                swap(positionToGridTile(e), positionToGridTile(e - 1));
                e--;
            }
        }
    }

    /**
     * help-method for moveTile()
     * @param m toMove position
     * @param t target position
     * @param e empty position
     */
    private void moveTM(int m, int t, int e) {

        if (e < m && e != -1) {

            var n = e - t;

            for (var i = 0; i < n; i++) {

                swap(positionToGridTile(e), positionToGridTile(e - 1));
                e--;
            }
            swap(positionToGridTile(m), positionToGridTile(t));

        } else {

            var n = m - t;

            for (var i = 0; i < n; i++) {

                swap(positionToGridTile(m), positionToGridTile(m - 1));
                m--;
            }
        }
    }

    /**
     * returns the first empty GridTile
     * @param pos position
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
     * @param pos position
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
     * swaps tiles of two GridTiles
     * @param uno GridTile 1
     * @param dos GridTile 2
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

    /**
     * sorts rack for group
     */
    public void sortForGroup() {

        var groupSorted = new Tile[size];

        tilesToGrid(sortGroup(gridToTiles(groupSorted), true));
    }

    /**
     * sorts rack for run
     */
    public void sortForRun() {

        var runSorted = new Tile[size];

        tilesToGrid(sortRun(gridToTiles(runSorted), true));
    }

    /**
     * returns a tiles array with all rack tiles
     * @param tiles empty tiles array
     * @return tiles array filled with tiles on rack
     */
    private Tile[] gridToTiles(Tile[] tiles) {

        for (var i = 0; i < GRID_HEIGHT * GRID_WIDTH; i++) {

            if (!positionToGridTile(i).isEmpty()) {

                var j = 0;

                while (tiles[j] != null) {

                    j++;
                }

                tiles[j] = positionToGridTile(i).getTile();
            }
        }

        return tiles;
    }

    /**
     * sets rack to tiles in tiles array
     * @param tiles tiles array
     */
    private void tilesToGrid(Tile[] tiles) {

        for (var i = 0; i < GRID_HEIGHT * GRID_WIDTH; i++) {

            if (i < size) {

                positionToGridTile(i).setTile(tiles[i]);

            } else {

                positionToGridTile(i).removeTile();
            }
        }
    }

    /**
     * sorts a tiles-array for run
     * @param  tiles tiles array
     * @param mode mode (sort separate runs for group, or not)
     * @return array sorted for run
     */
    private Tile[] sortRun(Tile[] tiles, boolean mode) {

        int min;
        for (var i = 0; i < tiles.length - 1; i++) {

            min = i;

            for (var j = i; j < tiles.length; j++) {

                // true if tiles[j] has to be in front of tiles[min]
                if (tiles[min].compareToRun(tiles[j])) {

                    min = j;
                }
            }

            Tile help = tiles[i];

            tiles[i] = tiles[min];
            tiles[min] = help;
        }

        if (mode) {

            sortRunForGroup(tiles);
        }
        return tiles;
    }

    /**
     * sorts a tiles-array for group
     * @param tiles tiles array
     * @param mode mode (sort separate groups for run, or not)
     * @return array sorted for group
     */
    private Tile[] sortGroup(Tile[] tiles, boolean mode) {

        int min;
        for (var i = 0; i < tiles.length - 1; i++) {

            min = i;

            for (var j = i; j < tiles.length; j++) {

                // true if tiles[j] has to be in front of tiles[min]
                if (tiles[min].compareToGroup(tiles[j])) {

                    min = j;
                }
            }

            Tile help = tiles[i];

            tiles[i] = tiles[min];
            tiles[min] = help;
        }

        if (mode) {

            sortGroupForRun(tiles);
        }
        return tiles;
    }

    /**
     * sorts array sorted for run for group
     * @param tiles tiles array sorted for run
     */
    private void sortRunForGroup(Tile[] tiles) {

        var h = 0;
        for (var i = 1; i <= 13; i++) {

            var n = 0;

            while (h < tiles.length && tiles[h].getValue() == i) {

                h++;
                n++;
            }

            if (n > 1) {

                var toGroup = new Tile[n];

                System.arraycopy(tiles, h - n, toGroup, 0, n);

                sortGroup(toGroup, false);

                if (h - (h - n) >= 0) System.arraycopy(toGroup, 0, tiles, h - n, h - (h - n));
            }
        }
    }

    /**
     * sorts array sorted for run for group
     * @param tiles tiles array sorted for run
     */
    private void sortGroupForRun(Tile[] tiles) {

        var h = 0;
        for (var i = 0; i < 5; i++) {

            var n = 0;

            while (h < tiles.length && tiles[h].getTileColor().value == i) {

                h++;
                n++;
            }

            if (n > 1) {

                var toRun = new Tile[n];

                System.arraycopy(tiles, h - n, toRun, 0, n);

                var sorted = sortRun(toRun, false);

                if (h - (h - n) >= 0) System.arraycopy(sorted, 0, tiles, h - n, h - (h - n));
            }
        }
    }
}
