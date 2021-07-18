package tiles;

import positions.TilePosition;

public class PositionedTile {
    private Tile tile;
    private TilePosition position;

    public PositionedTile(Tile tile, TilePosition position) {
        this.tile = tile;
        this.position = position;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public TilePosition getPosition() {
        return position;
    }

    public void setPosition(TilePosition position) {
        this.position = position;
    }

    public String toString() {
        return tile + " @ " + position;
    }
}
