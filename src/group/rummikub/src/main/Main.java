package main;

import framework.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rummikub_controller.RummikubController;

import java.util.ArrayList;


public class Main extends Application {

    private final String APPLICATION_NAME = "Rummikub Alpha";
    private final int APP_WIDTH = 1200;
    private final int APP_HEIGHT = 1200;

    Stage stage;
    Scene scene;

    String fxmlURL = "../JavaFX/rummikubGUI.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(fxmlURL)
        );

        Parent root = loader.load();

        primaryStage.setTitle(APPLICATION_NAME);

        scene = new Scene(root, APP_HEIGHT, APP_WIDTH);
        primaryStage.setScene(scene);
        primaryStage.show();

        Player fxController = loader.getController();

        initGameController(fxController);

    }



    public static void initGameController(Player fxController){

        RummikubController rController = new RummikubController();
        rController.addPlayer(fxController);

    }


    public static void main(String[] args) {

        launch(args);

        // var test = new Test();

        //  test.maik();

        // test.andreas();

        //test.fer();

    }
}





