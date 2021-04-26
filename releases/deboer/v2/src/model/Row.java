package model;

public enum Row {
	TOP(0), MIDDLE(1), BOTTOM(2);

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
			return Row.MIDDLE;
		case 2:
			return Row.BOTTOM;
		default:
			return Row.MIDDLE;
		}
	}
}
