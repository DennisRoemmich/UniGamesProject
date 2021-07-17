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
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(new File("src/gui/SiedlerGUI.fxml").toURI().toURL());
        Parent root = loader.load();
        Scene scene = new Scene(root, 700,499.1);
        primaryStage.setTitle("Die Siedler von Konstanz");
        primaryStage.setScene(scene);
        primaryStage.minHeightProperty().bind(primaryStage.widthProperty().multiply(0.713));
        primaryStage.maxHeightProperty().bind(primaryStage.widthProperty().multiply(0.713));
        primaryStage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                primaryStage.setMaximized(false);
            }
        });
        //primaryStage.minWidthProperty().set(700);
        //primaryStage.minHeightProperty().set(500);
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);

    }
}
