package positions;

import java.util.Objects;

/*
A basic structure with two integer coordinates (x, y)
No further Functionality, only generated methods.
 */
/**
 * Represents the position of a tile.
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public class TilePosition {
    private int mValueX;
    private int mValueY;

    public TilePosition(int x, int y) {
        this.mValueX = x;
        this.mValueY = y;
    }

    public int getX() {
        return mValueX;
    }

    public int getY() {
        return mValueY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
        	return true;
        }
        if (o == null || getClass() != o.getClass()) {
        	return false;
        }
        TilePosition that = (TilePosition) o;
        return getX() == that.getX() && getY() == that.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    @Override
    public String toString() {
        return "(" + mValueX + "|" + mValueY + ")";
    }
}
