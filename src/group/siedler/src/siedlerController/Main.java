package siedlerController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        ClassLoader classLoader = getClass().getClassLoader();
        var resource = classLoader.getResource("resources/SiedlerGUI.fxml");
        if(resource == null) {
            System.out.println("\n=== URL is null ===\n");
        }
        FXMLLoader loader = new FXMLLoader(resource);
        Parent root = loader.load();
        Scene scene = new Scene(root, 1200,900);
        
        //This line of code just fixed everything.. *ALL HAIL THIS LINE OF CODE*
        scene.getRoot().requestFocus();
        
        primaryStage.setTitle("Die Siedler von Konstanz");
        primaryStage.setScene(scene);
       // primaryStage.minWidthProperty().set(700);

        /*
        primaryStage.minHeightProperty().bind(primaryStage.widthProperty().multiply(0.713));
        primaryStage.maxHeightProperty().bind(primaryStage.widthProperty().multiply(0.713));
        primaryStage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                primaryStage.setMaximized(false);
            }
        });
*/
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
