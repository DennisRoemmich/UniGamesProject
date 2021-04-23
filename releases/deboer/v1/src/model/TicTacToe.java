package model;

public class TicTacToe {
	private Cell cells[][];
	
	private Player nextPlayer;
	
	public TicTacToe() {
		initCells();
		nextPlayer = Player.O;
	}
	
	private void initCells() {
		cells = new Cell[3][3];
		resetCells();
	}
	
	private void resetCells() {
		for (Row row: Row.values()) {
			for (Column column: Column.values()) {
				cells[row.toInt()][column.toInt()] = new Cell();
			}
		}
	}
	
	public Boolean ticCell(Row row, Column column, Player player) {
		if (nextPlayer != player) {
			return false;
		}
		if (!cells[row.toInt()][column.toInt()].isFree()) {
			return false;
		}
		cells[row.toInt()][column.toInt()].setPlayer(player);
		togglePlayer();
		return true;
	}
	
	private void togglePlayer() {
		if(nextPlayer == Player.X) {
			nextPlayer = Player.O;
		} else {
			nextPlayer = Player.X;
		}
	}
}
