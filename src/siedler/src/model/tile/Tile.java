package model.tile;

public abstract class Tile {
    public abstract String getTypeID();
    public abstract boolean hasRollNumber();
    public String toString() {
        return getTypeID();
    }
}
