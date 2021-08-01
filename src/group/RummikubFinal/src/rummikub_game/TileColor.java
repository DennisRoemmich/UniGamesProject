package rummikub_game;

/**
 * this enum includes all possible colors, joker is counted as a own color
 */
public enum TileColor {

    BLACK(0),
    BLUE(1),
    RED(2),
    YELLOW(3),
    JOKER(4);

    public final int mValue;

    TileColor(int value) {

        this.mValue = value;
    }
}
