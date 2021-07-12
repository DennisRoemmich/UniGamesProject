package siedlerController;

import gui.MapNode;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane mainPane = new Pane();
        MapNode mapNode = new MapNode();
        mainPane.getChildren().add(mapNode);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(mainPane, 700, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
