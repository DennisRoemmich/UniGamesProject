package tiles;

import positions.PositionedObject;
import positions.TilePosition;

public class PositionedTile extends PositionedObject<Tile, TilePosition> {

    public PositionedTile(Tile tile, TilePosition position) {
        super(tile, position);
    }
}
