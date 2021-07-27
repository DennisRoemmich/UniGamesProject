package streets;

/**
 * Represents a street together with its position.
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
import positions.EdgePosition;
import positions.PositionedObject;

public class PositionedStreet extends PositionedObject<Street, EdgePosition> {

    public PositionedStreet(Street object, EdgePosition position) {
        super(object, position);
    }
}
