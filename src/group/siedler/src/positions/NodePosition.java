package positions;

import java.util.Objects;

/*
A basic structure with two integer ans one boolean coordinate (x, y, z)
No further Functionality, only generated methods.
 */
public class NodePosition {
    private int x;
    private int y;
    private NodePositionZCord z;
    // z == true  -> A
    // z == false -> B

    public NodePosition(int x, int y, boolean z) {
        this.x = x;
        this.y = y;
        this.z = NodePositionZCord.valueOf(z);
    }

    public NodePosition(int x, int y, NodePositionZCord z) {
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

    public boolean isZ() {
        return z.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodePosition that = (NodePosition) o;
        return getX() == that.getX() && getY() == that.getY() && isZ() == that.isZ();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), isZ());
    }

    @Override
    public String toString() {
        return "(" + x + "|" + y + "|" + z + ")";
    }
}
