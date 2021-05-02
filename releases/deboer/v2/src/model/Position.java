package model;

public enum Position {
	
	TOPLEFT(Row.TOP,Column.LEFT),    
	TOPCENTER(Row.TOP,Column.CENTER),  
	TOPRIGHT(Row.TOP,Column.RIGHT), 
	CENTERLEFT(Row.CENTER,Column.LEFT), 
	CENTERCENTER(Row.CENTER,Column.CENTER), 
	CENTERRIGHT(Row.CENTER,Column.RIGHT),
	BOTTOMLEFT(Row.BOTTOM,Column.LEFT), 
	BOTTOMCENTER(Row.BOTTOM,Column.CENTER), 
	BOTTOMRIGHT(Row.BOTTOM,Column.RIGHT);
	
	private final Row row;
	private final Column column;
	
	Position(Row row, Column column) {
		this.row = row;
		this.column = column;
	}
	
	public static Position valueOf(Row row, Column column) {
		String name = row.toString() + column.toString();
		return Position.valueOf(name);
	}
	
	public Row getRow() {
		return row;
	}

	public Column getColumn() {
		return column;
	}

	public int getIndex() {
		return row.toInt() * 3 + column.toInt();
	}
	
	public static Position valueOf(int i) {
		if (i < 1 || i > 9) {
			throw new IllegalArgumentException("Only numbers from 1-9 identify Positions.");
		}
		i--;
		return Position.valueOf(Row.fromInt(i/3),Column.fromInt(i%3));
	}
	
	@Override
	public String toString() {
		return Integer.toString(getIndex());
	}
}
