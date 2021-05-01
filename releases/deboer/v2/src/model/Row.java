package model;

public enum Row {
	TOP(0), CENTER(1), BOTTOM(2);

	private int value;
	
	private Row(int value) {
		this.value = value;
	}

	public int toInt() {
		return value;
	}
	
	public static Row fromInt(int value) {
		switch (value) {
		case 0:
			return Row.TOP;
		case 1:
			return Row.CENTER;
		case 2:
			return Row.BOTTOM;
		default:
			return Row.CENTER;
		}
	}

	// Get the 3 Positions with the Row
	public Position[] getPositions() {
		Column[] columns = Column.values();
		Position[] positions = new Position[columns.length];
		for(int i = 0; i < columns.length; i++) {
			positions[i] = Position.valueOf(this, columns[i]);
		}
		return positions;
	}
}
