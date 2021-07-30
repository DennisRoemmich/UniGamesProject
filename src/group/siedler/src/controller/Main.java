package controller;

import framework.PrintToConsole;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Launches the application and its resources.
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */


public class Main extends Application {
	

	public static int getHumanPlayers() {
		return humanPlayers;
	}


	public static void setHumanPlayers(int humanPlayers) {
		Main.humanPlayers = humanPlayers;
	}


	public static int getAiPlayers() {
		return aiPlayers;
	}


	public static void setAiPlayers(int aiPlayers) {
		Main.aiPlayers = aiPlayers;
	}


	public static int getExtraMaterials() {
		return extraMaterials;
	}


	public static void setExtraMaterials(int extraMaterials) {
		Main.extraMaterials = extraMaterials;
	}
	
    public static boolean isStandardGame() {
		return isStandardGame;
	}


	public static void setStandardGame(boolean isStandardGame) {
		Main.isStandardGame = isStandardGame;
	}

	static int humanPlayers;
	static int aiPlayers;
	static int extraMaterials;
	static boolean isStandardGame;
	



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
        
        
        Button button = new Button("Play Standard Map");
        Button button2 = new Button("Play Konstanz Map");
        TextField input1 = new TextField();
        input1.setText("0");
        
        TextField input2 = new TextField();
        input2.setText("0");
        TextField input3 = new TextField();
        input3.setText("3");
        
        button2.setOnAction(e -> { 
        try {
        		setHumanPlayers(Integer.parseInt(input3.getText()));
        		setAiPlayers(Integer.parseInt(input2.getText()));
        		setExtraMaterials(Integer.parseInt(input1.getText()));
        } catch (Exception ex)
        	{
        		PrintToConsole.println("No input in some fields!");
        	}
			setStandardGame(true);
			primaryStage.setScene(scene);
        });
        
        button.setOnAction(e -> { 
		
		try {
				setHumanPlayers(Integer.parseInt(input3.getText()));
				setAiPlayers(Integer.parseInt(input2.getText()));
				setExtraMaterials(Integer.parseInt(input1.getText()));
		} catch (Exception ex)
			{
				PrintToConsole.println("No input in some fields!");
			}
			setStandardGame(true);
			primaryStage.setScene(scene);
		});
        
        VBox vbox = new VBox(20);
        
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.setAlignment(Pos.CENTER);
        Scene scene2 = new Scene(vbox, 1200, 900);
        
        
        Label label0 = new Label("Welcome to Siedler von Konstanz!");
        label0.setMinWidth(160.0);
        label0.setTextFill(Color.ALICEBLUE);
		label0.setAlignment(Pos.CENTER);
		label0.setFont(new Font(28.0));
        
        Label label1 = new Label("Extra Starting Ressources:");
        label1.setMinWidth(160.0);
        label1.setTextFill(Color.ALICEBLUE);
        
        Label label2 = new Label("Number of AI players:");
        label2.setMinWidth(160.0);
        label2.setTextFill(Color.ALICEBLUE);
        
        Label label3 = new Label("Number of human players:");
        label3.setMinWidth(160.0);
        label3.setTextFill(Color.ALICEBLUE);
        
        HBox hbox0 = new HBox(60);
        hbox0.setAlignment(Pos.CENTER);
        HBox hbox1 = new HBox(20);
        hbox1.setAlignment(Pos.CENTER);
        HBox hbox2 = new HBox(60);
        hbox2.setAlignment(Pos.CENTER);
        
        HBox hbox3 = new HBox(60);
        hbox3.setAlignment(Pos.CENTER);
        HBox hbox4 = new HBox(60);
        hbox4.setAlignment(Pos.CENTER);
        
        
        
        hbox1.getChildren().addAll(button, button2);
        hbox2.getChildren().addAll(label1, input1);
        hbox3.getChildren().addAll(label2, input2);
        hbox4.getChildren().addAll(label3, input3);
        hbox0.getChildren().addAll(label0);
        
        BackgroundImage myBI= new BackgroundImage(new Image("resources/Background.png",1200,900,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                  BackgroundSize.DEFAULT);
        //then you set to your node
        vbox.setBackground(new Background(myBI));
        vbox.getChildren().addAll(hbox0, hbox4, hbox3, hbox2, hbox1);

        scene.getRoot().requestFocus();
        
        primaryStage.setTitle("Die Siedler von Konstanz");
        primaryStage.setScene(scene2);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
