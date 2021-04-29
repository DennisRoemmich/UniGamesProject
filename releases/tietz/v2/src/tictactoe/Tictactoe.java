package tictactoe;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

public class Tictactoe {

	private int[][] mBoard;
	private int mStartPlayer;
	private int mCurrentPlayer;
	private int mCurrentMove;
	private int[] mHistory;
	private int[] mScore;
	private String mPone = "Player 1";
	private String mPtwo = "Player 2";
	private int mMode;
	
	Random mRand = new Random();
	
	// constructor
	public Tictactoe() {
		
		mBoard = new int[3][3];
		initializeBoard();
		
		mStartPlayer = 1;
		mCurrentMove = 0;
		
		mHistory = new int[9];
		mScore = new int[2];
	}
	
	// initializes the board one first time
	private void initializeBoard() {
		
		for (int i = 0; i < this.mBoard.length; i++) {
			
			for (int j = 0; j < this.mBoard.length; j++) {
				
				this.mBoard[i][j] = 0;
			}
		}
	}
	
	
	// prints the board to the console
	private void printBoard(int m) {
		
		System.out.println("+" + "-" + "-" + "-" + "-" + "-" + "+");
		
		System.out.print("|");
		for (int i = 0; i < this.mBoard.length; i++) {
			
			if (m == 0) {
				this.printHelp(0, i);
				
			} else {
				System.out.print(1 + i + "|");
			}
		}
		System.out.println("");
		
		System.out.println("|" + "-" + "+" + "-" + "+" + "-" + "|");
		
		System.out.print("|");
		for (int i = 0; i < this.mBoard.length; i++) {
			
			if (m == 0) {
				this.printHelp(1, i);
				
			} else {
				System.out.print(4 + i + "|");
			}
		}
		System.out.println("");
		
		System.out.println("|" + "-" + "+" + "-" + "+" + "-" + "|");
		
		System.out.print("|");
		for (int i = 0; i < this.mBoard.length; i++) {
			
			if (m == 0) {
				this.printHelp(2, i);
				
			} else {
				System.out.print(7 + i + "|");
			}
		}
		System.out.println("");
		
		System.out.println("+" + "-" + "-" + "-" + "-" + "-" + "+");
	}
	
	// helps to print the board
	private void printHelp(int r, int c) {
		
		if (this.mBoard[r][c] == 0) {
			
			System.out.print(" " + "|");
			
		} else if (this.mBoard[r][c] == 1) {
			
			System.out.print("X" + "|");
			
		} else {
			
			System.out.print("O" + "|");
			}
	}
	
	// introduces the game and how to play
	public void begin() {
		
		System.out.println("Welcome to Tic Tac Toe!");
		System.out.println("The gameboard is nummered as follows:");
		
		this.printBoard(1);
		
		this.start();
	}
	
	// sets the game mode (PvP, PvE, EvE)
	private void start() {
		
		System.out.println("To play PvP: Press [p] - To play PvE: Press [e] - To simulate PvP: Press [s]");
		
		String i = this.getInput();
		
		if ("p".equals(i)) {
			
			this.mMode = 0;
			
			this.pvP();
			
		} else if ("e".equals(i)) {
			
			this.mMode = 1;
			
			this.pvE();
			
		} else if ("s".equals(i)) {
			
			this.mMode = 2;
			
			this.evE();
			
		} else {
			
			System.out.println("Please ensure to enter only one of the options given!");
			
			this.start();
		}
	}
	
	// starts the game to play with two players
	private void pvP() {
		
		System.out.println("Please enter the name of player 1: ");
		
		String po = this.getInput();
		
		System.out.println("Please enter the name of player 2: ");
		
		String pt = this.getInput();
		
		this.mPone = po;
		this.mPtwo = pt;
		
		System.out.println("Let's get started!");
		
		this.play();
	}
	
	// starts the game to play for one player against environment
	private void pvE() {
		
		System.out.println("Please enter the name of the player: ");
		
		String po = this.getInput();
		
		this.mPone = po;
		this.mPtwo = "Computer";
		
		System.out.println("Let's get started!");
		
		this.play();
	}
	
	// simulates a game (both players by computer)
	private void evE() {
		
		this.mPone = "X";
		this.mPtwo = "O";
		
		this.play();
	}
	
	
	// actual game process
	private void play() {
		
		this.mCurrentPlayer = this.mStartPlayer;
		int nextMove;
		
		this.printBoard(0);
		
		// runs until a player wins or it's a draw
		while (this.gamestatus() == 2) {
			
			this.mCurrentMove++;
			
			nextMove = this.getMove();
				
			// runs until input is valid
			while (!check(nextMove)) {
				
				nextMove = this.getMove();
			}
		
			this.move(nextMove);
			
			// the other player's turn
			this.mCurrentPlayer = -this.mCurrentPlayer;
		}
		
		// game is finished
		this.result();
		
		// 
		this.resume();
	}

	
	// reads from the console
	private String getInput() {
		
		try {
			
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			return in.readLine();
			
		} catch (Exception e) {
			
			System.out.println("Fatal error: peep, peeeep, peeeeeeeep, peeeeeeeeeeeeeeeep, ...");
		}
		
		return "";
	}
	
