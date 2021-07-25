package player;

import javafx.scene.paint.Color;

public enum PlayerColor {
    BLUE, GREEN, YELLOW, RED, PURPLE, BLACK, WHITE, PINK, ORANGE, BROWN, GREY, CYAN, LIME;

    public Color getColor() {
        return switch (this) {
            case BLUE -> Color.BLUE;
            case GREEN -> Color.GREEN;
            case YELLOW -> Color.YELLOW;
            case RED ->  Color.RED;
            case PURPLE ->  Color.PURPLE;
            case BLACK ->  Color.BLACK;
            case WHITE ->  Color.WHITE;
            case PINK -> Color.PINK;
            case ORANGE -> Color.ORANGE;
            case BROWN -> Color.BROWN;
            case GREY -> Color.GREY;
            case CYAN -> Color.CYAN;
            case LIME -> Color.LIME;
        };
    }

}
