package rummikub_game;

import java.awt.*;

public class Rack {

    static int gridHeight = 2;
    static int gridWidth = 10;

    private GridTile[][] grid = new GridTile[gridWidth][gridHeight];

    Rack(){

    }

    void addTile(Tile tile){
        // ...
    }

    void addTiles(Tile[] tiles){
        for(var tile : tiles){
            // ...
        }
    }

    int getSum(){
        return 0;
    }

    boolean isEmpty(){
        return false;
    }

    void removeTile(Point position){

    }

    boolean moveTile(Point from, Point to){
        return false;
    }

    void sortForGroup(){

    }

    void sortForRun(){

    }

    int getTileAmount(){
        return 0;
    }

}
