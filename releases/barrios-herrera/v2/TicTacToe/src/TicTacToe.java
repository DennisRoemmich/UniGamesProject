import java.util.Random;
import java.util.Scanner;

public class TicTacToe {

	private static Random rn = new Random();
	static Scanner in = new Scanner(System.in);
	private static char[][] gameBoard;
	private static boolean turn = true;
	private static final int ROWS = 3;
	private static final int COLS = 3;
	private static char P1 = 'X';
	private static char P2 = 'O';
	private static char empty = ' ';
	private static int row;
	private static int col;
	private static TwoStacksTTT stackP1 = new TwoStacksTTT(30);
	private static TwoStacksTTT stackP2 = new TwoStacksTTT(30);

	// constructor
	public TicTacToe() {
		gameBoard = new char[ROWS][COLS];
		initializeBoard(gameBoard, empty);
	}

	// initializes the game board by filling it with a given character
	public void initializeBoard(char[][] board, char symbol) {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				board[i][j] = symbol;
			}
		}
	}

	// prints the game board
	public static String printBoard() {
		StringBuilder bld = new StringBuilder();
		String board = "";
		for (int x = 0; x < ROWS; x++) {
			for (int y = 0; y < COLS; y++) {
				if (y != COLS - 1) {
					bld.append(gameBoard[x][y] + "|");
				} else {
					bld.append(gameBoard[x][y]);
				}

			}

			if (x != ROWS - 1) {
				bld.append("\n-+-+-\n");
			}
		}
		board = bld.toString();
		return board;
	}

	// controls the game with 2 players on same computer
	public void startGame() {
		boolean valid;
		boolean validPos;

		while (!gameOver(gameBoard)) {
			System.out.println(printBoard());
			showTurn(turn);

			do {
				valid = false;
				askCoordinates();
				validPos = checkPos(gameBoard, row, col);

				if (validPos) {
					if (!checkNotEmp(gameBoard, row, col)) {
						System.out.println("That cell is already occupied, please choose an empty cell.");
					} else {
						valid = true;
					}
				} else {
					System.out.println("Please make sure you insert valid numbers only. Try again!");
				}
			} while (!valid);

			// Every player will be asked after every move if any undos are wanted
			if (turn) {
				int undo = 0;
				int moves = 0;
				makeMove(gameBoard, row, col, P1);
				int available = Math.max(stackP1.size1(), stackP2.size1());

				if (!gameOver(gameBoard)) {
					if (available < 0) {
						System.out.println("There are not undos available, keep playing!");
					} else {

						System.out.println(printBoard());
						if (available == 0) {
							System.out.println("There is: 1 undo available.");
						} else {
							System.out.println("There are: " + (available + 1) + " undos available.");
						}
						System.out.println("If you would like to undo please type in 1, else type in 0.");
						undo = in.nextInt();
						if (undo == 1) {
							System.out.println("How many moves would you like to undo?" + "(Undos available: "
									+ (available + 1) + ").");
							moves = in.nextInt();
							undoMoves(moves);
						} else {
							System.out.println("Ok. Let's keep playing!");
						}
					}
				}

			} else {
				int undo = 0;
				int moves = 0;
				makeMove(gameBoard, row, col, P2);
				int available = Math.max(stackP1.size1(), stackP2.size1());
				if (!gameOver(gameBoard)) {
					if (available < 0) {
						System.out.println("There are not undos available, keep playing!");
					} else {

						System.out.println(printBoard());
						if (available == 0) {
							System.out.println("There is: 1 undo available.");
						} else {
							System.out.println("There are: " + (available + 1) + " undos available.");
						}
						System.out.println("If you would like to undo please type in 1, else type in 0.");
						undo = in.nextInt();
						if (undo == 1) {
							System.out.println("How many moves would you like to undo?" + "(Undos available: "
									+ (available + 1) + ").");
							moves = in.nextInt();
							undoMoves(moves);
						} else {
							System.out.println("Ok. Let's keep playing!");
						}
					}
				}

			}

			turn = !turn;
		}
		System.out.println(printBoard());
		winner(gameBoard);
	}

	// writes a symbol inside the game board depending on which player's turn it is
	private static void makeMove(char[][] board, int x, int y, char symbol) {
		board[x][y] = symbol;
		if (symbol == P1) {
			stackP1.push1(x);
			stackP1.push2(y);
		} else {
			stackP2.push1(x);
			stackP2.push2(y);
		}
	}

	// ask for the desired row
	private static int askX() {
		System.out.println("Please insert row: ");
		return in.nextInt();

	}

	// ask for the desired column
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

		if (x >= 0 && x < board.length && y >= 0 && y < board.length) {
			status = true;
		} else {
			status = false;
		}

		return status;
	}

	// checks if the position is occupied
	private static boolean checkNotEmp(char[][] board, int row, int col) {

		if (board[row][col] != empty) {
			return false;
		}

		return true;
	}

	// checks if the game board does not have more empty spaces
	private static boolean filledBoard(char[][] board) {

		for (int x = 0; x < ROWS; x++) {
			for (int y = 0; y < COLS; y++) {
				if (board[x][y] == ' ') {
					return false;
				}
			}
		}

		return true;
	}

	// shows who is the winner and why was the game won
	private static void winner(char[][] board) {
		char symbol = checkHorizontal(board);

		if (symbol != empty) {

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

		if (symbol != empty) {

			if (symbol == P1) {
				System.out.println("P1 you are the winner by a diagonal line!");
			} else {
				System.out.println("P2 you are the winner by a diagonal line!");
			}

			return;

		}

		symbol = checkDiagonalOpp(board);

		if (symbol != empty) {

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

		if (filledBoard(board) || checkHorizontal(board) != ' ' || checkVertical(board) != empty
				|| checkDiagonal(board) != empty || checkDiagonalOpp(board) != empty) {
			return true;
		}

		return false;
	}

	// checks if there is a horizontal line with the same symbol
	private static char checkHorizontal(char[][] board) {
		char symbol;
		boolean equal;

		for (int x = 0; x < ROWS; x++) {

			equal = true;
			symbol = board[x][0];

			if (symbol != ' ') {
				for (int y = 1; y < COLS; y++) {
					if (symbol != board[x][y]) {
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
		boolean equal;

		for (int y = 0; y < COLS; y++) {
			equal = true;
			symbol = board[0][y];

			if (symbol != empty) {
				for (int x = 1; x < ROWS; x++) {
					if (symbol != board[x][y]) {
						equal = false;
					}
				}

			}
			if (equal) {
				return symbol;
			}
		}

		return empty;
	}

	// checks if there is a diagonal line with the same symbol
	private static char checkDiagonal(char[][] board) {

		char symbol = board[0][0];
		boolean equal = true;

		// Check diagonal from left to right
		if (symbol != empty) {
			for (int x = 0; x < ROWS; x++) {
				if (symbol != board[x][x]) {
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
	private static char checkDiagonalOpp(char[][] board) {
		char symbol;
		boolean equal = true;
		// Check diagonal from right to left
		symbol = board[0][COLS - 1];
		if (symbol != empty) {
			for (int x = 1, y = 1; x < ROWS; x++, y--) {
				if (symbol != board[x][y]) {
					equal = false;
				}
			}

			if (equal) {
				return symbol;
			}
		}

		return empty;
	}

	// gets computer coordinate for x
	private static int computerMoveX() {
		return rn.nextInt(3);

	}

	// gets computer coordinate for y
	private static int computerMoveY() {
		return rn.nextInt(3);

	}

	// starts a game against computer
	public void startGameComputer() {
		boolean valid;
		boolean validPos;
		int rowComp = 0;
		int colComp = 0;

		while (!gameOver(gameBoard)) {
			System.out.println(printBoard());
			showTurn(turn);

			do {
				valid = false;
				if (turn) {
					askCoordinates();
					validPos = checkPos(gameBoard, row, col);
					if (validPos) {
						if (!checkNotEmp(gameBoard, row, col)) {
							System.out.println("That cell is already occupied, please choose an empty cell.");
						} else {
							valid = true;
						}
					} else {
						System.out.println("Please make sure you insert valid numbers only. Try again!");
					}
				} else if (!turn) {
					rowComp = computerMoveX();
					colComp = computerMoveY();
					validPos = checkPos(gameBoard, rowComp, colComp);

					if (validPos) {
						if (!checkNotEmp(gameBoard, rowComp, colComp)) {
							valid = false;
						} else {
							valid = true;
						}
					}
				}
			} while (!valid);

			if (turn) {
				int undo = 0;
				int moves = 0;
				makeMove(gameBoard, row, col, P1);
				int available = Math.max(stackP1.size1(), stackP2.size1());

				if (!gameOver(gameBoard)) {
					if (available < 0) {
						System.out.println("There are not undos available, keep playing!");
					} else {

						System.out.println(printBoard());
						if (available == 0) {
							System.out.println("There is: 1 undo available.");
						} else {
							System.out.println("There are: " + (available + 1) + " undos available.");
						}
						System.out.println("If you would like to undo please type in 1, else type in 0.");
						undo = in.nextInt();
						if (undo == 1) {
							System.out.println("How many moves would you like to undo?" + "(Undos available: "
									+ (available + 1) + ").");
							moves = in.nextInt();
							undoMoves(moves);
						} else {
							System.out.println("Ok. Let's keep playing!");
						}
					}
				}
			} else {
				makeMove(gameBoard, rowComp, colComp, P2);
				System.out.println(printBoard());
				System.out.println("Computer played.");
			}

			turn = !turn;
		}

		System.out.println(printBoard());
		winner(gameBoard);
	}

	// undoes a desired amount of moves
	// keeps track of moves by using a 2 stacks 1 array for each player moves.
	private static void undoMoves(int moves) {
		for (int i = 0; i < moves; i++) {
			int x = stackP1.peek1();
			int y = stackP1.peek2();
			gameBoard[x][y] = empty;
			stackP1.pop1();
			stackP1.pop2();

			int z = stackP2.peek1();
			int n = stackP2.peek2();
			gameBoard[z][n] = empty;
			stackP2.pop1();
			stackP2.pop2();

		}

	}

}
