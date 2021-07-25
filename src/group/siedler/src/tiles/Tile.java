package tiles;

import positions.TilePosition;

public class Tile {
    protected boolean isWater = false;
    protected boolean hasHitnumber = false;

    public boolean isWater() {
        return isWater;
    }

    public void setWater(boolean water) {
        isWater = water;
    }

    public boolean isHasHitnumber() {
        return hasHitnumber;
    }

    public void setHasHitnumber(boolean hasHitnumber) {
        this.hasHitnumber = hasHitnumber;
    }
}
