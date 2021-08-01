package positions;

/**
 * Represents the z axis of the NodePosition
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public enum NodePositionZCord {
    A(true), B(false);

    private boolean mValue;

    NodePositionZCord(boolean state) {
        this.mValue = state;
    }

    public boolean getValue() {
        return mValue;
    }

    public static NodePositionZCord valueOf(boolean state) {
        return state ? A : B;
    }
}
