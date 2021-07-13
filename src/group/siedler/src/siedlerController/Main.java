package siedlerController;

import buildings.Building;
import buildings.BuildingType;
import gui.MapNode;
import gui.RoadNode;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import player.PlayerColor;
import positions.EdgePosition;
import positions.EdgePositionZCord;
import positions.NodePosition;
import streets.Street;
import streets.StreetType;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane mainPane = new Pane();
        MapNode mapNode = new MapNode();
        int x = 0;
        int y = 0;
        for(EdgePositionZCord zCord : EdgePositionZCord.values()) {
            EdgePosition positionEdge = new EdgePosition(x,y,zCord);
            Street street = new Street(positionEdge, StreetType.ROAD, PlayerColor.BLUE);
            mapNode.getMap().addStreet(street);         
        }
        
        NodePosition positionNode = new NodePosition(x, y, true);
        Building building = new Building(positionNode, PlayerColor.BLUE);
        mapNode.getMap().addBuilding(building);
        
        mapNode.refreshOutput();
        mapNode.setLayoutX(300);;
        mapNode.setLayoutY(150);
        mainPane.getChildren().add(mapNode);
        primaryStage.setTitle("Die Siedler von Konstanz");
        primaryStage.setScene(new Scene(mainPane, 700, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
