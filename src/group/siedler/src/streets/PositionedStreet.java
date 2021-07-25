package streets;

import positions.EdgePosition;
import positions.PositionedObject;

public class PositionedStreet extends PositionedObject<Street, EdgePosition> {

    public PositionedStreet(Street object, EdgePosition position) {
        super(object, position);
    }

}
