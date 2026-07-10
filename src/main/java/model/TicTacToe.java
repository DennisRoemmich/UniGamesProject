package model;

public class TicTacToe {
	
	private GameBoard gameBoard = new GameBoard();
	
	private GameBoard[] history = new GameBoard[9];
	private int currentMove = 0;
	
	public GameBoard getGameBoard() {
		return gameBoard;
	}

	private Sign currentSign = Sign.O;
	
	public Result getResult() {
		return ResultChecker.getResult(gameBoard);
	}
	public boolean isRunning() { return !getResult().isFinished(); };

	public void reset() {
		gameBoard = new GameBoard();
		history = new GameBoard[9];
		currentMove = 0;
		currentSign = Sign.O;
	}
	
	public Boolean ticCell(Position position, Sign sign) {
		// Check if all conditions allow to tic the cell
		if (getResult().isFinished()) return false;
		if (getResult() == Result.NOT_STARTED) {
			currentSign = sign;
		}
		if (currentSign != sign) return false;
		
		if (gameBoard.tic(position, sign)) {
			toggleSign();
			history[currentMove] = (GameBoard) gameBoard.clone();
			currentMove++;
			return true;
		} else {
			return false;
		}
	}
	
	public GameBoard[] getHistory() {
		return history;
	}
	
	public Boolean ticCell(Position position) {
		return ticCell(position, currentSign);
	}
	
	private void toggleSign() {
		if(currentSign == Sign.O) {
			currentSign = Sign.X;
		} else if(currentSign == Sign.X) {
			currentSign = Sign.O;
		}
	}
}
