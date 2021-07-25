package tiles;

import positions.TilePosition;

public class NeutralTile extends Tile {

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
