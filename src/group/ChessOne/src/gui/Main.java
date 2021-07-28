package gui;

import gui.ChessBoardNode;
import gui.GuiController;
import javafx.application.Application;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        var resource = classLoader.getResource("resources/Chess.fxml");
        FXMLLoader loader = new FXMLLoader(resource);
        primaryStage.setTitle("Chess One");
        primaryStage.setScene(new Scene(loader.load(), 1400, 1000));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
