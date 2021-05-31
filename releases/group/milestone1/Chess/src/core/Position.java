package core;

import java.security.InvalidParameterException;

public class Position {

    private Row row;
    private Column column;

    public Position(Row row, Column column) {
        this.row = row;
        this.column = column;
    }

    public Position(String name) {
        Column column = Column.valueOf(name.charAt(0));
        Row row = Row.valueOf(name.charAt(1));
        if(row != null && column != null) {
            this.row = row;
            this.column = column;
        } else {
            throw new InvalidParameterException();
        }
    }

    public Row getRow() {
        return row;
    }

    public Column getColumn() {
        return column;
    }

    public String toString() {
        return column.toString() + String.valueOf(row.toString().charAt(1));
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

}