	// reads int from the console
	private int getMove() {
		
		if (this.mMode == 0 || this.mMode == 1 && this.mCurrentPlayer == 1) {
			
			if (this.mCurrentPlayer == 1) {
				
				System.out.println("Please insert your position, " + mPone + "!");
				
			} else {
				
				System.out.println("Please insert your position, " + mPtwo + "!");
			}
			
			String s = getInput();
			
			try {
				
				return Integer.parseInt(s);
				
			} catch (Exception e) {
				
				System.out.print("");
			}
			
		} else {
			
			wait(700);
			
			return mRand.nextInt(9) + 1;
		}
		
		return 0;
	}
	
	// checks whether the move is valid
	private boolean check(int move) {
		
		boolean m = this.mMode == 0 || this.mMode == 1 && this.mCurrentPlayer == 1;
		
		String occ = "Field already occupied! Please choose an other field!";
		
		// invalid input
		if (move < 1 || move > 9) {
			
			if (m) {
				
				System.out.println("Please ensure to enter a number between 1 and 9!");
			}
			
			return false;
			
		// field already occupied
		} else if (this.mBoard[(move - 1) / 3][(move - 1) % 3] != 0) {
			
			if (m) {
				
				System.out.println(occ);
			}
			
			return false;
			
		} else {
		
			if (this.mCurrentPlayer == 1) {
					
				System.out.println(this.mPone + " plays " + move);
					
			} else {
					
				System.out.println(this.mPtwo + " plays " + move);
					
			}
			
			return true;
		}
	}
	
	// sets a correct move and prints the updated board
	private void move(int pos) {
		
		this.mBoard[(pos - 1) / 3][(pos - 1) % 3] = this.mCurrentPlayer;
		this.mHistory[this.mCurrentMove - 1] = pos;
		
		this.printBoard(0);
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
		
		for (int i = 0; i < this.mBoard.length; i++) {
			
			for (int j = 0; j < this.mBoard[i].length; j++) {
				
				sumr = sumr + this.mBoard[i][j];
				sumc = sumc + this.mBoard[j][i];
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
		
		sumd1 = this.mBoard[0][0] + this.mBoard[1][1] + this.mBoard[2][2];
		sumd2 = this.mBoard[2][0] + this.mBoard[1][1] + this.mBoard[0][2];
		
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
		for (int i = 0; i < this.mBoard.length; i++) {
			
			for (int j = 0; j < this.mBoard[i].length; j++) {
				
				if (this.mBoard[i][j] == 0) {
					
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
			
			System.out.println(this.mPone + " wins! Congratulations!");
			this.mScore[0]++;
			
		}
		if (r == -1) {
			
			System.out.println(this.mPtwo + " wins! Congratulations!");
			this.mScore[1]++;
			
		}
		if (r == 0) {
			
			System.out.println("Draw! Nobody wins! Try again!");
		}
		
		this.displayScore();
	}
	
	// displays the current score to the console
	private void displayScore() {
		
		System.out.println("Current Score: " + this.mPone + " " + this.mScore[0] + 
							" : " + this.mScore[1] + " " + this.mPtwo);
		
		System.out.println("");
	}
	
	// presents options after a game has ended (replay, continue, end)
	private void resume() {
		
		System.out.println("For replay: Press [r] - To continue: Press [c] - To end: Press [e]");
		
		String i = this.getInput();
		
		if ("r".equals(i)) {
			
			this.replay();
			
		} else if ("c".equals(i)) {
			
			this.resetGame();
			
			this.play();
			
		} else if ("e".equals(i)) {
			
			System.out.print("Thank you for playing!");
			
		} else {
			
			System.out.println("Please ensure to enter only one of the options given!");
			
			this.resume();
		}
	}
	
	// replays the latest game
	private void replay() {
		
		this.resetReplay();
		
		System.out.println("Replay: ");
		
		this.printBoard(0);
		
		for (int i = 0; i < this.mHistory.length; i++) {
			
			if (this.mHistory[i] != 0) {
				
				this.mCurrentMove++;
				
				this.move(this.mHistory[i]);
				
				this.mCurrentPlayer = -this.mCurrentPlayer;
				
				wait(800);
			}
		}
		
		this.displayScore();
		
		this.resume();
	}
	
	// resets variables to do replay
	private void resetReplay() {
		
		this.resetBoard();
		this.mCurrentMove = 0;
		this.mCurrentPlayer = this.mStartPlayer;
	}
	
	// resets variables to start a new game
	private void resetGame() {
		
		this.resetBoard();
		this.mCurrentMove = 0;
		this.resetHistory();
		
		this.mStartPlayer = -this.mStartPlayer;
	}
	
	// clears the board
	private void resetBoard() {
		
		for (int i = 0; i < this.mBoard.length; i++) {
			
			for (int j = 0; j < this.mBoard[i].length; j++) {
				
				this.mBoard[i][j] = 0;
			}
		}
	}
	
	// erases the history
	private void resetHistory() {
		
		for (int i = 0; i < this.mHistory.length; i++) {
			
			this.mHistory[i] = 0;
		}
	}
	
	// waits to execute the next order
	public static void wait(int ms) {
		
	    try {
	    	
	        Thread.sleep(ms);
	        
	    } catch (InterruptedException ex) {
	    	
	        Thread.currentThread().interrupt();
	    }
	}
	
}
