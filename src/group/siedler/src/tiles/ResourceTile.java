package tiles;

import positions.TilePosition;
import materials.MaterialType;

public class ResourceTile extends Tile {
    private MaterialType resourceType;
    private int hitnumber;

    protected ResourceTile(TilePosition position) {
        super(position);
    }

    public ResourceTile(TilePosition position, MaterialType type, int hitnumber) {
        super(position);
        this.resourceType = type;
        this.hitnumber = hitnumber;
    }

    public MaterialType getResourceType() {
        return resourceType;
    }

    @Override
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
        return resourceType + " " + hitnumber + " @ " + position;
    }
}
