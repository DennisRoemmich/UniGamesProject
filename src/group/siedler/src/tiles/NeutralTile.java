package tiles;

/**
 * Represents the neutral tiles (Water tiles, Desert tiles)
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
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
