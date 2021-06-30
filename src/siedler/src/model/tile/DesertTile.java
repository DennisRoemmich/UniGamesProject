package model.tile;

public class DesertTile extends Tile {
    @Override
    public String getTypeID() {
        return "Desert";
    }

    @Override
    public boolean hasRollNumber() {
        return false;
    }
}
