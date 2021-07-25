package tiles;

public class Tile {
    protected boolean mIsWater = false;
    protected boolean mHasHitnumber = false;

    public boolean isWater() {
        return mIsWater;
    }

    public void setWater(boolean water) {
        mIsWater = water;
    }

    public boolean isHasHitnumber() {
        return mHasHitnumber;
    }

    public void setHasHitnumber(boolean hasHitnumber) {
        this.mHasHitnumber = hasHitnumber;
    }
}
