package model;

public enum Row {
	TOP(0), MIDDLE(1), BOTTOM(2);

	private int value;

	public int toInt() {
		return value;
	}
	
	private Row(int value) {
		this.value = value;
	}
}
