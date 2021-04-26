package model;

public abstract class TicTacToePresenter {
	
	public abstract void displayBoard(Cell[][] cells);
	
	public abstract void displayWinner(Player winner);

	protected abstract CellPosition getNextCellPosition();

	protected abstract void handleInvalidPosition();
	
}
