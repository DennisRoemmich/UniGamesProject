package application;




import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;



public class Main extends Application {
	
	private Stage mPrimaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		this.mPrimaryStage = primaryStage;
		mPrimaryStage.setTitle("Tic Tac Toe");
		mainWindow();
	}
	
	public void mainWindow() {

		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("MainWindow.fxml"));
			AnchorPane pane = loader.load();
			
			mPrimaryStage.setMinHeight(300);
			mPrimaryStage.setMinWidth(300);
			
			MainWindowControl mainWindowControl = loader.getController();
			mainWindowControl.setMain(this);
			
			Scene scene = new Scene(pane);
			mPrimaryStage.setScene(scene);
			mPrimaryStage.show();
			
			
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
  public static void main(String[] args) {       
 	 System.out.println("Thank you for playing this version of TicTacToe. Hope you had fun!");
 	 launch(args); 	 
 	 
  } 
  
  
}


