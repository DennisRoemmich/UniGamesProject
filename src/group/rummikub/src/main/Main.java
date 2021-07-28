package main;

import framework.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rummikub_controller.RummikubController;

import java.net.URL;

public class Main extends Application {

    Scene scene;


    ClassLoader classLoader = getClass().getClassLoader();
    URL resource = classLoader.getResource("rummikubGUI.fxml");

    @Override
    public void start(Stage primaryStage) throws Exception{

        var loader = new FXMLLoader(resource);

        Parent root = loader.load();

        var applicationName = "Rummikub Alpha";
        primaryStage.setTitle(applicationName);

        var appHeight = 1200;
        var appWidth = 1200;
        scene = new Scene(root, appHeight, appWidth);
        primaryStage.setScene(scene);
        primaryStage.show();

        Player fxController = loader.getController();

        initGameController(fxController);

    }



    public static void initGameController(Player fxController){

        var rController = new RummikubController();
        rController.addPlayer(fxController);

    }


    public static void main(String[] args) {

        launch(args);

    }
}





