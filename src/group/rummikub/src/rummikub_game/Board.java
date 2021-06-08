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

    public void initEmptyBoard(){
        grid = new GridTile[GRID_HEIGHT][GRID_WIDTH];
        for (var i = 0; i < GRID_HEIGHT * GRID_WIDTH; i++) {

            grid[i / GRID_WIDTH][i % GRID_WIDTH] = new GridTile();

        }
    }

    public void addTile(Point position, Tile tile){
        if(position.x < GRID_WIDTH && position.y < GRID_HEIGHT && grid[position.x][position.y].isEmpty()){
            grid[position.x][position.y].setTile(tile);
        } else {
            System.out.println("invalid position");
        }
    }

    public GridTile getGridTileAt(Point point){
        return grid[point.x][point.y];
    }

    public boolean moveTile(Point from, Point to){
        if (from.x < GRID_WIDTH && from.y < GRID_HEIGHT && to.x < GRID_WIDTH && to.y < GRID_HEIGHT &&
        grid[to.x][to.y].isEmpty()){
            grid [to.x][to.y].setTile(grid[from.x][from.y].getTile());
            grid[from.x][from.y].removeTile();
            return true;
        } else {
            return false;
        }
    }

    public void clearBoard(){
        initEmptyBoard();
    }

    public boolean isValid(){
        for(var set : createSets()){
            if(!set.isValid()){
                return false;
            }
        }
        return true;
    }

    private ArrayList<Set> createSets(){

        /* Mit Liste! */

        ArrayList<Set> setList = new ArrayList<>();
        var currentSet = new Set();

        for (var i = 0; i < GRID_HEIGHT * GRID_WIDTH; i++) {

            var currentGridTile = grid[i / GRID_WIDTH][i % GRID_WIDTH];

            if (currentGridTile.isEmpty()){

               if ( currentSet.getSize() != 0){
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

