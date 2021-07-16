package buildings;

import materials.MaterialSet;
import materials.MaterialType;
import positions.NodePosition;
import player.PlayerColor;
import streets.StreetType;

public class Building {
    private NodePosition position;
    private BuildingType type;
    private PlayerColor color;

    public Building(NodePosition position, PlayerColor color) {
        this.position = position;
        this.color = color;
        this.type = BuildingType.VILLAGE;
    }

    public void upgrade() {
        type = BuildingType.TOWN;
    }

    public NodePosition getPosition() {
        return position;
    }

    public void setPosition(NodePosition position) {
        this.position = position;
    }

    public BuildingType getType() {
        return type;
    }

    public void setType(BuildingType type) {
        this.type = type;
    }

    public PlayerColor getColor() {
        return color;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }

    public static MaterialSet getCost(BuildingType type) {
        MaterialSet materials = new MaterialSet();
        switch (type) {
            case VILLAGE -> {
                materials.addResources(MaterialType.WOOD, 1);
                materials.addResources(MaterialType.CLAY, 1);
                materials.addResources(MaterialType.WHEAT, 1);
                materials.addResources(MaterialType.WOOL, 1);
            }
            case TOWN -> {
                materials.addResources(MaterialType.ORE, 2);
                materials.addResources(MaterialType.WHEAT, 3);
            }
        }
        return materials;
    }
}
