package javaFX;

import console.Print;
import controller.SkatController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class FXLauncher extends Application {

    static final int MINWIDTH = 1365;
    static final int MINHEIGHT = 1024;


    static final int MAXWIDTH = 1365;
    static final int MAXHEIGHT = 1024 + 28;

    static SkatController skatController;

    @Override
    public void start(Stage primaryStage) throws Exception{

        var classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("SkatGUI.fxml");

        var loader = new FXMLLoader(resource);

        Parent root = loader.load();
        primaryStage.setTitle("Skat V0");
        primaryStage.setMinHeight(MINHEIGHT);
        primaryStage.setMinWidth(MINWIDTH);

        primaryStage.setMaxHeight(MAXHEIGHT);
        primaryStage.setMaxWidth(MAXWIDTH);

        var scene = new Scene(root, MINHEIGHT, MINWIDTH);
        primaryStage.setScene(scene);
        primaryStage.show();

        var FXController = (FXController) loader.getController();

        Print.debug("WARNING", "Whuup, Controller : " + FXLauncher.skatController.toString());
        FXController.setController(FXLauncher.skatController);
        FXController.setScene(scene);

    }


    public void launchFX(SkatController controller){


        FXLauncher.skatController = controller;
        Print.debug("WARNING", "Controller : " + FXLauncher.skatController.toString());
        launch();

    }

}