package model;

import game.Game;
import javafx.geometry.Pos;

public class TicTacToe extends Game {
	
	private GameBoard gameBoard = new GameBoard();
	
	public GameBoard getGameBoard() {
		return gameBoard;
	}

	private Sign currentSign = Sign.O;
	
	public Result getResult() {
		return ResultChecker.getResult(gameBoard);
	}
	public boolean isRunning() { return !getResult().isFinished(); };


	@Override
	public void executeAction(String action) {
		try {
			int i = Integer.parseInt(action);
			Position position = Position.valueOf(i);
			ticCell(position);
		} catch (Exception f) {

		}
	}

	@Override
	public void reset() {
		gameBoard = new GameBoard();
		currentSign = Sign.O;
	}
	
	public boolean ticCell(Position position, Sign sign) {
		// Check if all conditions allow to tic the cell
		if (getResult().isFinished()) return false;
		if (getResult() == Result.NOT_STARTED) {
			currentSign = sign;
		}
		if (currentSign != sign) return false;
		
		if (gameBoard.tic(position, sign)) {
			toggleSign();
			logAction(position.toString());
			return true;
		} else {
			return false;
		}
	}

	public boolean ticCell(Position position) {
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
