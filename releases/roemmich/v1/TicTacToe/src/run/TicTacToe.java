package run;

import java.util.Scanner;
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
		System.out.println("To play enter a number between 1 and 9.");
		System.out.println("If you want to replay a move press 0 \n");
		printTutorialMap();
		System.out.println("");

		//Initializes game board
		GameBoard game = new GameBoard();
			
		//Prints the board to the console 
		System.out.println(game.toString());
		
		//Runs the game
		runGame(game);	
	}
	/**
	 * Helper method that returns the next int of a Scanner input
	 * @param newInput Scanner input value
	 * @return integer value
	 */
	
	public static int readInput(Scanner newInput) {
		
		return newInput.nextInt();
	}
	
	/**
	 * Runs the game Tic Tac Toe
	 * @param game A game board initialization the game is played on
	 * @param turn The turn number
	 */
	public static void runGame(GameBoard game) {
		
		//Input stream
		Scanner readInput = new Scanner(System.in);
		
		int turn = 0;
		
		//Runs game moves as long as the game is still in progress.
		while (checkWinner(game) == -1) {
			gameMove(game, readInput, turn);
			System.out.println(game.toString());
			turn++;
		}
		//Close input stream
		readInput.close();
		//Prints the winner and ends the game
		printWinner(game);
	}

	/**
	 * Makes a game move in TicTacToe
	 * @param game A game board initialization the game is played on
	 * @param newInput Scanner console input
	 * @param turn The current turn
	 */
	public static void gameMove(GameBoard game, Scanner newInput, int turn)  {
		try {
			int input  = newInput.nextInt();
			
			//Error message String
			String numberError = "Error! Please choose a cell between 1 and 9.";
			
			//An input of 0 is not a valid move but will trigger a replay of the last move
			if (input == 0) {
				resetGameMove(game, turn);
				return;
			}
			if (input < 0 || input > 9) {
				System.out.println(numberError);
				gameMove(game, newInput, turn);
				}
			validMove(game, input, turn);
			} catch (RuntimeException e) {
			
			//An invalid input may not be an Integer and has to be caught by this block.
			System.out.println("Invalid input! Please enter a number between 1 and 9.");
			System.out.println(game.toString());
			newInput.next();
			gameMove(game, newInput, turn);
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
				game.xCell(game.toRow(input), game.toColumn(input));
			} else {	
				game.oCell(game.toRow(input), game.toColumn(input));
			}
			game.setGameLog(input);
		} else {
			System.out.println(cellError);	
		}
	}
	
	/**
	 * Returns true if the player with X wins
	 * @param game The gameboard
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
	 * @param game The gameboard
	 * @return boolean value
	 */
	public static boolean playerOWins(GameBoard game) {
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
	 * @param game The gameboard
	 * @return boolean value
	 */
	public static boolean isDraw(GameBoard game) {
		
		for (int row=0; row<3; row++) {
			for (int column=0; column<3; column++) {
				if (game.getCell(row, column).isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Checks the winner of the game
	 * @param game 
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
	
	/**
	 * Goes back one move
	 * @param game The game board
	 */
	public static void resetGameMove(GameBoard game, int turn) {
		try {
			int lastMove = game.getGameLog().getLast();
			game.getGameLog().removeLast();
			
			switch (lastMove) {
				case 1: 
					game.clearCell(0, 0);
					break;
				case 2: 
					game.clearCell(0, 1);
					break;
				case 3: 
					game.clearCell(0, 2);
					break;
				case 4: 
					game.clearCell(1, 0);
					break;
				case 5: 
					game.clearCell(1, 1);
					break;
				case 6: 
					game.clearCell(1, 2);
					break;
				case 7: 
					game.clearCell(2, 0);
					break;
				case 8: 
					game.clearCell(2, 1);
					break;
				case 9: 
					game.clearCell(2, 2);
					break;
				default:
					break;
			}	
		} catch (Exception e) {
			System.out.println("Changed player symbol.");
			
			//TODO: Better exception handling here // unintended player change feature
			
		}
	}
}
