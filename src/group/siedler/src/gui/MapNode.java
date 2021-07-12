package gui;

import map.MapGenerator;
import positions.EdgePosition;
import positions.NodePosition;
import positions.TilePosition;
import javafx.scene.layout.Region;
import map.Map;
import streets.Street;
import tiles.Tile;

public class MapNode extends Region {
    private Map map = MapGenerator.generateBasicMap();

    private double tileWidth = 100;

    // TODO : Dynamically calculate offset
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
        for(Street street : map.getStreets()) {
            Region newNode = new RoadNode(tileWidth, street);
            GuiPosition position = convertPosition(street.getPosition());
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

    private GuiPosition convertPosition(NodePosition nodePosition) {
        // TODO : Implement calculation
        return null;
    }

    private GuiPosition convertPosition(EdgePosition edgePosition) {
        double x, y;
        switch(edgePosition.getZ()) {
            case A -> {
                x = (xOffset * 1.35) + (edgePosition.getX() * 2 + edgePosition.getY()) * tileWidth * 0.35;
                y = (yOffset * 1.12) + edgePosition.getY() * tileWidth * 0.6;
            }
            case B -> {
                x = (xOffset * 1.35) + (edgePosition.getX() * 2 + edgePosition.getY()) * tileWidth * 0.35;
                y = (yOffset * 1.52) + edgePosition.getY() * tileWidth * 0.6;
            }
            case C -> {
                x = xOffset + (edgePosition.getX() * 2 + edgePosition.getY()) * tileWidth * 0.35;
                y = (yOffset * 1.32) + edgePosition.getY() * tileWidth * 0.6;
            }
            default -> {
                x = 0;
                y = 0;
            }
        }
        return new GuiPosition(x,y);
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public double getTileWidth() {
        return tileWidth;
    }

    public void setTileWidth(double tileWidth) {
        this.tileWidth = tileWidth;
    }

    public double getxOffset() {
        return xOffset;
    }

    public void setxOffset(double xOffset) {
        this.xOffset = xOffset;
    }

    public double getyOffset() {
        return yOffset;
    }

    public void setyOffset(double yOffset) {
        this.yOffset = yOffset;
    }
}
