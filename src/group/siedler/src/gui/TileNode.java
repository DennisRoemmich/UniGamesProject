package gui;

import javafx.geometry.Pos;
import javafx.scene.Group;
import tiles.Tile;
import tiles.NeutralTile;
import tiles.ResourceTile;
import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import java.util.Optional;

/**
 * Represents every tile in the game.
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public class TileNode extends Group {

    protected Optional<Tile> mTile = Optional.empty();

    public static final double DEGREE_ANGLE = Math.PI / 6; //30 degrees

    public TileNode() {
        refreshOutput();
    }

    public  TileNode(Tile tile) {
        this.mTile = Optional.of(tile);
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
            list.add(centerX + radius * Math.cos((1 + 2 * i) * DEGREE_ANGLE));
            list.add(centerY - radius * Math.sin((1 + 2 * i) * DEGREE_ANGLE));
        }

        stack.getChildren().add(hexagonShape);
        hexagonShape.setStroke(Color.BLACK);

        if (mTile.isEmpty()) {
            hexagonShape.setFill(Color.TRANSPARENT);
        } else {
            Tile extractedTile = mTile.get();
            try {
                NeutralTile neutralTile = (NeutralTile) extractedTile;
                if (neutralTile.isWater()) {
                    hexagonShape.setFill(Color.SKYBLUE);
                } else {
                    hexagonShape.setFill(Color.SANDYBROWN);
                }
                return;
            } catch (Exception e) {
            	//Unused 
            }
            try {
                ResourceTile resourceTile = (ResourceTile) extractedTile;
                Shape circle = new Circle(16, Color.BEIGE);
                circle.setStroke(Color.BLACK);
                stack.getChildren().add(circle);
                int hitnumber = resourceTile.getHitnumber();
                Font font = new Font("Tahoma", getFontSize(hitnumber));
                Text hitnumberLabel = new Text(String.valueOf(hitnumber));
                hitnumberLabel.setTextAlignment(TextAlignment.CENTER);
                
                if (hitnumber == 6 || hitnumber == 8) {
                    Color hitnumberColor = Color.INDIANRED;
                    hitnumberLabel.setFill(hitnumberColor);
                    hitnumberLabel.setStroke(hitnumberColor);
                }
                hitnumberLabel.setFont(font);
                stack.getChildren().add(hitnumberLabel);
                
                switch (resourceTile.getResourceType()) {
                    case WOOD -> hexagonShape.setFill(Color.SADDLEBROWN);                    
                    case CLAY -> hexagonShape.setFill(Color.TOMATO);                                          
                    case WHEAT -> hexagonShape.setFill(Color.GOLD);                                         
                    case WOOL -> hexagonShape.setFill(Color.LIMEGREEN);                                          
                    case ORE -> hexagonShape.setFill(Color.DARKGREY);                                          
                }
            } catch (Exception e) {
            	//Unused
            }
        }
    }

    public static int getFontSize(int hitnumber) {
        return switch (hitnumber) {
            case 2, 12 -> 14;
            case 3, 11 -> 16;
            case 4, 10 -> 20;
            case 5, 9 -> 22;
            case 6, 8 -> 22;
            default -> 22;
        };
    }

    public Optional<Tile> getTile() {
        return mTile;
    }

    public void setTile(Optional<Tile> tile) {
        this.mTile = tile;
    }
}
