package model.tile;

import model.Direction;

public class TilePosition {
    private final int row;
    private final int column;

    public TilePosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public TilePosition getNext(Direction direction) {
        int newColumn = column;
        if (direction == Direction.RIGHT || direction == Direction.UPRIGHT) {
            newColumn++;
        } else if(direction == Direction.LEFT || direction == Direction.DOWNLEFT) {
            newColumn--;
        }
        int newRow = row;
        if(direction == Direction.UPLEFT || direction == Direction.UPRIGHT) {
            newRow++;
        } else if(direction == Direction.DOWNLEFT || direction == Direction.DOWNRIGHT) {
            newRow--;
        }
        return new TilePosition(row, column);
    }
}
