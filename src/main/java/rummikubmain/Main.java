package rummikubmain;

import rummikubframework.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rummikub_controller.RummikubController;

import java.net.URL;

/**
 * main class for rummikub
 */
public class Main extends Application {

    Scene mScene;


    ClassLoader mClassLoader = getClass().getClassLoader();
    URL mResource = mClassLoader.getResource("resources/rummikubGUI.fxml");

    static boolean disableRandom = false;

    @Override
    public void start(Stage primaryStage) throws Exception {

        var loader = new FXMLLoader(mResource);

        Parent root = loader.load();

        var applicationName = "Rummikub Final";
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

        var rController = new RummikubController(disableRandom);
        rController.addPlayer(fxController);

    }


    public static void main(String[] args) {

        if(args.length > 0 && args[0].equals("test")){
            disableRandom = true;
        }

        launch(args);

    }
}





