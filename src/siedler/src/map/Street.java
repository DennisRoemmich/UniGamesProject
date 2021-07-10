package map;

import player.PlayerColor;
import positions.EdgePosition;

public class Street {
    private EdgePosition position;
    private StreetType type;
    private PlayerColor color;

    public Street(EdgePosition position, StreetType type, PlayerColor color) {
        this.position = position;
        this.type = type;
        this.color = color;
    }

    public EdgePosition getPosition() {
        return position;
    }

    public void setPosition(EdgePosition position) {
        this.position = position;
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
}
