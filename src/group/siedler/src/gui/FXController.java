package gui;

import buildings.Building;
import javafx.fxml.Initializable;

import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import player.PlayerColor;
import positions.EdgePosition;
import positions.EdgePositionZCord;
import positions.NodePosition;
import streets.Street;
import streets.StreetType;

import java.util.ResourceBundle;

public class FXController implements Initializable {

    @FXML
    private AnchorPane back;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MapNode mapNode = new MapNode();
        int x = 0;
        int y = 0;
        for(EdgePositionZCord zCord : EdgePositionZCord.values()) {
            EdgePosition positionEdge = new EdgePosition(x,y,zCord);
            Street street = new Street(positionEdge, StreetType.ROAD, PlayerColor.BLUE);
            mapNode.getMap().addStreet(street);
        }

        //Initialize 2 test buildings
        NodePosition positionNode = new NodePosition(x, y, true);
        NodePosition positionNode2 = new NodePosition(x, y, false);
        Building building = new Building(positionNode, PlayerColor.BLUE);
        Building building2 = new Building(positionNode2, PlayerColor.BLUE);
        mapNode.getMap().addBuilding(building);
        mapNode.getMap().addBuilding(building2);

        mapNode.refreshOutput();
        mapNode.setLayoutX(300);
        mapNode.setLayoutY(150);
        back.getChildren().add(mapNode);
    }
}
