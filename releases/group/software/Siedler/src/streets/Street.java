package streets;

import materials.MaterialSet;
import materials.MaterialType;
import player.PlayerColor;

/**
 * Represents a street.
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public class Street {
    private StreetType mType;
    private PlayerColor mColor;

    public Street(StreetType type, PlayerColor color) {
        this.mType = type;
        this.mColor = color;
    }

    public StreetType getType() {
        return mType;
    }

    public void setType(StreetType type) {
        this.mType = type;
    }

    public PlayerColor getColor() {
        return mColor;
    }

    public void setColor(PlayerColor color) {
        this.mColor = color;
    }

    public static MaterialSet getCost(StreetType type) {
        MaterialSet materials = new MaterialSet();
        if (type == StreetType.ROAD) {
    		materials.addResources(MaterialType.WOOD, 1);
            materials.addResources(MaterialType.CLAY, 1);
        } else {
            materials.addResources(MaterialType.WOOD, 1);
            materials.addResources(MaterialType.WOOL, 1);
        }
        return materials;
    }

    @Override
    public String toString() {
        return mColor + " " + mType;
    }
}
