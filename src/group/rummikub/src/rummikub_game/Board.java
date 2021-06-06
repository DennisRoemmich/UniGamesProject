package rummikub_game;

import java.awt.*;

public class Board {

    public static final int GRID_HEIGHT = 20;
    public static final int GRID_WIDTH = 20;

    GridTile[][] grid = new GridTile[GRID_WIDTH][GRID_HEIGHT];
    GridTile[][] empty = new GridTile[GRID_WIDTH][GRID_HEIGHT];

    public Board(){

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
        grid = empty;
    }

    public boolean isValid(){
        for(var set : createSets()){
            if(!set.isValid()){
                return false;
            }
        }
        return true;
    }

    private Set[] createSets(){
        return new Set[0];
    }

}

}