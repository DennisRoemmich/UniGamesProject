package tiles;

import positions.PositionedObject;
import positions.TilePosition;

import javax.print.attribute.standard.JobStateReason;

public class PositionedTile extends PositionedObject<Tile, TilePosition> {

    public PositionedTile(Tile tile, TilePosition position) {
        super(tile, position);
    }

}
