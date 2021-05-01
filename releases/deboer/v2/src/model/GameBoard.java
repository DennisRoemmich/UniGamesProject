package model;

public class GameBoard {
	private Sign[] signs;
	
	public GameBoard() {
		initCells();
	}
	
	public GameBoard clone() {
		GameBoard newBoard = new GameBoard();
		for(Position pos: Position.values()) {
			newBoard.signs[pos.getIndex()] = this.signs[pos.getIndex()];
		}
		return newBoard;
	}

	private void initCells() {
		signs = new Sign[9];
		for(Position position: Position.values()) {
			signs[position.getIndex()] = Sign.NONE;
		}
	}
	
	public Sign getSign(Position position) {
		return signs[position.getIndex()];
	}
	
	public Sign[] getSigns(Position... positions) {
		Sign[] signsAtPositions = new Sign[positions.length];
		for (int i = 0; i < positions.length; i++) {
			signsAtPositions[i] = getSign(positions[i]);
		}
		return signsAtPositions;
	}
	
	public void resetSigns() {
		for(Position position: Position.values()){
			signs[position.getIndex()] = Sign.NONE;
		}
	}
	
	public boolean tic(Position position, Sign sign) {
		if(isFree(position) && sign != null) {
			signs[position.getIndex()] = sign;
			return true;
		} else { 
			return false;
		}
	}
	
	public Position getRandomFreePosition() {
		for(Position position: Position.values()) {
			if(isFree(position)) return position;
		}
		return null;
	}
	
	public boolean freeCellAvailable() {
		return getRandomFreePosition() != null;
	}
	
	public boolean isFree(Position position) {
		return signs[position.getIndex()] == Sign.NONE;
	}
}
