package model;

public enum Player {
	X, O, NONE;
	
	public String toString() {
		switch(this) {
		case X:
			return "X";
		case O:
			return "O";
		default:
			return " ";
		}
	}
}
