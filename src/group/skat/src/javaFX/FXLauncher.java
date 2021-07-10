package javaFX;

import controller.SkatController;
import framework.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class FXLauncher extends Application {

    static final int MINWIDTH = 1280;
    static final int MINHEIGHT = 720;


    static final int MAXWIDTH = 1280;
    static final int MAXHEIGHT = 720 + 28;

    SkatController controller;

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
        FXController.setController(controller);
        FXController.setScene(scene);

    }


    public void launchFX(SkatController controller){

        this.controller = controller;
        launch();

    }

}