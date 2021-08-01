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

    Scene mScene;


    ClassLoader mClassLoader = getClass().getClassLoader();
    URL mResource = mClassLoader.getResource("resources/rummikubGUI.fxml");

    @Override
    public void start(Stage primaryStage) throws Exception {

        var loader = new FXMLLoader(mResource);

        Parent root = loader.load();

        var applicationName = "Rummikub Alpha";
        primaryStage.setTitle(applicationName);

        var appHeight = 1200;
        var appWidth = 1200;
        mScene = new Scene(root, appHeight, appWidth);
        primaryStage.setScene(mScene);
        primaryStage.show();

        Player fxController = loader.getController();

        initGameController(fxController);

    }



    public static void initGameController(Player fxController) {

        var rController = new RummikubController();
        rController.addPlayer(fxController);

    }


    public static void main(String[] args) {

        launch(args);

    }
}





