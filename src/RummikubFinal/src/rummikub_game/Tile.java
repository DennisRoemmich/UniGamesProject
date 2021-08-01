package rummikub_game;

import javafx.FXGridCell;

/**
 * class for the tiles
 */
public class Tile {

    TileColor mColor;
    int mValue;

    /**
     * Constructor only for joker
     * @param joker color of tile
     */
    public Tile(TileColor joker) {

        this.mColor = joker;
        mValue = 30;
    }

    /**
     * Constructor for any tile but joker
     * @param color color of tile
     * @param value value of tile
     */
    public Tile(TileColor color, int value) {

        this.mColor = color;
        this.mValue = value;
    }

    /**
     * @return color of tile
     */
    public TileColor getTileColor() {

        return mColor;
    }

    /**
     * @return value of tile
     */
    public int getValue() {

        return mValue;
    }

    /**
     * @return true if joker, false if not joker
     */
    public boolean isJoker() {

        return mColor == TileColor.JOKER;
    }

    /**
     * @return String of Tile
     */
    public String toString() {

        return toString(false);
    }

    public String toString(boolean wide) {

        if (!wide) {

            return "[" + mColor.toString() + "," + Integer.toString(mValue) + "]";

        }

        var colorString = switch (mColor) {
            case JOKER -> "JK";
            case RED -> "RD";
            case BLUE -> "BL";
            case BLACK -> "BK";
            case YELLOW -> "YL";
        };

        var formatted = String.format("%02d", mValue);

        return "[" + colorString + "|" + formatted + "]";

    }


    /**
     * compares two tiles for run
     * @param tile tile to compare with
     * @return true if tile has smaller value, false if not
     */
    public boolean compareToRun(Tile tile) {

        if (tile != null) {

            return tile.getValue() < mValue;

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

            return tile.getTileColor().mValue < mColor.mValue;

        } else {

            return false;
        }
    }

    public boolean isEqualTo(Tile tile) {

        return mColor == tile.mColor && mValue == tile.mValue;
    }

    /**
     * @param tile grid cell
     * @return true if grid cell tile and this tile are the same (not identical, but same color and value)
     */
    public boolean isEqualToFX(FXGridCell tile) {

        return mColor != tile.getTileColor() || mValue != tile.getValue();
    }


}
