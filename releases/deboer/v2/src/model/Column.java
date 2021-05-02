package model;

public enum Column {
	LEFT(0), CENTER(1), RIGHT(2);
	
	private int value;

	private Column(int value) {
		this.value = value;
	}

	public int toInt() {
		return value;
	}
	
	public static Column fromInt(int value) {
		switch (value) {
		case 0:
			return Column.LEFT;
		case 1:
			return Column.CENTER;
		case 2:
			return Column.RIGHT;
		default:
			return Column.CENTER;
		}
	}

	// Get the Positions of the Column
	public Position[] getPositions() {
		Row[] rows = Row.values();
		Position[] positions = new Position[rows.length];
		for(int i = 0; i < rows.length; i++) {
			positions[i] = Position.valueOf(rows[i], this);
		}
		return positions;
	}
}
