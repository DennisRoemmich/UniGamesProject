package core.map;

import core.positions.TilePosition;

public class NeutralTile extends Tile {
    private boolean isWater;

    public NeutralTile(TilePosition position, boolean isWater) {
        super(position);
        this.isWater = isWater;
    }

    public boolean isWater() {
        return isWater;
    }

    @Override
    public String toString() {
        return (isWater ? "WATER" : "DESERT") + " @ " + position;
    }
}
