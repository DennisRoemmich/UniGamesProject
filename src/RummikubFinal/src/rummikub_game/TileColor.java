package rummikub_game;

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
