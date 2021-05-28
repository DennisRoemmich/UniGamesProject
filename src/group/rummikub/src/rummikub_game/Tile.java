package rummikub_game;

public class Tile {

    TileColor color;
    int value;

    public Tile() { }

    public Tile(TileColor color){

        this.color = color;
        this.value = 30;
    }

    public Tile(TileColor color, int value){

        this.color = color;
        this.value = value;
    }

    public TileColor getTileColor() {

        return this.color;
    }

    public int getValue() {

        return this.value;
    }

    public boolean isJoker(){

        return this.color == TileColor.JOKER;
    }
}

