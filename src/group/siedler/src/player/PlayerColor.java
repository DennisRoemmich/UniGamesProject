package player;

import java.util.Arrays;
import java.util.List;

import javafx.scene.paint.Color;

/**
 * Defines the different colors the players can have. 
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public enum PlayerColor {
    BLUE, GREEN, YELLOW, PURPLE, WHITE, RED, BLACK, PINK, ORANGE, BROWN, GREY, CYAN, LIME;

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
    
    public static List<PlayerColor> getColorList() {
    	return Arrays.asList(PlayerColor.values());
    }

}
