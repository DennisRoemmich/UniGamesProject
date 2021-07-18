package positions;

public enum NodePositionZCord {
    A(true), B(false);

    private final boolean value;

    NodePositionZCord(boolean state) {
        this.value = state;
    }

    public boolean getValue() {
        return value;
    }

    public static NodePositionZCord valueOf(boolean state) {
        return state ? A : B;
    }
}
