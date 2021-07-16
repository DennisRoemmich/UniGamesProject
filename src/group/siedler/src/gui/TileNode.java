package gui;

import javafx.geometry.Pos;
import tiles.Tile;
import tiles.NeutralTile;
import tiles.ResourceTile;

import javafx.collections.ObservableList;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Optional;

public class TileNode extends Region {

    protected Optional<Tile> tile = Optional.empty();

    public static final double angle30degree = Math.PI / 6;

    public TileNode() {
        refreshOutput();
    }

    public  TileNode(double width, Tile tile) {
        this.tile = Optional.of(tile);
        refreshOutput();
    }

    private void refreshOutput() {
        this.getChildren().clear();

        StackPane stack = new StackPane();
        stack.setAlignment(Pos.CENTER);
        this.getChildren().add(stack);

        Polygon hexagonShape = new Polygon();

        double centerX = 50;
        double centerY = 50;
        double radius = 40;

        ObservableList<Double> list = hexagonShape.getPoints();
        // Add points to the polygon list
        for (int i = 0; i < 6; i++) {
            list.add(centerX + radius * Math.cos((1 + 2 * i) * angle30degree));
            list.add(centerY - radius * Math.sin((1 + 2 * i) * angle30degree));
        }

        stack.getChildren().add(hexagonShape);
        hexagonShape.setStroke(Color.BLACK);

        if(tile.isEmpty()) {
            hexagonShape.setFill(Color.TRANSPARENT);
        } else {
            Tile extractedTile = tile.get();
            try {
                NeutralTile neutralTile = (NeutralTile) extractedTile;
                if(neutralTile.isWater()) {
                    hexagonShape.setFill(Color.SKYBLUE);
                } else {
                    hexagonShape.setFill(Color.SANDYBROWN);
                }
                return;
            } catch (Exception e) {

            }
            try {
                ResourceTile resourceTile = (ResourceTile) extractedTile;
                Shape circle = new Circle(14, Color.BEIGE);
                circle.setStroke(Color.BLACK);
                stack.getChildren().add(circle);
                int hitnumber = resourceTile.getHitnumber();
                Font font = new Font("Tahoma", 20);
                Text hitnumberLabel = new Text(String.valueOf(hitnumber));
                hitnumberLabel.setTextAlignment(TextAlignment.CENTER);
                if(hitnumber == 6 || hitnumber == 8) {
                    hitnumberLabel.setFill(Color.INDIANRED);
                    hitnumberLabel.setStroke(Color.INDIANRED);
                }
                hitnumberLabel.setFont(font);
                stack.getChildren().add(hitnumberLabel);
                switch (resourceTile.getResourceType()) {
                    case WOOD -> {
                        hexagonShape.setFill(Color.SADDLEBROWN);
                    }
                    case CLAY -> {
                        hexagonShape.setFill(Color.TOMATO);
                    }
                    case WHEAT -> {
                        hexagonShape.setFill(Color.GOLD);
                    }
                    case WOOL -> {
                        hexagonShape.setFill(Color.LIMEGREEN);
                    }
                    case ORE -> {
                        hexagonShape.setFill(Color.DARKGREY);
                    }
                }
            } catch (Exception e) {

            }
        }
    }

    public Optional<Tile> getTile() {
        return tile;
    }

    public void setTile(Optional<Tile> tile) {
        this.tile = tile;
    }
}
