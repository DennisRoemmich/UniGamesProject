package core.positioning;

import java.security.InvalidParameterException;

public class Position {

    private Row row;
    private Column column;

    public Position(Row row, Column column) {
        this.row = row;
        this.column = column;
    }

    public Position(String name) {
        try {
        Column column = Column.valueOf(name.charAt(0));
        Row row = Row.valueOf(name.charAt(1));
            this.row = row;
            this.column = column;
        } catch (Exception e) {
            throw e;
        }
    }

    public Row getRow() {
        return row;
    }

    public Column getColumn() {
        return column;
    }

    public String toString() {
        return column.toString() + row.toString();
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof Position)) {
            return false;
        }

        Position p = (Position) o;

        // Compare the data members and return accordingly
        return p.column == this.column && p.row == this.row;
    }

    public static Position[] values() {
        Position[] positions = new Position[64];
        for(Row row : Row.values()) {
            for(Column column : Column.values()) {
                positions[row.getIndex() * 8 + column.getIndex()] = new Position(row, column);
            }
        }
        return positions;
    }

    public Position clone() {
        return new Position(row, column);
    }

}
