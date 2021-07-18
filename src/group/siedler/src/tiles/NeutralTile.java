package tiles;

import positions.TilePosition;

public class NeutralTile extends Tile {
    private final boolean isWater;

    public NeutralTile(boolean isWater) {
        this.isWater = isWater;
    }

    public boolean isWater() {
        return isWater;
    }

    @Override
    public String toString() {
        return (isWater ? "WATER" : "DESERT") + "TILE";
    }
}
