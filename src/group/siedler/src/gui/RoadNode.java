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
import streets.Street;
import tiles.NeutralTile;
import tiles.ResourceTile;

import java.util.Optional;

public class RoadNode extends Region  {
    protected Optional<Street> street = Optional.empty();
    protected double tileWidth = 100;

    public static final double angle30degree = Math.PI / 6;

    public RoadNode() {
        refreshOutput();
    }

    public RoadNode(double width) {
        this.tileWidth = width;
        refreshOutput();
    }

    public  RoadNode(double width, Street street) {
        this.tileWidth = width;
        this.street = Optional.of(street);
        refreshOutput();
    }

    private void refreshOutput() {

        if(street.isEmpty()) {
            return;
        }

        this.getChildren().clear();

        double width = tileWidth * 0.3;
        double height = tileWidth * 0.05;

        Rectangle rectangle = new Rectangle(width, height);

        rectangle.setRotate(30 + 120 * street.get().getPosition().getZ().ordinal());

        this.getChildren().add(rectangle);
        rectangle.setStroke(Color.BLACK);

        switch (street.get().getColor()) {
            case BLUE -> {
                rectangle.setFill(Color.BLUE);
            }
            case GREEN -> {
                rectangle.setFill(Color.GREEN);
            }
            case YELLOW -> {
                rectangle.setFill(Color.YELLOW);
            }
            case RED -> {
                rectangle.setFill(Color.RED);
            }
            case PURPLE -> {
                rectangle.setFill(Color.PURPLE);
            }
            case BLACK -> {
                rectangle.setFill(Color.BLACK);
            }
            case WHITE -> {
                rectangle.setFill(Color.WHITE);
            }
        }
    }

    public Optional<Street> getStreet() {
        return street;
    }

    public void setStreet(Optional<Street> street) {
        this.street = street;
    }
}
