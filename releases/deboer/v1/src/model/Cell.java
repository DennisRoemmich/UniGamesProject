package model;

import java.util.Optional;

public class Cell {
	public Optional<Player> player;
	
	public void clearCell() {
		this.player = null;
	}
	
	public void setPlayer(Player player) {
		this.player = Optional.of(player);
	}
	
	public Boolean isFree() {
		return player == null;
	}
	
	public Cell() {
		this.player = null;
	}
}
