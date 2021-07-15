package player;

import javafx.scene.paint.Color;

public enum PlayerColor {
    BLUE, GREEN, YELLOW, RED, PURPLE, BLACK, WHITE;

    public Color getColor() {
        return switch (this) {
            case BLUE -> Color.BLUE;
            case GREEN -> Color.GREEN;
            case YELLOW -> Color.YELLOW;
            case RED ->  Color.RED;
            case PURPLE ->  Color.PURPLE;
            case BLACK ->  Color.BLACK;
            case WHITE ->  Color.WHITE;
        };
    }

}
