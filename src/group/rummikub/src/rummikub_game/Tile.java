package rummikub_game;

public class Tile {

    TileColor color;
    int value;

    /**
     * Constructor only for joker
     * @param color of tile
     */
    public Tile(TileColor color){

        this.color = color;
        this.value = 30;
    }

    /**
     * Constructor for any tile but joker
     * @param color of tile
     * @param value of tile
     */
    public Tile(TileColor color, int value){

        this.color = color;
        this.value = value;
    }

    /**
     * @return color of tile
     */
    public TileColor getTileColor() {

        return this.color;
    }

    /**
     * @return value of tile
     */
    public int getValue() {

        return this.value;
    }

    /**
     * @return true if joker
     * @return false if not joker
     */
    public boolean isJoker(){

        return this.color == TileColor.JOKER;
    }
}

