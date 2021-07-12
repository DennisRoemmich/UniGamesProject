package tiles;

import positions.TilePosition;

public abstract class Tile {
    protected TilePosition position;
    public Tile(TilePosition position) {
        this.position = position;
    }

    public TilePosition getPosition() {
        return position;
    }

    public int getHitnumber() {
        return -1; // -1 means as default -> tile without number
    }
}
