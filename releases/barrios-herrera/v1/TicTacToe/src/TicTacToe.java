import java.util.Scanner;

public class TicTacToe {

	static Scanner in = new Scanner(System.in);
	private static char[][] gameBoard = new char[5][5];
	private static boolean turn = true;
	private static char P1 = 'X';
	private static char P2 = 'O';
	private static int row;
	private static int col;

	// Creates the game board
	private static void fillGameBoard(char[][] board) {

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (j % 2 != 0 && i % 2 == 0) {
					board[i][j] = '|';
				} else if (i % 2 != 0 && j % 2 == 0) {
					board[i][j] = '-';
				} else if (i % 2 != 0 && j % 2 != 0) {
					board[i][j] = '+';
				} else {
					board[i][j] = ' ';
				}
			}
		}

	}

	// prints the game board
	private static void printGameBoard(char[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
	}

	// writes a symbol inside the game board depending on which player's turn it is
	private static void makeMove(char[][] board, int x, int y, char symbol) {
		board[x][y] = symbol;
	}

	// starts the game
	public static void startGame() {
		fillGameBoard(gameBoard);
		boolean valid;
		boolean validPos;

		// while the game is not over, run the game
		while (!gameOver(gameBoard)) {
			printGameBoard(gameBoard);
			showTurn(turn);

			// while the position is not valid try to get a valid position
			do {
				valid = false;
				askCoordinates();
				validPos = checkPos(gameBoard, row, col);

				// if the position given is inside the game board then check if it's a valid
				// position
				if (validPos) {
					if (!checkNotEmp(gameBoard, row, col)) {
						System.out.println("That cell is already occupied, please choose an empty cell.");
					} else {
						valid = true;
					}

				} else {
					System.out.println("Please make sure you insert valid numbers. Try again!");
				}

			} while (!valid);

			if (turn) {
				makeMove(gameBoard, row, col, P1);
			} else {
				makeMove(gameBoard, row, col, P2);
			}

			turn = !turn;
		}

		printGameBoard(gameBoard);
		winner(gameBoard);
	}

	// ask for the desired row
	private static int askX() {
		System.out.println("Please insert row: ");
		return in.nextInt();

	}

	// asked for the desired column
	private static int askY() {
		System.out.println("Please insert column: ");
		return in.nextInt();
	}

	// asks for desired row and column
	private static void askCoordinates() {
		row = askX();
		col = askY();
	}

	// Shows whose turn it is
	private static void showTurn(boolean turn) {
		if (turn) {
			System.out.println("Player 1 is your turn!");
		} else {
			System.out.println("Player 2 is your turn!");
		}
	}

	// checks if the position is a valid position
	private static boolean checkPos(char[][] board, int x, int y) {
		boolean status = true;

		if (x >= 0 && x < board.length && y >= 0 && y < board.length && x % 2 == 0 && y % 2 == 0) {
			status = true;
		} else {
			status = false;
		}

		return status;
	}

	// checks if the position is occupied
	private static boolean checkNotEmp(char[][] board, int row, int col) {

		if (board[row][col] != ' ') {
			return false;
		}

		return true;
	}

	// checks if the game board does not have more empty spaces
	private static boolean filledBoard(char[][] board) {

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == ' ') {
					return false;
				}
			}
		}

		return true;
	}

	// shows whose the winner
	private static void winner(char[][] board) {
		char symbol = checkHorizontal(board);

		if (symbol != ' ') {

			if (symbol == P1) {
				System.out.println("P1 you are the winner by a horizontal line!");
			} else {
				System.out.println("P2 you are the winner by a horizontal line!");
			}

			return;

		}

		symbol = checkVertical(board);

		if (symbol != ' ') {

			if (symbol == P1) {
				System.out.println("P1 you are the winner by a vertical line!");
			} else {
				System.out.println("P2 you are the winner by a vertical line!");
			}

			return;

		}

		symbol = checkDiagonal(board);

		if (symbol != ' ') {

			if (symbol == P1) {
				System.out.println("P1 you are the winner by a diagonal line!");
			} else {
				System.out.println("P2 you are the winner by a diagonal line!");
			}

			return;

		}

		System.out.println("Is a tie!");

	}

	// Controls when the game is over
	private static boolean gameOver(char[][] board) {

		if (filledBoard(board) || checkHorizontal(board) != ' ' || checkVertical(board) != ' '
				|| checkDiagonal(board) != ' ') {
			return true;
		}

		return false;
	}

	// checks if there is a horizontal line with the same symbol
	private static char checkHorizontal(char[][] board) {
		char symbol;
		boolean equal;

		for (int i = 0; i < board.length; i++) {

			equal = true;
			symbol = board[i][0];

			if (symbol != ' ') {
				for (int j = 1; j < board[0].length; j++) {
					if (symbol != board[i][j]) {
						equal = false;
					}
				}

				if (equal) {
					return symbol;
				}
			}
		}

		return ' ';
	}

	// checks if there is a vertical line with the same symbol
	private static char checkVertical(char[][] board) {

		char symbol;
		boolean equal = true;

		for (int j = 0; j < board.length; j++) {
			symbol = board[0][j];

			if (symbol != ' ') {
				for (int i = 1; i < board[0].length; i++) {
					if (symbol != board[i][j]) {
						equal = false;
					}
				}

				if (equal) {
					return symbol;
				}
			}
		}

		return ' ';
	}

	// checks if there is a diagonal line with the same symbol
	private static char checkDiagonal(char[][] board) {

		char symbol = board[0][0];
		boolean equal = true;

		// Check diagonal from left to right
		if (symbol != ' ') {
			for (int i = 0; i < board.length; i++) {
				if (symbol != board[i][i]) {
					equal = false;
				}
			}

			if (equal) {
				return symbol;
			}
		}

		// Check diagonal from right to left
		symbol = board[0][4];
		if (symbol != ' ') {
			for (int i = 2, j = 2; i < board.length && j < board[i].length; i++, j--) {
				if (symbol != board[i][j]) {
					equal = false;
				}
			}

			if (equal) {
				return symbol;
			}
		}

		return ' ';
	}

}
