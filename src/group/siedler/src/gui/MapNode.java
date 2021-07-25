package gui;

import buildings.BuildingType;
import javafx.scene.Group;
import map.BuildRules;
import map.MapGenerator;
import player.PlayerColor;
import positions.EdgePosition;
import positions.NodePosition;
import positions.TilePosition;
import buildings.Building;
import javafx.scene.layout.Region;
import map.Map;
import siedlerController.Controller;
import streets.PositionedStreet;
import streets.Street;
import streets.StreetType;
import tiles.PositionedTile;

import java.util.List;

public class MapNode extends Group {
    private Map map = MapGenerator.generateVariableMap(7, 5);
    private SiedlerEventHandler eventHandler;

    // TODO : Dynamically calculate offset
    private double xOffset = 150;
    private double yOffset = 150;

    public MapNode(Controller controller) {
        this.eventHandler = controller;
        this.map = controller.getMap();
        refreshOutput();
    }

    public MapNode(SiedlerEventHandler eventHandler) {
        this.eventHandler = eventHandler;
        refreshOutput();
    }

    public MapNode(Map map, SiedlerEventHandler eventHandler) {
        this.map = map;
        this.eventHandler = eventHandler;
        refreshOutput();
    }

    public void refreshOutput() {
        getChildren().clear();
        BurglarNode burglarNode = new BurglarNode();
        GuiPosition burglarGui = GuiPosition.valueOf(map.getBurglarPosition());
        burglarNode.setLayoutX(burglarGui.getX());
        burglarNode.setLayoutY(burglarGui.getY());
        for(PositionedTile tile : map.getTiles()) {
            Group newNode = new TileNode(100, tile.getObject());
            GuiPosition position = GuiPosition.valueOf(tile.getPosition());
            TileClickedEventHandler tileEventHandler = new TileClickedEventHandler(tile.getPosition(), eventHandler);
            newNode.setLayoutX(position.getX());
            newNode.setLayoutY(position.getY());
            newNode.setOnMouseClicked(tileEventHandler);
            this.getChildren().add(newNode);
        }
        for(PositionedStreet street : map.getStreets()) {
            Group newNode = new StreetNode(street);
            GuiPosition position = GuiPosition.valueOf(street.getPosition());
            newNode.setLayoutX(position.getX());
            newNode.setLayoutY(position.getY());
            this.getChildren().add(newNode);
        }
        for(Building building : map.getBuildings()) {
            Group newNode = new BuildingNode(building);
            GuiPosition position = GuiPosition.valueOf(building.getPosition());
            newNode.setLayoutX(position.getX());
            newNode.setLayoutY(position.getY());
            this.getChildren().add(newNode);
        }

        getChildren().add(burglarNode);
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void addPlaceholderNodes(Controller controller) {
        for(StreetType type : StreetType.values()) {
            if (controller.getCurrentPlayerHand().isSuperset(Street.getCost(type))) {
                addStreetPlaceholders(controller.getCurrentPlayerColor(), type);
            }
        }
        for(BuildingType type : BuildingType.values()) {
            if (controller.getCurrentPlayerHand().isSuperset(Building.getCost(type))) {
                addBuildingPlaceholders(controller.getCurrentPlayerColor(), type);
            }
        }
    }

    public void addBuildingPlaceholders(PlayerColor color, BuildingType type) {
        var possibleBuildings = BuildRules.getValidNodePositions(map, color, type);
        addBuildingPlaceholders(possibleBuildings, type);
    }

    public void addStreetPlaceholders(PlayerColor color, StreetType type) {
        var possibleStreets = BuildRules.getValidEdgePositions(map, color, type);
        addStreetPlaceholders(possibleStreets, type);
    }

    public void addStreetPlaceholders(List<EdgePosition> positions, StreetType type) {
        for (EdgePosition edgePosition : positions) {
            StreetNode roadNode = new StreetNode(edgePosition, type);
            GuiPosition guiPosition = GuiPosition.valueOf(edgePosition);
            roadNode.setLayoutX(guiPosition.getX());
            roadNode.setLayoutY(guiPosition.getY());
            StreetPlaceholderEventHandler streetEventHandler = new StreetPlaceholderEventHandler(eventHandler, edgePosition);
            roadNode.setOnMouseClicked(streetEventHandler);
            this.getChildren().add(roadNode);
        }
    }

    public void addBuildingPlaceholders(List<NodePosition> positions, BuildingType type) {
        for (NodePosition nodePosition : positions) {
            BuildingNode buildingNode = new BuildingNode(nodePosition);
            GuiPosition guiPosition = GuiPosition.valueOf(nodePosition);
            buildingNode.setLayoutX(guiPosition.getX());
            buildingNode.setLayoutY(guiPosition.getY());
            BuilderPlaceholderEventHandler buildingEventHandler = new BuilderPlaceholderEventHandler(eventHandler, nodePosition);
            buildingNode.setOnMouseClicked(buildingEventHandler);
            this.getChildren().add(buildingNode);
        }
    }
}
