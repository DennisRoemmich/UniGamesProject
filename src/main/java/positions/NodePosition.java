package positions;

import java.util.Objects;

/*
A basic structure with two integer ans one boolean coordinate (x, y, z)
No further Functionality, only generated methods.
 */
/**
 * Represents the node position.
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public class NodePosition {
    private int mValueX;
    private int mValueY;
    private NodePositionZCord mValueZ;
    // z == true  -> A
    // z == false -> B

    public NodePosition(int x, int y, boolean z) {
        this.mValueX = x;
        this.mValueY = y;
        this.mValueZ = NodePositionZCord.valueOf(z);
    }

    public NodePosition(int x, int y, NodePositionZCord z) {
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

    public boolean isZ() {
        return mValueZ.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
        	return true;
        }
        if (o == null || getClass() != o.getClass()) {
        	return false;
        }
        NodePosition that = (NodePosition) o;
        return getX() == that.getX() && getY() == that.getY() && isZ() == that.isZ();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), isZ());
    }

    @Override
    public String toString() {
        return "(" + mValueX + "|" + mValueY + "|" + mValueZ + ")";
    }
}
