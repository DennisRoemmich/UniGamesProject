package model;

public class TicTacToe {
	private Cell cells[][];
	
	private Player nextPlayer;
	
	public TicTacToePresenter presenter;
	
	boolean isRunning = false;
	
	public TicTacToe() {
		initCells();
		nextPlayer = Player.O;
	}
	
	public void initCells() {
		cells = new Cell[3][3];
		resetCells();
	}
	
	public Cell getCell(Row row, Column column) {
		return cells[row.toInt()][column.toInt()];
	}
	
	private void resetCells() {
		for (Row row: Row.values()) {
			for (Column column: Column.values()) {
				cells[row.toInt()][column.toInt()] = new Cell();
			}
		}
	}
	
	public Boolean ticCell(Row row, Column column, Player player) {
		
		// Check if all conditions allow to tic the cell
		if (getWinner() != Player.NONE) return false;
		if (nextPlayer != player) return false;
		if (!cells[row.toInt()][column.toInt()].isFree()) return false;
		
		cells[row.toInt()][column.toInt()].player = player;
		togglePlayer();
		return true;
	}
	
	public Boolean ticCell(Row row, Column column) {
		return ticCell(row, column, nextPlayer);
	}
	
	private Boolean ticCell(CellPosition position) {
		return ticCell(position.row, position.column);
	}
	
	private void togglePlayer() {
		if(nextPlayer == Player.X) {
			nextPlayer = Player.O;
		} else {
			nextPlayer = Player.X;
		}
	}
	
	public void stopGameIfWon() {
		if (getWinner() != Player.NONE) {
			isRunning = false;
		}
	}
	
	public Player getWinner() {
		
		Player p;
		
		p = checkForWinningRow();
		if (p != Player.NONE) return p;

		p = checkForWinningColumn();
		if (p != Player.NONE) return p;
		
		p = checkForWinningDiagonal();
		return p;
	}

	private Player checkForWinningDiagonal() {
		for(int offset = 0; offset <= 2; offset += 2) {
			Player p = playerInCells(cells[0][0+offset], cells[1][1], cells[2][2-offset]);
			if (p != Player.NONE) {
				return p;
			}
		}
		return Player.NONE;
	}

	private Player checkForWinningColumn() {
		for(Column column: Column.values()) {
			Player p = playerInCells(cells[0][column.toInt()], cells[1][column.toInt()], cells[2][column.toInt()]);
			if (p != Player.NONE) {
				return p;
			}
		}
		return Player.NONE;
	}

	private Player checkForWinningRow() {
		for(Row row: Row.values()) {
			Player p = playerInCells(cells[row.toInt()][0], cells[row.toInt()][1], cells[row.toInt()][2]);
			if (p != Player.NONE) {
				return p;
			}
		}
		return Player.NONE;
	}
	
	
	public Player playerInCells(Cell a, Cell b, Cell c) {
		if (a.player == b.player && a.player == c.player) {
			return a.player;
		} else {
			return Player.NONE;
		}
	}

	public void startGame() {
		isRunning = true;
		presenter.displayBoard(cells);	
		handleInput();	
	}
	
	public void handleInput() {
		if (!ticCell(presenter.getNextCellPosition())) {
			presenter.handleInvalidPosition();
			presenter.displayBoard(cells);
			handleInput();
		} else {
			presenter.displayBoard(cells);
			stopGameIfWon();
			if(isRunning) {
				handleInput();
			} else {
				presenter.displayWinner(getWinner());
			}
		}
	}
}
