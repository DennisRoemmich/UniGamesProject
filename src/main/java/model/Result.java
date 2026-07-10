package model;

public enum Result {
	X_WON(true), O_WON(true), TIE(true), IN_PROGRESS(false), NOT_STARTED(false);
	
	private final boolean isFinished;
	
	Result(boolean value) {
		this.isFinished = value;
	}

	@Override
	public String toString() {
		switch(this) {
		case X_WON:
			return "X won";
		case O_WON:
			return "O won";
		case TIE:
			return "Tie";
		case IN_PROGRESS:
			return "in progress";
		case NOT_STARTED:
			return "not started";
		default:
			return "Default";
		}
	}

	public boolean isFinished() {
		return isFinished;
	}
}
