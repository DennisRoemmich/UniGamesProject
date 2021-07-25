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
import java.util.Optional;

public class MapNode extends Group {
    private Optional<Map> map;
    private SiedlerEventHandler eventHandler;

    // TODO : Dynamically calculate offset
    private double xOffset = 150;
    private double yOffset = 150;

    public MapNode(Controller controller) {
        this.eventHandler = controller;
        setMap(controller.getMap());
        refreshOutput();
    }

    public void refreshOutput() {
        getChildren().clear();
        if(map.isEmpty()) {
            return;
        }
        BurglarNode burglarNode = new BurglarNode();
        GuiPosition burglarGui = GuiPosition.valueOf(map.get().getBurglarPosition());
        burglarNode.setLayoutX(burglarGui.getX());
        burglarNode.setLayoutY(burglarGui.getY());
        for(PositionedTile tile : map.get().getTiles()) {
            Group newNode = new TileNode(100, tile.getObject());
            GuiPosition position = GuiPosition.valueOf(tile.getPosition());
            TileClickedEventHandler tileEventHandler = new TileClickedEventHandler(tile.getPosition(), eventHandler);
            newNode.setLayoutX(position.getX());
            newNode.setLayoutY(position.getY());
            newNode.setOnMouseClicked(tileEventHandler);
            this.getChildren().add(newNode);
        }
        for(PositionedStreet street : map.get().getStreets()) {
            Group newNode = new StreetNode(street);
            GuiPosition position = GuiPosition.valueOf(street.getPosition());
            newNode.setLayoutX(position.getX());
            newNode.setLayoutY(position.getY());
            this.getChildren().add(newNode);
        }
        for(Building building : map.get().getBuildings()) {
            Group newNode = new BuildingNode(building);
            GuiPosition position = GuiPosition.valueOf(building.getPosition());
            newNode.setLayoutX(position.getX());
            newNode.setLayoutY(position.getY());
            this.getChildren().add(newNode);
        }

        getChildren().add(burglarNode);
    }

    public Optional<Map> getMap() {
        return map;
    }

    public void setMap(Map map) {
        if(map != null) {
            this.map = Optional.of(map);
        } else {
            this.map = Optional.empty();
        }
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
        if(map.isPresent()) {
            var possibleBuildings = BuildRules.getValidNodePositions(map.get(), color, type);
            addBuildingPlaceholders(possibleBuildings, type);
        }
    }

    public void addStreetPlaceholders(PlayerColor color, StreetType type) {
        if(map.isPresent()) {
            var possibleStreets = BuildRules.getValidEdgePositions(map.get(), color, type);
            addStreetPlaceholders(possibleStreets, type);
        }
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
