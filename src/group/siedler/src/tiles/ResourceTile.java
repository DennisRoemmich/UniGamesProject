package tiles;

import positions.TilePosition;
import materials.MaterialType;

public class ResourceTile extends Tile {
    private MaterialType resourceType;
    private int hitnumber;

    public ResourceTile(MaterialType type, int hitnumber) {
        this.hasHitnumber = true;
        this.resourceType = type;
        this.hitnumber = hitnumber;
    }

    public MaterialType getResourceType() {
        return resourceType;
    }

    public int getHitnumber() {
        return hitnumber;
    }

    public void setResourceType(MaterialType resourceType) {
        this.resourceType = resourceType;
    }

    public void setHitnumber(int hitnumber) {
        this.hitnumber = hitnumber;
    }

    @Override
    public String toString() {
        return resourceType + " " + hitnumber;
    }
}
