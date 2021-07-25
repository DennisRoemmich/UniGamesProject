package buildings;

import materials.MaterialSet;
import materials.MaterialType;
import positions.NodePosition;
import player.PlayerColor;

public class Building {
    private NodePosition mPosition;
    private BuildingType mType;
    private PlayerColor mColor;

    public Building(NodePosition position, PlayerColor color) {
        this.mPosition = position;
        this.mColor = color;
        this.mType = BuildingType.VILLAGE;
    }

    public void upgrade() {
        mType = BuildingType.TOWN;
    }

    public NodePosition getPosition() {
        return mPosition;
    }

    public void setPosition(NodePosition position) {
        this.mPosition = position;
    }

    public BuildingType getType() {
        return mType;
    }

    public void setType(BuildingType type) {
        this.mType = type;
    }

    public PlayerColor getColor() {
        return mColor;
    }

    public void setColor(PlayerColor color) {
        this.mColor = color;
    }

    public static MaterialSet getCost(BuildingType type) {
        MaterialSet materials = new MaterialSet();
        if(type == BuildingType.VILLAGE) {
            materials.addResources(MaterialType.WOOD, 1);
            materials.addResources(MaterialType.CLAY, 1);
            materials.addResources(MaterialType.WHEAT, 1);
            materials.addResources(MaterialType.WOOL, 1);
        } else {
            materials.addResources(MaterialType.ORE, 2);
            materials.addResources(MaterialType.WHEAT, 3);
        }
        return materials;
    }
}
