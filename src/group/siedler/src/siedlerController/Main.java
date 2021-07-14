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

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(new File("src/gui/SiedlerGUI.fxml").toURI().toURL());

        Parent root = loader.load();
        Scene scene = new Scene(root, 1000,600);
        primaryStage.setTitle("hi");
        primaryStage.setScene(scene);
        primaryStage.show();

        /* Pane mainPane = new Pane();
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
        mainPane.getChildren().add(mapNode);
        primaryStage.setTitle("Die Siedler von Konstanz");
        primaryStage.setScene(new Scene(mainPane, 700, 500));
        primaryStage.show();*/
    }


    public static void main(String[] args) {
        launch(args);
    }
}
