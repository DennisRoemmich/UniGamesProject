package rummikub_game;

public class Tile {

    TileColor color;
    int value;

    /**
     * Constructor only for joker
     * @param color of tile
     */
    public Tile(TileColor joker){

        this.color = joker;
        value = 30;
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

        return color;
    }

    /**
     * @return value of tile
     */
    public int getValue() {

        return value;
    }

    /**
     * @return true if joker
     * @return false if not joker
     */
    public boolean isJoker(){

        return color == TileColor.JOKER;
    }

    public String toString(){

        return "[" + color.toString() + "," + Integer.toString(value) + "]";
    }

    public boolean compareToRun(Tile tile) {

        return tile.getValue() < value;
    }

    public boolean compareToGroup(Tile tile) {

        return tile.getTileColor().value < color.value;
    }
}
