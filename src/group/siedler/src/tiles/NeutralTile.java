package tiles;

public class NeutralTile extends Tile {

    public NeutralTile(boolean isWater) {
        this.mIsWater = isWater;
    }
    
    @Override
    public boolean isWater() {
        return mIsWater;
    }

    @Override
    public String toString() {
        return (mIsWater ? "WATER" : "DESERT") + "TILE";
    }
}
