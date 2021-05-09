package application;

import java.awt.Label;
import java.util.ArrayList;

import java.util.List;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import run.TicTacToe;
import simulation.GameBoard;
import simulation.Cell;


/**
 * Dies ist das erste mal dass ich mit JavaFX arbeite... ich bitte um Nachsicht :(
 * @author DR
 *
 */









public class Main extends Application
{
	Button button;
	Button button2;
	Button buttons[];
	GameBoard game = new GameBoard();
	List<Three> threes = new ArrayList<>();
	
	int turn = 0;
	boolean gameEnded = false;
	boolean XTurn = true;
	
	
    public static void main(String[] args) 
    {       
   	 System.out.println("Thank you for playing this version of TicTacToe. Hope you had fun!");
   	 launch(args); 	 
   	 
    } 
  //public void start(Stage primaryStage) throws Exception{
	  
	  
 // }
//}
//    private Label statusMsg = new Label("X's turn.");
//    
//    
//      public void start(Stage primaryStage) throws Exception{
//    	  
//    	  GridPane pane = new GridPane();
//    	  GameBoard game = new GameBoard();
//    	  
//    	  BorderPane borderPane = new BorderPane();
//    	  borderPane.setCenter(pane)
//    	  borderPane.setBottom(null);
//    	  
//    	  Scene scene = new Scene(borderPane, 450, 300);
//    	  primaryStage.setTitle("Tic Tac Toe");
//    	  primaryStage.setScene(scene);
//    	  primaryStage.show();
//    	  
//
//      }
//      
//	  public class GUIHelper {
//		
//		  this.setOnMouseClicked (e -> handleClick());
//	  }
//      
//	  public void handleClick() {
//		  
//	  }
//      
//      }
    
    GameCell[][] board = new GameCell[3][3];
    
    
    public void start(Stage primaryStage) throws Exception{
    	primaryStage.setScene(new Scene(gameScene()));
    	primaryStage.show();  	
    
    }
    
    public Parent gameScene() {
    	
    	
    	GameBoard game = new GameBoard();
    	Pane root = new Pane();
    	root.setPrefSize(150, 150);
    	
    	for (int i = 0; i<3; i++) {
    		for (int j = 0; j <3; j++) {
    			GameCell cell = new GameCell(game);
    			cell.setTranslateX(j * 50);
    			cell.setTranslateY(i * 50);
    			
    			root.getChildren().add(cell);
    			
    			board[j][i] = cell;
    			
    		}
    	}
    	
    	//if (TicTacToe.checkWinner(game) != -1)

    	//}
    	
    	for (int x = 0; x < 3; x++) {
    		threes.add(new Three(board[0][x], board[1][x], board[2][x]));
    	}
    	
    	for (int y = 0; y <3; y++ ) {
    		threes.add(new Three(board[y][0], board[y][1], board[y][2]));
    	}
    	threes.add(new Three(board[0][0], board[1][1], board[2][2]));
    	threes.add(new Three(board[2][0], board[1][1], board[0][2]));
    	
    	return root;
    }
    
    //boolean ja = true;
    
    public class GameCell extends StackPane {
    	
    	Text text = new Text();
    	
    	
    	public GameCell(GameBoard game) {
    		Rectangle border = new Rectangle(50, 50);
    		border.setFill(null);
    		border.setStroke(Color.BLACK);
    		
    		setAlignment(Pos.CENTER);
    		getChildren().addAll(border, text);
    		
    		//if (ja) {
    		setOnMouseClicked(e -> 	{
    			if(gameEnded) {
				return;
			}
			
		if(TicTacToe.checkWinner(game)!= -1)	
			return;
			if(XTurn) {
				drawX();
				
				XTurn = false;
				checkWinner();
				
			}else {
				drawO();
				XTurn = true;
				checkWinner();
				
			}}); 
    			
    		
    		}
    		

		
    	public void checkWinner() {
    		for (Three three : threes ) {
    			if (three.hasEnded()) {
    				gameEnded = true;
    				
    				display("Game Ended");
    				break;
    			}
    		}
    			
    	}
    	
    	public void display(String title) {
    		Stage window = new Stage();
    		
    		window.setTitle(title);
    		
    		
    		Label label = new Label();
    		label.setText("Game Ended!");
    		
    		Text text2 = new Text();
    		text2.setText("Game Ended!");
    		
    		Button goBack = new Button("Go Back");
    		goBack.setOnAction(e -> window.close());
    		
    		BorderPane layout = new BorderPane();
    		layout.setPrefSize(50, 50);
    		
    		layout.setCenter(text2);
    		//layout.getChildren().add(layout);
    		//layout.getChildren().add(label);
    		
    		//layout.setAlignment(Pos.CENTER);
    		
    		Scene scene = new Scene(layout);
    		window.setScene(scene);
    		window.show();
    	}	

    	
    	public void drawX() {
    		text.setText("X");
    	}
    	
