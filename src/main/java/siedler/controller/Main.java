package siedler.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import siedler.framework.PrintToConsole;

/**
 * Launches the application and its resources.
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public class Main extends Application {

	private static Stage pStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
    	
    	setPrimaryStage(primaryStage);
    	primaryStage.setResizable(false);
    	
    	ClassLoader classLoader = getClass().getClassLoader();

        
        var resourceMenu = classLoader.getResource("resources/SiedlerMenu.fxml");
        if (resourceMenu == null) {
            PrintToConsole.println("\n=== URL is null ===\n");
        }
        FXMLLoader loaderMenu = new FXMLLoader(resourceMenu);
        Parent rootMenu = loaderMenu.load();
        
        Scene scene = new Scene(rootMenu, 1200, 900);
        


        
        scene.getRoot().requestFocus();
        
        primaryStage.setTitle("Die Siedler von Konstanz - Menü");
        primaryStage.setScene(scene);

        primaryStage.show();
    
	}
    
    public static Stage getPrimaryStage() {
        return pStage;
    }

    private static void setPrimaryStage(Stage pStage) {
        Main.pStage = pStage;
    }
    


    public static void main(String[] args) {
        launch(args);
    }
}
