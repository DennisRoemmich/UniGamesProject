package tiles;

import positions.PositionedObject;
import positions.TilePosition;

/**
 * Represents Tile together with its tile position. 
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public class PositionedTile extends PositionedObject<Tile, TilePosition> {

    public PositionedTile(Tile tile, TilePosition position) {
        super(tile, position);
    }
}
