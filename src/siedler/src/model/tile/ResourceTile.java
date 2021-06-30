package model.tile;

import model.ResourceType;
import model.tile.Tile;

public class ResourceTile extends Tile {
    private ResourceType resourceType;
    private int rollNumber;

    public ResourceType getResourceType() {
        return resourceType;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    @Override
    public String getTypeID() {
        return resourceType.toString() + " TILE";
    }

    @Override
    public boolean hasRollNumber() {
        return true;
    }
}
