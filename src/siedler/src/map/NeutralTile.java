package map;

import map.Tile;
import positions.TilePosition;

public class NeutralTile extends Tile {
    private boolean isWater;

    public NeutralTile(TilePosition position, boolean isWater) {
        super(position);
    }

    public boolean isWater() {
        return isWater;
    }

    @Override
    public String toString() {
        return (isWater ? "WATER" : "DESERT") + " @ " + position;
    }
}
