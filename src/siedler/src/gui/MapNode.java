package gui;

import core.map.MapGenerator;
import core.map.Tile;
import core.positions.TilePosition;
import javafx.scene.layout.Region;
import core.map.Map;

public class MapNode extends Region {
    private Map map = MapGenerator.generateBasicMap();

    private double tileWidth = 100;

    private double xOffset = 150;
    private double yOffset = 150;


    public MapNode() {
        refreshOutput();
    }

    public MapNode(Map map) {
        this.map = map;
        refreshOutput();
    }

    public void refreshOutput() {
        for(Tile tile : map.getTiles()) {
            Region newNode = new TileNode(tileWidth, tile);
            GuiPosition position = convertPosition(tile.getPosition());
            newNode.setLayoutX(position.getX());
            newNode.setLayoutY(position.getY());
            this.getChildren().add(newNode);
        }
    }

    private GuiPosition convertPosition(TilePosition tilePosition) {
        double x = xOffset + (tilePosition.getX() * 2 + tilePosition.getY()) * tileWidth * 0.35;
        double y = yOffset + tilePosition.getY() * tileWidth * 0.6;
        return new GuiPosition(x,y);
    }
}
