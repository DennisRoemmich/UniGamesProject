package model;

public enum Sign {
	X, O, NONE;
	
	@Override
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
