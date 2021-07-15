package gui;

import buildings.Building;
import buildings.BuildingType;
import javafx.fxml.Initializable;

import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import map.BuildRules;
import map.Map;
import map.MapGenerator;
import player.PlayerColor;
import positions.EdgePosition;
import positions.EdgePositionZCord;
import positions.NodePosition;
import siedlerFramework.PrintToConsole;
import streets.Street;
import streets.StreetType;

import java.util.ResourceBundle;

public class FXController implements Initializable {

    @FXML
    private AnchorPane back;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Map map = MapGenerator.generateTestMap();
        MapNode mapNode = new MapNode();

        mapNode.setMap(map);
        mapNode.refreshOutput();
        mapNode.setLayoutX(300);
        mapNode.setLayoutY(150);

        back.getChildren().add(mapNode);

        var possibleStreets = BuildRules.getValidPositions(map, PlayerColor.BLUE);
        for(EdgePosition position : possibleStreets) {
            S
        }
        /*var possibleBuildings = BuildRules.getValidPositions(map, PlayerColor.BLUE, BuildingType.VILLAGE);
        while(possibleBuildings.size() != 0) {
            map.addBuilding(new Building(possibleBuildings.get(0), PlayerColor.BLUE));
            possibleBuildings = BuildRules.getValidPositions(map, PlayerColor.BLUE, BuildingType.VILLAGE);
        }*/
        mapNode.refreshOutput();
        PrintToConsole.println(possibleStreets.toString());
    }
}
