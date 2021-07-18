package materials;

import javafx.scene.paint.Color;

public enum MaterialType {
    WOOD, CLAY, WHEAT, WOOL, ORE;

    public Color getColor() {
        return switch (this) {
            case WOOD -> Color.SADDLEBROWN;
            case CLAY -> Color.TOMATO;
            case WHEAT -> Color.GOLD;
            case WOOL -> Color.LIMEGREEN;
            case ORE -> Color.DARKGREY;
        };
    }
}
