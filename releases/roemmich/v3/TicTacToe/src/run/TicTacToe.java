package run;


import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import simulation.*;

/**
 * Runs the game Tic Tac Toe
 * @author Dennis Roemmich
 */
public class TicTacToe {
	
	
	/**
	 * Main method for testing
	 * @param args Main arguments
	 */
	public static void main(String[] args) {
		
		//Prints info messages to the console
		System.out.println("Welcome to Tic Tac Toe!");

		
		//Initializes game board
		GameBoard game = new GameBoard();
		
		boolean cont = true;
		String input;
		Scanner menuInput = new Scanner(System.in);

		while (cont) {
			
			System.out.println("(Y) Play (N) Exit (R) Replay Game ");  
			
			input  = menuInput.next();
			
			if ("r".equals(input) || "R".equals(input)) {
			game.clearGame();
			System.out.println("Enter the number of turns you want to replay or type 0 for the full game ");

			replayGame(game, readInput(game));
			} 
		
			if ("y".equals(input) || "Y".equals(input)) {
			
			game.clearGame();
			game.clearGameLog();
			
			System.out.println("(P) Player vs Player (C) Player vs Computer ");
			
			gameStart(game, menuInput);
			}

			if ("n".equals(input) || "N".equals(input)) {
			cont = false;
			menuInput.close();
			} 
		}	
	}
	
	/**
	 * A new game is being played and the player decides to play vs AI or another player	
	 * @param game The game board
	 * @param input The Scanner input
	 */
	public static void gameStart(GameBoard game, Scanner input) {
		
		String newInput = input.next();
		
		if ("c".equals(newInput) || "C".equals(newInput)) {
			System.out.println("To play enter a number between 1 and 9. \n");
			printTutorialMap();
			System.out.println("");
			
			System.out.println(game.toString());
			System.out.println("");
			
			runAiGame(game);
		} 
		if ("p".equals(newInput) || "P".equals(newInput)) {
			System.out.println("To play enter a number between 1 and 9. \n");
			printTutorialMap();
			System.out.println("");
			
			System.out.println(game.toString());
			System.out.println("");
			
			runGame(game);	
		}
	}
	
	
	/**
	 * Helper method that returns the next integer of a Scanner input
	 * @return integer value
	 */
	public static int readInput(GameBoard game) {
		
		try {
		@SuppressWarnings("resource")
		Scanner newInput = new Scanner(System.in);
		
		
		return newInput.nextInt();
		
		} catch (Exception e) {
			System.out.println("Invalid input! Please enter a number between 1 and 9.");
			System.out.println(game.toString());
			return readInput(game);
		}
	}
	
	/**
	 * Runs the game Tic Tac Toe
	 * @param game A game board initialization the game is played on
	 */
	public static void runGame(GameBoard game) {
		
		int turn = 0;
		
		//Runs game moves as long as the game is still in progress.
		while (checkWinner(game) == -1) {
			gameMove(game, readInput(game), turn);
			System.out.println(game.toString());
			turn++;
		}

		//Prints the winner and ends the game
		printWinner(game);
		
	}
	
	/**
	 * Runs the game with more parameters. Made for Ai play.
	 * @param game The game board
	 * @param input Integer input
	 * @param turn The turn number
	 */
	public static void runGame(GameBoard game, int input, int turn) {
		
	//Runs game moves as long as the game is still in progress.
	gameMove (game, input, turn);
	System.out.println(game.toString());
			
	//Prints the winner and ends the game
	printWinner(game);	
		
	}
	
	/**
	 * Runs the game with an AI
	 * @param game The game board
	 */
	public static void runAiGame(GameBoard game) {
		
		int turn = 0;
		
		//Runs game moves as long as the game is still in progress.
		while (checkWinner(game) == -1) {
			
			if (turn % 2 == 0) {
			gameMove(game, readInput(game), turn);
			} else {
				int random;
				random = game.getRandom();
				int index = 0;
				while (index < game.getGameLog().size()) {

					if (random == game.getGameLog().get(index)) {
						random = game.getRandom();
						
						//Otherwise it would count to 1 immediately skipping cell 0!!
						index = -1;
					}
					index++;
				}
				try {
					TimeUnit.MILLISECONDS.sleep(300);
				} catch (Exception e) {
					e.printStackTrace();
				}
			gameMove(game, random, turn);
			}
			System.out.println(game.toString());
			turn++;	
		}
		//Prints the winner and ends the game
		printAiWinner(game);
	}

	/**
	 * Makes a game move in TicTacToe
	 * @param game A game board initialization the game is played on
	 * @param turn The current turn
	 */
	public static void gameMove(GameBoard game, int input, int turn)  {
		try {
			
			//Error message String
			String numberError = "Error! Please choose a cell between 1 and 9.";
			

			if (input < 1 || input > 9) {
				System.out.println(numberError);
				gameMove(game, readInput(game), turn);
				} 
			validMove(game, input, turn);
				
			} catch (Exception e) {
			//Maybe there will be something to catch in the future..
		}
	}
	
