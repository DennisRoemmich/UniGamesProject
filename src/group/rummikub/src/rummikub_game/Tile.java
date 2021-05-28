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

    public int compareTo(Tile tile) {

        return switch (this.color) {
            case BLACK -> compareToBlack(tile);
            case BLUE -> compareToBlue(tile);
            case RED -> compareToRed(tile);
            case YELLOW -> compareToYellow(tile);
            case JOKER -> compareToJoker(tile);
        };
    }

    private int compareToBlack(Tile tile) {

        if (tile.getTileColor() == TileColor.BLACK) {

            return 0;

        } else {

            return -1;
        }
    }

    private int compareToBlue(Tile tile) {

        if (tile.getTileColor() == TileColor.BLUE) {

            return 0;

        } else if (tile.getTileColor() == TileColor.BLACK){

            return 1;

        } else {

            return -1;
        }
    }

    private int compareToRed(Tile tile) {

        if (tile.getTileColor() == TileColor.RED) {

            return 0;

        } else if (tile.getTileColor() == TileColor.YELLOW || tile.isJoker()){

            return -1;

        } else {

            return 1;
        }
    }

    private int compareToYellow(Tile tile) {

        if (tile.getTileColor() == TileColor.YELLOW) {

            return 0;

        } else if (tile.isJoker()) {

            return -1;

        } else {

            return 1;
        }
    }

    private int compareToJoker(Tile tile) {

        if (tile.isJoker()) {

            return 0;

        } else {

            return 1;
        }
    }
}

