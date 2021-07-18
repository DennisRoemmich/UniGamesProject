package siedlerController;

import buildings.Building;
import buildings.BuildingType;
import gui.FXController;
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
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        ClassLoader classLoader = getClass().getClassLoader();
        String fileName = "resources/SiedlerGUI.fxml";
        var resource = classLoader.getResource(fileName);
        if(resource == null) {
            System.out.println("\n=== URL is null ===\n");
        }
        FXMLLoader loader = new FXMLLoader(resource);
        Parent root = loader.load();
        Scene scene = new Scene(root, 1200,900);
        
        //This line of code just fixed everything.. *ALL HAIL THIS LINE OF CODE*
        scene.getRoot().requestFocus();
        
        primaryStage.setTitle("Die Siedler von Konstanz");
        primaryStage.setScene(scene);
       // primaryStage.minWidthProperty().set(700);

        /*
        primaryStage.minHeightProperty().bind(primaryStage.widthProperty().multiply(0.713));
        primaryStage.maxHeightProperty().bind(primaryStage.widthProperty().multiply(0.713));
        primaryStage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                primaryStage.setMaximized(false);
            }
        });
*/
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
