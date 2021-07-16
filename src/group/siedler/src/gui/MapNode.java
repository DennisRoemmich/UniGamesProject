package gui;

import buildings.BuildingType;
import map.BuildRules;
import map.MapGenerator;
import player.PlayerColor;
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
            Region newNode = new TileNode(100, tile);
            GuiPosition position = convertPosition(tile.getPosition());
            newNode.setLayoutX(position.getX());
            newNode.setLayoutY(position.getY());
            this.getChildren().add(newNode);
        }
        for(Street street : map.getStreets()) {
            Region newNode = new RoadNode(street);
            GuiPosition position = convertPosition(street.getPosition());
            newNode.setLayoutX(position.getX());
            newNode.setLayoutY(position.getY());
            this.getChildren().add(newNode);
        }
        for(Building building : map.getBuildings()) {
            Region newNode = new BuildingNode(building);
            GuiPosition position = convertPosition(building.getPosition());
            newNode.setLayoutX(position.getX());
            newNode.setLayoutY(position.getY());
            this.getChildren().add(newNode);
        }
    }

    private GuiPosition convertPosition(TilePosition tilePosition) {
        double x = (tilePosition.getX() * 2 + tilePosition.getY()) * 35;
        double y = tilePosition.getY() * 60;
        return new GuiPosition(x,y);
    }

    private GuiPosition convertPosition(NodePosition nodePosition) {
    	double x, y;
    	if(nodePosition.isZ()) {
            x = 1 + (nodePosition.getX() * 2 + nodePosition.getY()) * 35;
            y = 20 + nodePosition.getY() * 60;
        } else {
            x = 71 + (nodePosition.getX() * 2 + nodePosition.getY()) * 35;
            y = 62 + nodePosition.getY() * 60;
        }
        return new GuiPosition(x,y);
    }

    private GuiPosition convertPosition(EdgePosition edgePosition) {
        double x, y;
        switch(edgePosition.getZ()) {
            case A -> {
                x = 38 + (edgePosition.getX() * 2 + edgePosition.getY()) * 35;
                y = 9 + edgePosition.getY() * 60;
            }
            case B -> {
                x = 38 + (edgePosition.getX() * 2 + edgePosition.getY()) * 35;
                y = 69 + edgePosition.getY() * 60;
            }
            case C -> {
                x = 100 * -0.14 +(edgePosition.getX() * 2 + edgePosition.getY()) * 35;
                y = 39 + edgePosition.getY() * 60;
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

    public void clearPlaceholderNodes() {
        refreshOutput();
    }

    public void addPlaceholderNodes(PlayerColor color) {
        var possibleStreets = BuildRules.getValidEdgePositions(map, color);
        var possibleBuildings = BuildRules.getValidNodePositions(map, color, BuildingType.VILLAGE);
        possibleBuildings.addAll(BuildRules.getValidNodePositions(map, color, BuildingType.TOWN));
        for(EdgePosition position : possibleStreets) {
            RoadNode roadNode = new RoadNode(position);
            this.getChildren().add(roadNode);
        }
        for(NodePosition position : possibleBuildings) {
            BuildingNode buildingNode = new BuildingNode(position);
            this.getChildren().add(buildingNode);
        }
    }
}
