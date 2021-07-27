package core;

import core.positioning.Direction;
import core.positioning.Rank;

public enum Color {
    BLACK(false), WHITE(true);

    private boolean value;

    Color(boolean value) {
        this.value = value;
    }

    public Color getContrary() {
        if(this == BLACK) {
            return WHITE;
        } else {
            return BLACK;
        }
    }

    public boolean isWhite() {
        return value;
    }

    public static Color valueOf(boolean value) {
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
