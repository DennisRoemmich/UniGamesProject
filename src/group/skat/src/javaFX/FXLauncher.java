package javaFX;

import controller.SkatController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class FXLauncher extends Application {

    static final int MINWIDTH = 1365;
    static final int MINHEIGHT = 1024 - 200;


    static final int MAXWIDTH = 1365;
    static final int MAXHEIGHT = 1024 + 28;

    static SkatController skatController;

    FXController fxController;
    int playerAmount;

    @Override
    public void start(Stage primaryStage) throws Exception{

        var classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("resources/SkatGUI.fxml");

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

        fxController = (FXController) loader.getController();

        fxController.setController(FXLauncher.skatController);
        fxController.setScene(scene);
        fxController.addAsPlayer(playerAmount);

    }

    public void launchFX(SkatController controller){

        FXLauncher.skatController = controller;
        launch();

    }

    public void launchFX(SkatController controller, int playerAmount){

        FXLauncher.skatController = controller;
        launch();

    }

    public FXController getFxController(){

        return fxController;

    }

}