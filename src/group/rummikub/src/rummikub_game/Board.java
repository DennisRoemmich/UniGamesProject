package rummikub_game;

import java.awt.*;

public class Board {

    static final int boardHeight = 20;
    static final int boardWidth = 20;

    GridTile[][] grid = new GridTile[boardWidth][boardHeight];

    public Board(){

    }

    public void addTile(Point position, Tile tile){

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
