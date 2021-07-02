package javaFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class FXLauncher extends Application {

    static final int HEIGHT = 500;
    static final int WIDTH = 400;

    @Override
    public void start(Stage primaryStage) throws Exception{

        var classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("SkatGUI.fxml");

        var loader = new FXMLLoader(resource);

        Parent root = loader.load();
        primaryStage.setTitle("Skat V0");
        primaryStage.setScene(new Scene(root, HEIGHT, WIDTH));
        primaryStage.show();
    }


    public void launchFX(){

        launch();

    }

}