    	public void drawO() {
    		text.setText("O");
    	}
    	public String getChar() {
    		return text.getText();
    	}

    }

	public class Three {
		GameCell[] cells;
		
		public Three(GameCell... cells ) {
			this.cells = cells;
		}
		
		public boolean hasEnded() {
			if (cells[0].getChar().isEmpty())
				return false;
			
			return cells[0].getChar().equals(cells[1].getChar()) 
					&& cells[0].getChar().equals(cells[2].getChar());		
		}

	}
	

    
//    public void start(Stage primaryStage) throws Exception {	
//    	
//    	primaryStage.setTitle("Tic Tac Toe");
//    	
//    	
//    	
//    	button = new Button();
//    	button.setText("Play vs Computer");
//    	
//    	button2 = new Button();
//    	button2.setText("Player vs Player");
//    	
//    	HBox layout = new HBox(10);
//    	layout.getChildren().add(button);
//    	layout.getChildren().add(button2);
//    	
//    	
//    	//layout.al
//    	Label O = new Label("O");
//    	Label X = new Label("X");
//    	
//    	String text;
//    	
//    	 int turn = 0;
//    	
//    	TilePane tilePane = new TilePane();
//    	
//    	buttons = new Button[10];
//    	
//    	for (int i=1; i<10; i++) {
//    		Button button = new Button(Integer.toString(i));
//    		button.setPrefWidth(65);
//    		button.setPrefHeight(65);
//    		tilePane.getChildren().add(button);
//    		buttons[i]= button;
//    		
//    		final int iCopy = i;
//    		
//    		
//    			buttons[i].setOnAction(e -> {
//    				if (iCopy % 2 == 0){
//    				buttons[iCopy].setDisable(true); buttons[iCopy].setText("X");
//    				}
//    			
//    			else {
//    				buttons[iCopy].setDisable(true); buttons[iCopy].setText("O");}
//    				
//    			});
//    			//turn++;
//    		
//    			
//    			//turn++;
//    		
//    		
//    		//buttons[iCopy].setText("O");
//    		
//        	Label label = new Label("Welcome to Tic Tac Toe!");
//        	//TilePane.setAlignment(label, Pos.BOTTOM_LEFT);
//        	//tilePane.getChildren().addAll(label);
//    		}
//    	
//    	
//    	tilePane.setOrientation(Orientation.HORIZONTAL);
//    	tilePane.setHgap(15);
//    	tilePane.setVgap(15);
//    	
//    	tilePane.setPrefColumns(3);
//    	tilePane.setPrefRows(3);
//    	
//    	primaryStage.setResizable(false);
//    	
//    	Label label = new Label("Hello");
//    	//TilePane.setAlignment(label, Pos.BOTTOM_LEFT);
//    	//tilePane.getChildren().addAll(label);
//    	
//    	//tilePane.setTileAlignment(Pos.CENTER);
//    	
//    	//tilePane.setAlignment(button, Pos.TOP_LEFT);
//
//    	
//        Scene scene = new Scene(tilePane);
//        Scene sceneTwo = new Scene(layout);
//        primaryStage.setScene(sceneTwo);
//        
//        button.setOnAction(e -> primaryStage.setScene(scene) );
//        button2.setOnAction(e -> primaryStage.setScene(scene));
//        primaryStage.show();
//        }
//              
   
    
//    public void method() {
//    	
//    	//int hi = getStatus()
//    }
    
    	
 	
    	
    	
    	
	public void display(String title, String message) 
	{
		Stage window = new Stage();
		
		window.setTitle(title);
		window.setMinWidth(100);
		
		Label label = new Label();
		label.setText(message);
		
		Button goBack = new Button("Go Back");
		goBack.setOnAction(e -> window.close());
		
		VBox layout = new VBox(10);
		//layout.getChildren().add(layout);
		//layout.getChildren().add(label);
		
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.show();
		
		

    class NewBox {
    	
    	public void display(String title, String message) {
    		Stage window = new Stage();
    		
    		window.setTitle(title);
    		
    		
    		Label label = new Label();
    		label.setText(message);
    		
    		Button goBack = new Button("Go Back");
    		goBack.setOnAction(e -> window.close());
    		
    		
    		layout.getChildren().add(layout);
    		
    		
    		layout.setAlignment(Pos.CENTER);
    		
    		Scene scene = new Scene(layout);
    		window.setScene(scene);
    		window.show();
    	}	
    		
    }  		
    	
	}


}
	


