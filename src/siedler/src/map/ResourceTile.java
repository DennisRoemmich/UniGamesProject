package map;

import positions.TilePosition;
import rohstoffe.ResourceType;

public class ResourceTile extends Tile {
    private ResourceType resourceType;
    private int hitnumber;

    protected ResourceTile(TilePosition position) {
        super(position);
    }

    public ResourceTile(TilePosition position, ResourceType type, int hitnumber) {
        super(position);
        this.resourceType = type;
        this.hitnumber = hitnumber;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    @Override
    public int getHitnumber() {
        return hitnumber;
    }

    public void setResourceType(ResourceType resourceType) {
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
