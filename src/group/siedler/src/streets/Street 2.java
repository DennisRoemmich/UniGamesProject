package streets;

import materials.MaterialSet;
import materials.MaterialType;
import player.PlayerColor;
import positions.EdgePosition;

public class Street {
    private StreetType type;
    private PlayerColor color;

    public Street(StreetType type, PlayerColor color) {
        this.type = type;
        this.color = color;
    }

    public StreetType getType() {
        return type;
    }

    public void setType(StreetType type) {
        this.type = type;
    }

    public PlayerColor getColor() {
        return color;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }

    public static MaterialSet getCost(StreetType type) {
        MaterialSet materials = new MaterialSet();
        switch (type) {
            case ROAD -> {
                materials.addResources(MaterialType.WOOD, 1);
                materials.addResources(MaterialType.CLAY, 1);
            }
            case SHIP -> {
                materials.addResources(MaterialType.WOOD, 1);
                materials.addResources(MaterialType.WOOL, 1);
            }
        }
        return materials;
    }

    @Override
    public String toString() {
        return color + " " + type;
    }
}
