package model;

public enum Column {
	LEFT(0), MIDDLE(1), RIGHT(2);
	
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
			return Column.MIDDLE;
		case 2:
			return Column.RIGHT;
		default:
			return Column.MIDDLE;
		}
	}
	
}
