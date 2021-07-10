package map;

import player.PlayerColor;
import positions.NodePosition;

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
}
