package map;

import java.util.Objects;

/*
A basic structure with two integer ans one boolean coordinate (x, y, z)
No further Functionality, only generated methods.
 */
public class EdgePosition {
    public int x;
    public int y;
    public EdgePositionZCord z;

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

    public EdgePositionZCord isZ() {
        return z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EdgePosition that = (EdgePosition) o;
        return getX() == that.getX() && getY() == that.getY() && isZ() == that.isZ();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), isZ());
    }
}
