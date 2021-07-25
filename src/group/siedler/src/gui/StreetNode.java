package gui;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import positions.EdgePosition;
import positions.PositionedObject;
import streets.Street;
import streets.StreetType;
import tiles.NeutralTile;
import tiles.ResourceTile;

import java.util.Optional;

public class StreetNode extends Group {
    protected Optional<Street> street = Optional.empty();
    protected EdgePosition position;
    protected StreetType type = StreetType.ROAD;

    public static final double angle30degree = Math.PI / 6;

    public StreetNode(EdgePosition position) {
        this.position = position;
        refreshOutput();
    }

    public StreetNode(EdgePosition position, StreetType type) {
        this.position = position;
        this.type = type;
        refreshOutput();
    }

    public StreetNode(PositionedObject<Street, EdgePosition> street) {
        this.street = Optional.of(street.getObject());
        this.type = street.getObject().getType();
        this.position = street.getPosition();
        refreshOutput();
    }

    private void refreshOutput() {
        this.getChildren().clear();

        double width = 30;
        double height = 7;

        Rectangle rectangle = new Rectangle(width, height);

        Color fillColor = street.isEmpty() ? Color.TRANSPARENT : street.get().getColor().getColor();

        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(5.0, -6.5,
                25.0, 3.5,
                5.0, 13.5);

        this.setRotate(30 + 120 * position.getZ().ordinal());

        if(street.isPresent() && street.get().getType() == StreetType.SHIP) {
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
        return street;
    }

    public void setStreet(Optional<Street> street) {
        this.street = street;
    }
}
