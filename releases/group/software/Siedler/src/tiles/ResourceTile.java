package tiles;

import materials.MaterialType;

/**
 * Represents the resource tiles.
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public class ResourceTile extends Tile {
    private MaterialType mResourceType;
    private int mHitnumber;

    public ResourceTile(MaterialType type, int hitnumber) {
        this.mHasHitnumber = true;
        this.mResourceType = type;
        this.mHitnumber = hitnumber;
    }

    public MaterialType getResourceType() {
        return mResourceType;
    }

    public int getHitnumber() {
        return mHitnumber;
    }

    public void setResourceType(MaterialType resourceType) {
        this.mResourceType = resourceType;
    }

    public void setHitnumber(int hitnumber) {
        this.mHitnumber = hitnumber;
    }

    @Override
    public String toString() {
        return mResourceType + " " + mHitnumber;
    }
}
