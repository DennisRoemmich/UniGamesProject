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
	
	
}
