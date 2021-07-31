package engine.pieces;

import engine.squares.Direction;
import engine.squares.Rank;

public enum PlayerColor {
    BLACK(false), WHITE(true);

    private boolean mValue;

    PlayerColor(boolean value) {
        this.mValue = value;
    }

    public PlayerColor getContrary() {
        if (this == BLACK) {
            return WHITE;
        } else {
            return BLACK;
        }
    }

    public boolean isWhite() {
        return mValue;
    }

    public static PlayerColor valueOf(boolean value) {
        return value ? WHITE : BLACK;
    }

    public Rank getBackrank() {
        return isWhite() ? Rank.M1 : Rank.M8;
    }

    public Direction getPawnMoveDirection() {
        return isWhite() ? Direction.UP : Direction.DOWN;
    }

    public double getScoreFactor() {
        return isWhite() ? 1 : -1;
    }
}
