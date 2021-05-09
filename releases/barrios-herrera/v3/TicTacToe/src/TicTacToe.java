import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TicTacToe extends Application{
	private static Random rn = new Random();
	private char currentPlayer = 'X';
	private static Cell[][] gameBoard = new Cell[3][3];
	private static char empty = ' ';
	private char player = ' ';
	private static TwoStacksTTT stackP1 = new TwoStacksTTT(30);
	private static TwoStacksTTT stackP2 = new TwoStacksTTT(30);
	private static int counterP1 = 0;
	private static int counterP2 = 0;

	
	//manages the Interfaces
	@Override
	public void start(Stage primaryStage) throws Exception {
		Button undo = new Button();
		undo.setText("Undo last move");
		undo.setTranslateY(0);
		undo.setTranslateX(200);
		undo.setOnAction((ActionEvent event) -> {
			undoMoves(1);
		});
		Button twoPlayer = new Button();
		twoPlayer.setText("Player vs Player");
		twoPlayer.setPrefSize(200,100);
		twoPlayer.setOnAction((ActionEvent event) -> {
			GridPane pane = new GridPane();
			
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					gameBoard[i][j] = new Cell();
					pane.add(gameBoard[i][j], j, i);
				}
			}
			
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					int finalI = i;
					int finalJ = j;
					gameBoard[i][j].setOnMouseClicked(e -> move(finalI,finalJ));
				}
			}

			StackPane rootP = new StackPane();
			rootP.getChildren().addAll(pane, undo);
			undo.setTranslateX(500);
			
			Scene secondScene = new Scene(rootP, 1250, 900);
			
			Stage secondStage = new Stage();
			secondStage.setTitle("TTT");
			secondStage.setScene(secondScene);
			secondStage.show();
		});
		
		Button computer = new Button();
		computer.setText("Play against Computer");
		computer.setPrefSize(200,100);
		
		computer.setOnAction((ActionEvent event) -> {
			GridPane pane = new GridPane();
			
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					gameBoard[i][j] = new Cell();
					pane.add(gameBoard[i][j], j, i);
				}
			}
			
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					int finalI = i;
					int finalJ = j;
					gameBoard[i][j].setOnMouseClicked(e -> moveComputer(finalI,finalJ));
				}
			}
			
			
			
			StackPane rootC = new StackPane();
			rootC.getChildren().addAll(pane, undo);
			undo.setTranslateX(500);
			
			Scene thirdScene = new Scene(rootC, 1250, 900);
			
			Stage thirdStage = new Stage();
			thirdStage.setTitle("TTT");
			thirdStage.setScene(thirdScene);
			thirdStage.show();
		});
		
		StackPane root = new StackPane();
		root.getChildren().addAll(twoPlayer, computer);
		twoPlayer.setTranslateY(-200);
		computer.setTranslateY(0);
		
		Scene scene = new Scene (root, 900, 900);
		primaryStage.setTitle("Menu");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		primaryStage.setOnCloseRequest(e -> Platform.exit());
		
	}
	
	// checks if the game board does not have more empty spaces
		public static boolean filledBoard() {

			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					if (gameBoard[x][y].getPlayer() == ' ') {
						return false;
					}
				}
			}

			return true;
		}

		// shows who is the winner and why was the game won
		private static void winner() {
			
			Alert al = new Alert(Alert.AlertType.INFORMATION);
			al.setTitle("And the winner is...");
			char symbol = checkHorizontal();
			
			if (symbol != empty) {
				
				
				if (symbol == 'X') {
					
					al.setHeaderText("P1 you are the winner by a horizontal line!");
					al.showAndWait();
				} else {
					al.setHeaderText("P2 you are the winner by a horizontal line!");
					al.showAndWait();
				}

				return;

			}

			symbol = checkVertical();

			if (symbol != empty) {

				if (symbol == 'X') {
					al.setHeaderText("P1 you are the winner by a vertical line!");
					al.showAndWait();
				} else {
					al.setHeaderText("P2 you are the winner by a vertical line!");
					al.showAndWait();
				}

				return;

			}

			symbol = checkDiagonal();

			if (symbol != empty) {

				if (symbol == 'X') {
					al.setHeaderText("P1 you are the winner by a diagonal line!");
					al.showAndWait();
				} else {
					al.setHeaderText("P2 you are the winner by a diagonal line!");
					al.showAndWait();
				}

				return;

			}

			symbol = checkDiagonalOpp();

			if (symbol != empty) {

				if (symbol == 'X') {
					al.setHeaderText("P1 you are the winner by a diagonal line!");
					al.showAndWait();
				} else {
					al.setHeaderText("P2 you are the winner by a diagonal line!");
					al.showAndWait();
				}

				return;

			}

			al.setHeaderText("Is a tie!");
			al.showAndWait();

		}
		// Controls when the game is over
		public static boolean gameOver() {

			if (checkHorizontal() != empty || checkVertical() != empty
					|| checkDiagonal() != empty || checkDiagonalOpp() != empty ||filledBoard()) {
				return true;
			}

			return false;
		}

		// checks if there is a horizontal line with the same symbol
		private static char checkHorizontal() {
			char symbol = empty;
			boolean equal;

			for (int x = 0; x < 3; x++) {

				equal = true;
				symbol = gameBoard[x][0].getPlayer();

				if (symbol != empty) {
					for (int y = 1; y < 3; y++) {
						if (symbol != gameBoard[x][y].getPlayer()) {
							equal = false;
						}
					}

					if (equal) {
						return symbol;
					}
				}
			}

			return empty;
		}

		// checks if there is a vertical line with the same symbol
		private static char checkVertical() {

			char symbol = empty;
			boolean equal;

			for (int y = 0; y < 3; y++) {
				equal = true;
				symbol = gameBoard[0][y].getPlayer();

				if (symbol != empty) {
					for (int x = 1; x < 3; x++) {
						if (symbol != gameBoard[x][y].getPlayer()) {
							equal = false;
						}
					}
					if (equal) {
						return symbol;
				}
				
				}
			}

			return empty;
		}

		// checks if there is a diagonal line with the same symbol
		private static char checkDiagonal() {

			char symbol = gameBoard[0][0].getPlayer();
			boolean equal = true;

			// Check diagonal from left to right
			if (symbol != empty) {
				for (int x = 0; x < 3; x++) {
					if (symbol != gameBoard[x][x].getPlayer()) {
						equal = false;
					}
				}

				if (equal) {
					return symbol;
				}
			}

			return empty;
		}

		// Checks diagonal from right to left
		private static char checkDiagonalOpp() {
			boolean equal = true;
			// Check diagonal from right to left
			
			char symbol = gameBoard[0][2].getPlayer();
			if (symbol != empty) {
				for (int x = 1, y = 1; x < 3; x++, y--) {
					if (symbol != gameBoard[x][y].getPlayer()) {
						equal = false;
					}
				}

				if (equal) {
					return symbol;
				}
			}

			return empty;
		}
		
		// checks if the position is occupied
		private static boolean checkEmpty(int x, int y) {

			if (gameBoard[x][y].getPlayer() != empty) {
				return false;
			}

			return true;
		}
		
		//makes move by player.
		private void move(int x, int y) {
			if(player == empty && currentPlayer != empty && checkEmpty(x,y)) {
				gameBoard[x][y].setPlayer(currentPlayer,false);
				if(currentPlayer == 'X') {
				stackP1.push1(x);
				stackP1.push2(y);
				counterP1++;
				} else if (currentPlayer == 'O') {
					stackP2.push1(x);
					stackP2.push2(y);
					counterP2++;
				}
				currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
			} else if (!checkEmpty(x,y)) {
				Alert al = new Alert(Alert.AlertType.INFORMATION);
				al.setTitle("Uh oh!");
				al.setHeaderText("That square is already filled, try again!");
				al.showAndWait();
			}
			
			if(gameOver()) {
				emptyStacks();

				winner();
			}
		}
		
		//gets a random number for the computer move on the x position
		private static int computerMoveX() {
			return rn.nextInt(3);

		}

		// gets computer coordinate for y
		private static int computerMoveY() {
			return rn.nextInt(3);

		}
		
		//makes move by computer
		public void moveComputer(int x, int y) {
			int xc = 0;
			int yc = 0;
			boolean valid = false;
			if(player == empty && currentPlayer == 'X' && checkEmpty(x,y)) {
				gameBoard[x][y].setPlayer(currentPlayer,false);  
				stackP1.push1(x);
				stackP1.push2(y);
				counterP1++;
				currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
			} else if (!checkEmpty(x,y)) {
				Alert al = new Alert(Alert.AlertType.INFORMATION);
				al.setTitle("Uh oh!");
				al.setHeaderText("That square is already filled, try again!");
				al.showAndWait();
			}
			
			if(gameOver()) {
				winner();
				emptyStacks();

				currentPlayer = 'X';
			}
			
			if(!gameOver()) {
			if(currentPlayer == 'O') {
				do {
					valid = false;
					xc = computerMoveX();
					yc = computerMoveY();
					if(checkEmpty(xc,yc)) {
						valid = true;
					} else {
						valid = false;
					}
				} while (!valid);
				
				gameBoard[xc][yc].setPlayer(currentPlayer,false);
				stackP2.push1(xc);
				stackP2.push2(yc);
				counterP2++;
				currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
				if(gameOver()) {
					winner();
					emptyStacks();

				}
			}
			currentPlayer = 'X';

			}
			
		}
		
		//undo of 1 move by using 2 stacks 1 array
		private static void undoMoves(int moves) {
			for (int i = 0; i < moves; i++) {
				int x = stackP1.peek1();
				int y = stackP1.peek2();
				gameBoard[x][y].setPlayer('X',true);
				stackP1.pop1();
				stackP1.pop2();
				counterP1--;

				int z = stackP2.peek1();
				int n = stackP2.peek2();
				gameBoard[z][n].setPlayer('O',true);
				stackP2.pop1();
				stackP2.pop2();
				counterP2--;
			}

		}
		
		//empties stacks
		private static void emptyStacks() {
			for(int i = 0; i < counterP1; i++) {
				stackP1.pop1();
				stackP1.pop2();
			}
			
			for(int j = 0; j < counterP2; j++) {
				stackP2.pop1();
				stackP2.pop2();
			}
			
			counterP1 = 0;
			counterP2 = 0;
		}
	
	public static void main(String[] args) {
		
		launch (args);
		
	}
}


