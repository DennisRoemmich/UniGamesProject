package rummikub_game;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Board {

    public static final int GRID_HEIGHT = 20;
    public static final int GRID_WIDTH = 20;

    GridTile[][] grid;

    public Board(){

        // init Board with empty GridTiles

        initEmptyBoard();

    }

    public int getBoardSize() {

        return GRID_HEIGHT * GRID_WIDTH;
    }

    public void initEmptyBoard() {

        grid = new GridTile[GRID_HEIGHT][GRID_WIDTH];

        for (var i = 0; i < getBoardSize(); i++) {

            grid[i / GRID_WIDTH][i % GRID_WIDTH] = new GridTile();

        }
    }

    public boolean addTile(Point position, Tile tile) {

        if(isOnBoard(position) && getGridTileAt(position).isEmpty()){

            getGridTileAt(position).setTile(tile);
            return true;

        } else {

            return false;
        }
    }

    public void removeTile(Point position) {

        getGridTileAt(position).removeTile();
    }

    public GridTile getGridTileAt(Point point){

        return grid[point.x][point.y];
    }

    public boolean isOnBoard(Point point) {

        return point.x < GRID_HEIGHT && point.y < GRID_WIDTH;
    }

    public boolean moveTile(Point from, Point to) {

        if (isOnBoard(from) && isOnBoard(to) && !getGridTileAt(from).isEmpty() && getGridTileAt(to).isEmpty()) {

            getGridTileAt(to).setTile(getGridTileAt(from).getTile());
            getGridTileAt(from).removeTile();

            return true;
        }
        return false;
    }

    public void clearBoard() {

        initEmptyBoard();
    }

    public boolean isValid() {

        for (var set : createSets()) {

            if (!set.isValid()) {

                return false;
            }
        }
        return true;
    }

    public String toString(){

        return toString(false);
    }

    public String toString(boolean wide){

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


    private ArrayList<Set> createSets() {

        ArrayList<Set> setList = new ArrayList<>();
        var currentSet = new Set();

        for (var i = 0; i < getBoardSize(); i++) {

            var currentGridTile = grid[i / GRID_WIDTH][i % GRID_WIDTH];

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