	/**
	 * Checks if the valid input input also is a valid move.
	 * @param game The GameBoard initialization
	 * @param input The console input
	 * @param turn The turn number
	 */
	public static void validMove(GameBoard game, int input, int turn) {
		
		//Error message for when the cell is already filled
		String cellError = "Please choose an empty cell.";
		
		//Depending on if the cell is empty and whose turn it is, the input is being placed and logged
		if (game.getCell(game.toRow(input), game.toColumn(input)).isEmpty()) {
			if (turn % 2 == 0) {
				game.setX(game.toRow(input), game.toColumn(input));
			} else {	
				game.setO(game.toRow(input), game.toColumn(input));
			}
			game.setGameLog(input);
		} else {
			System.out.println(cellError);	
			gameMove(game, readInput(game), turn);
		}
	}
	
	/**
	 * Returns true if the player with X wins
	 * @param game The game board
	 * @return boolean value
	 */
	public static boolean playerXWins(GameBoard game) {
		
		return	game.getCell(0, 0).isX() && game.getCell(0, 1).isX() && game.getCell(0, 2).isX() ||
			game.getCell(1, 0).isX() && game.getCell(1, 1).isX() && game.getCell(1, 2).isX() ||
			game.getCell(2, 0).isX() && game.getCell(2, 1).isX() && game.getCell(2, 2).isX() || 
			game.getCell(0, 0).isX() && game.getCell(1, 0).isX() && game.getCell(2, 0).isX() ||
			game.getCell(0, 1).isX() && game.getCell(1, 1).isX() && game.getCell(2, 1).isX() ||
			game.getCell(0, 2).isX() && game.getCell(1, 2).isX() && game.getCell(2, 2).isX() ||
			game.getCell(0, 0).isX() && game.getCell(1, 1).isX() && game.getCell(2, 2).isX() || 
			game.getCell(0, 2).isX() && game.getCell(1, 1).isX() && game.getCell(2, 0).isX(); 
				
			
	}
	
	/**
	 * Returns true if the player with O wins
	 * @param game The game board
	 * @return boolean value
	 */
	public static boolean playerOWins(GameBoard game) {
		
		//Here again...
		return	game.getCell(0, 0).isO() && game.getCell(0, 1).isO() && game.getCell(0, 2).isO() ||
			game.getCell(1, 0).isO() && game.getCell(1, 1).isO() && game.getCell(1, 2).isO() ||
			game.getCell(2, 0).isO() && game.getCell(2, 1).isO() && game.getCell(2, 2).isO() || 
			game.getCell(0, 0).isO() && game.getCell(1, 0).isO() && game.getCell(2, 0).isO() ||
			game.getCell(0, 1).isO() && game.getCell(1, 1).isO() && game.getCell(2, 1).isO() ||
			game.getCell(0, 2).isO() && game.getCell(1, 2).isO() && game.getCell(2, 2).isO() ||
			game.getCell(0, 0).isO() && game.getCell(1, 1).isO() && game.getCell(2, 2).isO() || 
			game.getCell(0, 2).isO() && game.getCell(1, 1).isO() && game.getCell(2, 0).isO(); 		
	}
	
	/**
	 * Returns true if the game ends in a draw
	 * @param game The game board
	 * @return boolean value
	 */
	public static boolean isDraw(GameBoard game) {
		
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				if (game.getCell(row, column).isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Checks the winner of the game
	 * @param game The game board
	 * @return integer // -1 Game in progress // 0 Draw // 1 X wins // 2 O wins
	 */
	public static int checkWinner(GameBoard game) {
		// 1 if X wins, 2 if O wins, 0 if draw and -1 if game is still in progress
		if (playerXWins(game)) {
			return 1;
		}
		if (playerOWins(game)) {
			return 2;
		}
		if (isDraw(game)) {
			return 0;
		}
		return -1;
	}
	
	/**
	 * Prints the result on the console
	 * @param game Current game
	 */
	public static void printWinner(GameBoard game) {
		if (checkWinner(game) == 0) {
			System.out.println("Draw!");
		}
		if (checkWinner(game) == 1) {
			System.out.println("X wins!");
		}
		if (checkWinner(game) == 2) {
			System.out.println("O wins!");
		}
	}
	
	/**
	 * Prints the result on the console
	 * @param game Current game
	 */
	public static void printAiWinner(GameBoard game) {
		if (checkWinner(game) == 0) {
			System.out.println("Draw!");
		}
		if (checkWinner(game) == 1) {
			System.out.println("You win!");
		}
		if (checkWinner(game) == 2) {
			System.out.println("You lose!");
		}
	}

	/**
	 * Prints a map with numbers instead of empty dots to show which input correlate with which cells
	 */
	public static void printTutorialMap()  {

		int rowIndex;
		int columnIndex;
		int number = 1;
		
		for (rowIndex = 0; rowIndex < 3; rowIndex++) {
			for (columnIndex = 0; columnIndex < 3; columnIndex++) {
				System.out.print(number + " ");
				number++;
				
			}
			System.out.println("");
		}	
	}
	
	private static void replayGame(GameBoard game, int input) {
		

		try {
			if (game.getGameLog().isEmpty()) {
				System.out.println("No replay data found!");
			}
			int turn = 0;
			int max;
			
			if (input <= game.getGameLog().size() && input > 0) {
				max = input - 1; 
			} else {
				max = game.getGameLog().size() - 1;
			}
			while (turn <= max) {
				
				TimeUnit.MILLISECONDS.sleep(900);
				runGame(game, game.getGameLog().get(turn), turn);
				
			turn++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
