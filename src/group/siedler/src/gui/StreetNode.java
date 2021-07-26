package gui;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import positions.EdgePosition;
import positions.PositionedObject;
import streets.Street;
import streets.StreetType;
import java.util.Optional;

public class StreetNode extends Group {
    protected Optional<Street> mStreet = Optional.empty();
    protected EdgePosition mPosition;
    protected StreetType mType = StreetType.ROAD;

    public static final double DEGREE_ANGLE = Math.PI / 6; //30 degrees

    public StreetNode(EdgePosition position) {
        this.mPosition = position;
        refreshOutput();
    }

    public StreetNode(EdgePosition position, StreetType type) {
        this.mPosition = position;
        this.mType = type;
        refreshOutput();
    }

    public StreetNode(PositionedObject<Street, EdgePosition> street) {
        this.mStreet = Optional.of(street.getObject());
        this.mType = street.getObject().getType();
        this.mPosition = street.getPosition();
        refreshOutput();
    }

    private void refreshOutput() {
        this.getChildren().clear();

        double width = 30;
        double height = 7;

        Rectangle rectangle = new Rectangle(width, height);

        Color fillColor = mStreet.isEmpty() ? Color.TRANSPARENT : mStreet.get().getColor().getColor();

        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(5.0, -6.5,
                25.0, 3.5,
                5.0, 13.5);

        this.setRotate(30.0 + 120 * mPosition.getZ().ordinal());

        if (mStreet.isPresent() && mStreet.get().getType() == StreetType.SHIP) {
            triangle.setFill(fillColor);
            triangle.setStroke(Color.BLACK);
            rectangle.setFill(Color.TRANSPARENT);
            rectangle.setStroke(Color.TRANSPARENT);
        } else {
            rectangle.setStroke(Color.BLACK);
            rectangle.setFill(fillColor);
            triangle.setFill(Color.TRANSPARENT);
            triangle.setStroke(Color.TRANSPARENT);
        }

        this.getChildren().add(rectangle);
        this.getChildren().add(triangle);

    }

    public Optional<Street> getStreet() {
        return mStreet;
    }

    public void setStreet(Optional<Street> street) {
        this.mStreet = street;
    }
}
