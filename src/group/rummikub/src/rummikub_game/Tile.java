package rummikub_game;

import JavaFX.FXGridCell;

public class Tile {

    TileColor color;
    int value;

    /**
     * Constructor only for joker
     * @param joker color of tile
     */
    public Tile(TileColor joker){

        this.color = joker;
        value = 30;
    }

    /**
     * Constructor for any tile but joker
     * @param color color of tile
     * @param value value of tile
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
     * @return true if joker, false if not joker
     */
    public boolean isJoker(){

        return color == TileColor.JOKER;
    }

    /**
     * @return String of Tile
     */
    public String toString(){

        return toString(false);
    }

    public String toString(boolean wide){

        if (!wide) {

            return "[" + color.toString() + "," + Integer.toString(value) + "]";

        }

        var colorString = "??";

        switch (color){
            case JOKER -> colorString = "JK";
            case RED -> colorString = "RD";
            case BLUE -> colorString = "BL";
            case BLACK -> colorString = "BK";
            case YELLOW -> colorString = "YL";
        }

        var formatted = String.format("%02d", value);

        return "[" + colorString + "|" + formatted + "]";

    }


    /**
     * compares two tiles for run
     * @param tile tile to compare with
     * @return true if tile has smaller value, false if not
     */
    public boolean compareToRun(Tile tile) {

        if (tile != null) {

            return tile.getValue() < value;

        } else {

            return false;
        }
    }

    /**
     * compares two tiles for group
     * @param tile tile to compare with
     * @return true if tile has smaller color value, false if not
     */
    public boolean compareToGroup(Tile tile) {

        if (tile != null) {

            return tile.getTileColor().value < color.value;

        } else {

            return false;
        }
    }

    public boolean isEqualTo(Tile tile) {

        return color == tile.color && value == tile.value;
    }

    public boolean isEqualToFX(FXGridCell tile) {

        return color == tile.getTileColor() && value == tile.getValue();
    }
}
