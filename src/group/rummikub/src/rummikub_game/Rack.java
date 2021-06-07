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
     * converts point to GridTile.
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
    public GridTile positionToGridTile(int position) {

        return grid[position / GRID_WIDTH][position % GRID_WIDTH];
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
    public void removeTile(Point position) {

        pointToGridTile(position).removeTile();
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
    private GridTile getFirstEmpty(int position) {

        for (int i = position + 1; i < GRID_HEIGHT * GRID_WIDTH; i++) {

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
    private GridTile getFirstNonEmpty(int position) {

        for (int i = position + 1; i < GRID_HEIGHT * GRID_WIDTH; i++) {

            if (!positionToGridTile(i).isEmpty()) {

                return positionToGridTile(i);
            }
        }
        return new GridTile();
    }

    /**
     * swaps tiles of two GridTiles
     * @param uno GridTile
     * @param dos GridTile
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

    public void sortForGroup() {

        var groupSorted = new Tile[size];

        tilesToGrid(sortGroup(gridToTiles(groupSorted), true));
    }

    public void sortForRun() {

        var runSorted = new Tile[size];

        tilesToGrid(sortRun(gridToTiles(runSorted), true));
    }

    private Tile[] gridToTiles(Tile [] tiles) {

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
     * @param tiles
     * @param mode
     * @return
     */
    private Tile[] sortRun(Tile[] tiles, boolean mode) {

        int min;
        for (var i = 0; i < tiles.length - 1; i++) {

            min = i;

            for (var j = i; j < tiles.length; j++) {

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
     * @param tiles-array sorted by group
     * @param mode
     */
    private Tile[] sortGroup(Tile[] tiles, boolean mode) {

        int min;
        for (var i = 0; i < tiles.length - 1; i++) {

            min = i;

            for (var j = i; j < tiles.length; j++) {

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
