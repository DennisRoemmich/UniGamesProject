package rummikub_game;

import java.awt.*;

public class Board {

    public static final int GRID_HEIGHT = 20;
    public static final int GRID_WIDTH = 20;

    GridTile[][] grid = new GridTile[GRID_WIDTH][GRID_HEIGHT];

    public Board(){

    }

    public void addTile(Point position, Tile tile){

    }

    public GridTile getGridTileAt(Point point){
        return grid[point.x][point.y];
    }

    public boolean moveTile(Point from, Point to){
        return false;
    }

    public void clearBoard(){

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
