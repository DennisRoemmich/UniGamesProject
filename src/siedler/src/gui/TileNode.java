package gui;


import core.map.NeutralTile;
import core.map.ResourceTile;
import javafx.collections.ObservableList;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import core.map.Tile;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Optional;

public class TileNode extends Region {

    protected Optional<Tile> tile = Optional.empty();
    protected double width = 250;

    public static final double angle30degree = Math.PI / 6;

    public TileNode() {
        refreshOutput();
    }
    public TileNode(double width) {
        this.width = width;
        refreshOutput();
    }

    public  TileNode(double width, Tile tile) {
        this.width = width;
        this.tile = Optional.of(tile);
        refreshOutput();
    }

    private void refreshOutput() {
        this.getChildren().clear();

        Polygon hexagonShape = new Polygon();

        double centerX = width / 2;
        double centerY = width / 2;
        double radius = width * 0.4;

        ObservableList<Double> list = hexagonShape.getPoints();
        // Add points to the polygon list
        for (int i = 0; i < 6; i++) {
            list.add(centerX + radius * Math.cos((1 + 2 * i) * angle30degree));
            list.add(centerY - radius * Math.sin((1 + 2 * i) * angle30degree));
        }

        this.getChildren().add(hexagonShape);
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
                Shape circle = new Circle(width / 2 ,width / 2, 35 * width / 250, Color.BEIGE);
                circle.setStroke(Color.BLACK);
                this.getChildren().add(circle);
                int hitnumber = resourceTile.getHitnumber();
                Font font = new Font("Tahoma", 48 * width / 250);
                Text hitnumberLabel = new Text(111 * width / 250 ,143 * width / 250, String.valueOf(hitnumber));
                hitnumberLabel.setTextAlignment(TextAlignment.CENTER);
                if(hitnumber == 6 || hitnumber == 8) {
                    hitnumberLabel.setFill(Color.INDIANRED);
                    hitnumberLabel.setStroke(Color.INDIANRED);
                }
                hitnumberLabel.setFont(font);
                this.getChildren().add(hitnumberLabel);
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
