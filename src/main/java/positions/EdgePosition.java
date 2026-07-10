package positions;

import java.util.Objects;

/*
A basic structure with two integer and one boolean coordinate (x, y, z)
No further Functionality, only generated methods.
 */
/**
 * Represents Edge positions on the board.
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public class EdgePosition {
    private int mValueX;
    private int mValueY;
    private EdgePositionZCord mValueZ;

    public EdgePosition(int x, int y, EdgePositionZCord z) {
        this.mValueX = x;
        this.mValueY = y;
        this.mValueZ = z;
    }

    public int getX() {
        return mValueX;
    }

    public int getY() {
        return mValueY;
    }

    public EdgePositionZCord getZ() {
        return mValueZ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
        	return true;
        }
        if (o == null || getClass() != o.getClass()) {
        	return false;
        }
        EdgePosition that = (EdgePosition) o;
        return getX() == that.getX() && getY() == that.getY() && getZ() == that.getZ();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), getZ());
    }

    @Override
    public String toString() {
        return "(" + mValueX + "|" + mValueY + "|" + mValueZ + ")";
    }
}
