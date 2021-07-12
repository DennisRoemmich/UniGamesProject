package core.positions;

import java.util.Objects;

/*
A basic structure with two integer ans one boolean coordinate (x, y, z)
No further Functionality, only generated methods.
 */
public class EdgePosition {
    private int x;
    private int y;
    private EdgePositionZCord z;

    public EdgePosition(int x, int y, EdgePositionZCord z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public EdgePositionZCord getZ() {
        return z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EdgePosition that = (EdgePosition) o;
        return getX() == that.getX() && getY() == that.getY() && getZ() == that.getZ();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), getZ());
    }

    @Override
    public String toString() {
        return "(" + x + "|" + y + "|" + z + ")";
    }
}
