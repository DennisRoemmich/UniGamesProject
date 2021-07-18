package gui;

import javafx.collections.ObservableList;
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
import streets.Street;
import tiles.NeutralTile;
import tiles.ResourceTile;

import java.util.Optional;

public class RoadNode extends Region  {
    protected Optional<Street> street = Optional.empty();
    protected EdgePosition position;

    public static final double angle30degree = Math.PI / 6;

    public RoadNode(EdgePosition position) {
        this.position = position;
        refreshOutput();
    }

    public  RoadNode(Street street) {
        this.street = Optional.of(street);
        this.position = street.getPosition();
        refreshOutput();
    }

    private void refreshOutput() {

        double width = 30;
        double height = 5;

        Rectangle rectangle = new Rectangle(width, height);

        rectangle.setStroke(Color.BLACK);

        if(street.isEmpty()) {
            rectangle.setFill(Color.TRANSPARENT);
            //rectangle.setStrokeWidth(3);
        } else {
            rectangle.setFill(street.get().getColor().getColor());
            //rectangle.setStrokeWidth(2);
        }

        rectangle.setRotate(30 + 120 * position.getZ().ordinal());

        this.getChildren().clear();
        this.getChildren().add(rectangle);

    }

    public Optional<Street> getStreet() {
        return street;
    }

    public void setStreet(Optional<Street> street) {
        this.street = street;
    }
}
