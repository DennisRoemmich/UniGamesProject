package rummikub_game;

public class Tile {

    TileColor color;
    int value;

    Tile(){
    }

    public Tile(TileColor color){
    }

    public Tile(TileColor color, int value){
    }

    public boolean isJoker(){
        return false;
    }

    public TileColor getTileColor(){
        return this.color;
    }

    public int getValue(){
        return this.value;
    }




}

