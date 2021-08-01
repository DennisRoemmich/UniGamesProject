package rummikub_game;

import java.awt.*;
import java.util.ArrayList;

/**
 * this class is for the game board
 */
public class Board {

    public static final int GRID_HEIGHT = 8;
    public static final int GRID_WIDTH = 16;

    private GridTile[][] mGrid;

    /**
     * constructor
     */
    public Board() {

        // init Board with empty GridTiles

        initEmptyBoard();

    }

    public GridTile[][] getBoard() {

        return mGrid;
    }

    public int getBoardSize() {

        return GRID_HEIGHT * GRID_WIDTH;
    }

    public void initEmptyBoard() {

        mGrid = new GridTile[GRID_HEIGHT][GRID_WIDTH];

        for (var i = 0; i < getBoardSize(); i++) {

            mGrid[i / GRID_WIDTH][i % GRID_WIDTH] = new GridTile();

        }
    }

    /**
     * adds a tile at a position
     * @param position position
     * @param tile tile
     * @return true if valid, false if not
     */
    public boolean addTile(Point position, Tile tile) {

        if (isOnBoard(position) && getGridTileAt(position).isEmpty()) {

            getGridTileAt(position).setTile(tile);
            return true;

        } else {

            return false;
        }
    }

    /**
     * removes a tile at a position
     * @param position position
     */
    public void removeTile(Point position) {

        getGridTileAt(position).removeTile();
    }

    /**
     * @param point coordinates
     * @return tile at coordinates
     */
    public GridTile getGridTileAt(Point point) {

        return mGrid[point.x][point.y];

    }

    public boolean isOnBoard(Point point) {

        return point.x < GRID_HEIGHT && point.y < GRID_WIDTH;
    }

    /**
     * moves a tile on the board
     * @param from tile coordinates
     * @param to target coordinates
     * @return true if valid, false if not
     */
    public boolean moveTile(Point from, Point to) {

        if (isOnBoard(from) && isOnBoard(to) && !getGridTileAt(from).isEmpty() && getGridTileAt(to).isEmpty()) {

            getGridTileAt(to).setTile(getGridTileAt(from).getTile());
            getGridTileAt(from).removeTile();

            return true;
        }
        return false;
    }

    /**
     * checks if the board is valid
     * @return true if valid, false if not
     */
    public boolean isValid() {

        for (var set : createSets()) {

            if (!set.isValid()) {

                return false;
            }
        }
        return true;
    }

    public String toString() {

        return toString(false);
    }

    /**
     * for debug
     * @param wide
     * @return
     */
    public String toString(boolean wide) {

        var strB = new StringBuilder();

        for (var i = 0; i < Board.GRID_HEIGHT; i++) {

            if (wide) {

                var formatted = String.format("%02d", i);
                strB.append(formatted).append(" | ");

            }

            for (var o = 0; o < Board.GRID_WIDTH; o++) {

                var point = new Point(i, o);
                strB.append(getGridTileAt(point).toString(wide));

            }

            strB.append("\n");
        }
        return strB.toString();
    }


    /**
     * creates sets laying on the board for e.g. validation check
     * @return array list of sets
     */
    private ArrayList<Set> createSets() {

        ArrayList<Set> setList = new ArrayList<>();
        var currentSet = new Set();

        for (var i = 0; i < getBoardSize(); i++) {

            var currentGridTile = mGrid[i / GRID_WIDTH][i % GRID_WIDTH];

            if (currentGridTile.isEmpty()) {

               if (currentSet.getSize() != 0) {

                   setList.add(currentSet);
                   currentSet = new Set();
               }
            } else {

                currentSet.addTile(currentGridTile.getTile());
            }
        }
        return setList;
    }

}

