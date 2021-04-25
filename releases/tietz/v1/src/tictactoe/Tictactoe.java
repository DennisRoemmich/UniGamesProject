package tictactoe;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.*;

public class Tictactoe {

	private int[][] board;
	private int currentPlayer;
	
	// constructor
	public Tictactoe() {
		board = new int[3][3];
		currentPlayer = 1;
		initializeBoard();
	}
	
	// initializes the board one first time
	private void initializeBoard() {
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board.length; j++) {
				this.board[i][j] = 0;
			}
		}
	}
	
	
	// prints the board to the console
	private void printBoard() {
		
		System.out.println("+" + "-" + "-" + "-" + "-" + "-" + "+");
		
		System.out.print("|");
		for (int i = 0; i < this.board.length; i++) {
			this.printHelp(0, i);
		}
		System.out.println("");
		
		System.out.println("|" + "-" + "+" + "-" + "+" + "-" + "|");
		
		System.out.print("|");
		for (int i = 0; i < this.board.length; i++) {
			this.printHelp(1, i);
		}
		System.out.println("");
		
		System.out.println("|" + "-" + "+" + "-" + "+" + "-" + "|");
		
		System.out.print("|");
		for (int i = 0; i < this.board.length; i++) {
			this.printHelp(2, i);
		}
		System.out.println("");
		
		System.out.println("+" + "-" + "-" + "-" + "-" + "-" + "+");
	}
	
	// helps to print the board
	private void printHelp(int r, int c) {
		if (this.board[r][c] == 0) {
			System.out.print(" " + "|");
		} else if (this.board[r][c] == 1) {
			System.out.print("X" + "|");
		} else {
			System.out.print("O" + "|");
			}
	}
	
	// actual game process
	private void play() {
		
		int nextMove;
		
		this.printBoard();
		
		// runs until a player wins or it's a draw
		while (this.gamestatus() == 2) {
			nextMove = this.getInput();
			
			// runs until input is valid
			while (!check(nextMove)) {
				nextMove = this.getInput();
			}
			
			this.move(nextMove);
			
			// the other player's turn
			this.currentPlayer = -this.currentPlayer;
		}
		
		// game is finished
		this.result();
		
	}
	
	// reads from the console
	private int getInput() {
		
		if (this.currentPlayer == 1) {
			System.out.println("Please insert your position, Player 1: ");
		} else {
			System.out.println("Please insert your position, Player 2: ");
		}
		
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String input = in.readLine();
			return Integer.parseInt(input);
		} catch (Exception e) {
			System.out.print("");
		}
		
		return 0;
		
	}
	
	// checks whether the move is valid
	private boolean check(int move) {
		
		String occ = "Field already occupied! Please choose an other field!";
		
		// invalid input
		if (move < 1 || move > 9) {
			System.out.println("Please ensure to enter a number between 1 and 9!");
			return false;
			
		// field already occupied
		} else if (this.board[(move - 1) / 3][(move - 1) % 3] != 0) {
			System.out.println(occ);
			return false;
		} else {
			return true;
		}
	}
	
	// sets a correct move and prints the updated board
	private void move(int pos) {
		
		this.board[(pos - 1) / 3][(pos - 1) % 3] = this.currentPlayer;
		
		this.printBoard();
	}
	
	// checks the current game status (win, draw or running)
	private int gamestatus() {
		
		// checks for winner
		int w = this.checkWin();
		if (w != 0) {
			return w;
		}
		
		// checks for draw
		int d = this.checkDraw();
		if (d == 1) {
			return 0;
		}
		
		// game still running
		return 2;
		
	}
	
	// checks whether one of the players has already won
	private int checkWin() {
		
		// checks the rows and columns
		int sumr = 0;
		int sumc = 0;
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[i].length; j++) {
				sumr = sumr + this.board[i][j];
				sumc = sumc + this.board[j][i];
			}
			if (sumr == 3 || sumc == 3) {
				return 1;
			} else if (sumr == -3 || sumc == -3) {
				return -1;
			}
			sumr = 0;
			sumc = 0;
		}
		
		// checks the diagonals
		int sumd1 = 0;
		int sumd2 = 0;
		sumd1 = this.board[0][0] + this.board[1][1] + this.board[2][2];
		sumd2 = this.board[2][0] + this.board[1][1] + this.board[0][2];
		if (sumd1 == 3 || sumd2 == 3) {
			return 1;
		} else if (sumd1 == -3 || sumd2 == -3) {
			return -1;
		}
		
		// no winner
		return 0;
	}
	
	// checks whether its a draw or not
	private int checkDraw() {
		
		// checks for no draw (or free fields)
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[i].length; j++) {
				if (this.board[i][j] == 0) {
					return 0;
				}
			}
		}
		
		// draw
		return 1;
	}
	
	// evaluates the result and prints it to the console
	private void result() {
		
		int r = this.gamestatus();
		
		if (r == 1) {
			System.out.println("Player 1 wins! Congratulations!");
		}
		if (r == -1) {
			System.out.println("Player 2 wins! Congratulations!");
		}
		if (r == 0) {
			System.out.println("Draw! Nobody wins! Try again!");
		}
	}
	
}
