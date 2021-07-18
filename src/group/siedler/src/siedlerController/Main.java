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
import materials.MaterialType;
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

        FXMLLoader loader = new FXMLLoader(new File("./src/gui/SiedlerGUI.fxml").toURI().toURL());
        Parent root = loader.load();
        Scene scene = new Scene(root, 700,499.1);
        
        //This line of code just fixed everything.. *ALL HAIL THIS LINE OF CODE*
        scene.getRoot().requestFocus();

        scene.getRoot().setStyle("-fx-font-family: 'serif'");
        
        primaryStage.setTitle("Die Siedler von Konstanz");
        primaryStage.setScene(scene);
        primaryStage.minWidthProperty().set(700);
        primaryStage.minHeightProperty().bind(primaryStage.widthProperty().multiply(0.713));
        primaryStage.maxHeightProperty().bind(primaryStage.widthProperty().multiply(0.713));
        primaryStage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                primaryStage.setMaximized(false);
            }
        });

        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
