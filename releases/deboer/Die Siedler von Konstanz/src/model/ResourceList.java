package model;

public class ResourceList {
    private int[] amounts = new int[5];

    public ResourceList() {
        for (Resource resource: Resource.values()) {
            amounts[resource.toInt()] = 0;
        }
    }

    public ResourceList(int wood, int clay, int stone, int wool, int wheat) {
        amounts[Resource.WOOD.value] = wood;
        amounts[Resource.CLAY.value] = clay;
        amounts[Resource.STONE.value] = stone;
        amounts[Resource.WOOL.value] = wool;
        amounts[Resource.WHEAT.value] = wheat;
    }

    public int getAmount(Resource resource) {
        return amounts[resource.toInt()];
    }

    public boolean isCovering(ResourceList list) {
        for (Resource resource: Resource.values()) {
            if (this.getAmount(resource) < list.getAmount(resource)){
                return false;
            }
        }
        return true;
    }

}
