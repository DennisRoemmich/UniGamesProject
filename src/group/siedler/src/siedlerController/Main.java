package siedlerController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import siedlerFramework.PrintToConsole;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        ClassLoader classLoader = getClass().getClassLoader();
        var resource = classLoader.getResource("resources/SiedlerGUI.fxml");
        if (resource == null) {
            PrintToConsole.println("\n=== URL is null ===\n");
        }
        FXMLLoader loader = new FXMLLoader(resource);
        Parent root = loader.load();
        Scene scene = new Scene(root, 1200, 900);

        scene.getRoot().requestFocus();
        
        primaryStage.setTitle("Die Siedler von Konstanz");
        primaryStage.setScene(scene);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
