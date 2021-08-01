package siedler.gui;

import buildings.BuildingType;
import javafx.scene.Group;
import map.BuildRules;
import player.PlayerColor;
import positions.EdgePosition;
import positions.NodePosition;
import siedler.controller.Controller;
import buildings.Building;
import map.Map;
import streets.PositionedStreet;
import streets.Street;
import streets.StreetType;
import tiles.PositionedTile;
import java.util.List;
import java.util.Optional;

/**
 * Builds the whole map with all of its elements.
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public class MapNode extends Group {
    private Optional<Map> mMap;
    private SiedlerEventHandler mEventHandler;

    public MapNode(Controller controller) {
        this.mEventHandler = controller;
        setMap(controller.getMap());
        refreshOutput();
    }

    public void refreshOutput() {
        getChildren().clear();
        if (mMap.isEmpty()) {
            return;
        }
        BurglarNode burglarNode = new BurglarNode();
        GuiPosition burglarGui = GuiPosition.valueOf(mMap.get().getBurglarPosition());
        burglarNode.setLayoutX(burglarGui.getX());
        burglarNode.setLayoutY(burglarGui.getY());
        for (PositionedTile tile : mMap.get().getTiles()) {
            Group newNode = new TileNode(tile.getObject());
            GuiPosition position = GuiPosition.valueOf(tile.getPosition());
            TileClickedEventHandler tileEventHandler = new TileClickedEventHandler(tile.getPosition(), mEventHandler);
            newNode.setLayoutX(position.getX());
            newNode.setLayoutY(position.getY());
            newNode.setOnMouseClicked(tileEventHandler);
            this.getChildren().add(newNode);
        }
        for (PositionedStreet street : mMap.get().getStreets()) {
            Group newNode = new StreetNode(street);
            GuiPosition position = GuiPosition.valueOf(street.getPosition());
            newNode.setLayoutX(position.getX());
            newNode.setLayoutY(position.getY());
            this.getChildren().add(newNode);
        }
        for (Building building : mMap.get().getBuildings()) {
            Group newNode = new BuildingNode(building);
            GuiPosition position = GuiPosition.valueOf(building.getPosition());
            newNode.setLayoutX(position.getX());
            newNode.setLayoutY(position.getY());
            this.getChildren().add(newNode);
        }

        getChildren().add(burglarNode);
    }

    public Optional<Map> getMap() {
        return mMap;
    }

    public void setMap(Map map) {
        if (map != null) {
            this.mMap = Optional.of(map);
        } else {
            this.mMap = Optional.empty();
        }
    }

    public void addPlaceholderNodes(Controller controller) {
        for (StreetType type : StreetType.values()) {
            if (controller.getCurrentPlayerHand().isSuperset(Street.getCost(type))) {
                addStreetPlaceholders(controller.getCurrentPlayerColor(), type);
            }
        }
        for (BuildingType type : BuildingType.values()) {
            if (controller.getCurrentPlayerHand().isSuperset(Building.getCost(type))) {
                addBuildingPlaceholders(controller.getCurrentPlayerColor(), type);
            }
        }
    }

    public void addBuildingPlaceholders(PlayerColor color, BuildingType type) {
        if (mMap.isPresent()) {
            var possibleBuildings = BuildRules.getValidNodePositions(mMap.get(), color, type);
            addBuildingPlaceholders(possibleBuildings);
        }
    }
    
    public void addBuildingPlaceholders(List<NodePosition> positions) {
        for (NodePosition nodePosition : positions) {
            BuildingNode buildingNode = new BuildingNode(nodePosition);
            GuiPosition guiPosition = GuiPosition.valueOf(nodePosition);
            buildingNode.setLayoutX(guiPosition.getX());
            buildingNode.setLayoutY(guiPosition.getY());
            BuilderPlaceholderEventHandler buildingEventHandler;
            buildingEventHandler = new BuilderPlaceholderEventHandler(mEventHandler, nodePosition);
            buildingNode.setOnMouseClicked(buildingEventHandler);
            this.getChildren().add(buildingNode);
        }
    }

    public void addStreetPlaceholders(PlayerColor color, StreetType type) {
        if (mMap.isPresent()) {
            var possibleStreets = BuildRules.getValidEdgePositions(mMap.get(), color, type);
            addStreetPlaceholders(possibleStreets, type);
        }
    }

    public void addStreetPlaceholders(List<EdgePosition> positions, StreetType type) {
        for (EdgePosition edgePosition : positions) {
            StreetNode roadNode = new StreetNode(edgePosition, type);
            GuiPosition guiPosition = GuiPosition.valueOf(edgePosition);
            roadNode.setLayoutX(guiPosition.getX());
            roadNode.setLayoutY(guiPosition.getY());
            StreetPlaceholderEventHandler streetEventHandler;
            streetEventHandler = new StreetPlaceholderEventHandler(mEventHandler, edgePosition);
            roadNode.setOnMouseClicked(streetEventHandler);
            this.getChildren().add(roadNode);
        }
    }
}
