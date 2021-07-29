package streets;

import positions.EdgePosition;
import positions.PositionedObject;

/**
 * Represents a street together with its position.
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */

public class PositionedStreet extends PositionedObject<Street, EdgePosition> {

    public PositionedStreet(Street object, EdgePosition position) {
        super(object, position);
    }
}
