package gui;

import map.MapGenerator;
import positions.EdgePosition;
import positions.NodePosition;
import positions.TilePosition;
import buildings.Building;
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
        for(Building building : map.getBuildings()) {
            Region newNode = new BuildingNode(tileWidth, building);
           // GuiPosition position = convertPosition(building.getPosition());
            //newNode.setLayoutX(position.getX());
           // newNode.setLayoutY(position.getY());
           // newNode.set
            this.getChildren().add(newNode);
        }
    }

    private GuiPosition convertPosition(TilePosition tilePosition) {
        double x = (tilePosition.getX() * 2 + tilePosition.getY()) * tileWidth * 0.35;
        double y = tilePosition.getY() * tileWidth * 0.6;
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
                x = tileWidth * 0.38 + (edgePosition.getX() * 2 + edgePosition.getY()) * tileWidth * 0.35;
                y = tileWidth * 0.09 + edgePosition.getY() * tileWidth * 0.6;
            }
            case B -> {
                x = tileWidth * 0.38 + (edgePosition.getX() * 2 + edgePosition.getY()) * tileWidth * 0.35;
                y = tileWidth * 0.69 + edgePosition.getY() * tileWidth * 0.6;
            }
            case C -> {
                x = tileWidth * -0.14 +(edgePosition.getX() * 2 + edgePosition.getY()) * tileWidth * 0.35;
                y = tileWidth * 0.39 + edgePosition.getY() * tileWidth * 0.6;
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
