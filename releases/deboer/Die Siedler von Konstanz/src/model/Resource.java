package model;

public enum Resource {
    WOOD(0), CLAY(1), STONE(2), WOOL(3), WHEAT(4);

    public final int value;

    Resource(int value) {
        this.value = value;
    }

    public int toInt() {
        return value;
    }
}
