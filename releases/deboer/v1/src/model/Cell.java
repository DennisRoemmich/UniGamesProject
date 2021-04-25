package model;

public class Cell {
	public Player player;
	
	public void clearCell() {
		this.player = Player.NONE;
	}

	public Boolean isFree() {
		return player == Player.NONE;
	}
	
	public Cell() {
		this.player = Player.NONE;
	}
	
	public String toString() {
		return player.toString();
	}
	
}
