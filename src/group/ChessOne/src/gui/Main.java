package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main for the GUI initialization
 * @author Jan de Boer, Dennis Roemmich
 *
 */
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
