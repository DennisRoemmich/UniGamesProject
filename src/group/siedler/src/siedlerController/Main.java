package siedlerController;

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
            EdgePosition position = new EdgePosition(x,y,zCord);
            Street street = new Street(position, StreetType.ROAD, PlayerColor.BLUE);
            mapNode.getMap().addStreet(street);
        }
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
