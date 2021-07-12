package materials;

import org.json.simple.JSONObject;

public class ResourceSet {
    JSONObject resources;

    public ResourceSet() {
        resources = new JSONObject();
        for(ResourceType type : ResourceType.values()) {
            resources.put(type.toString(), 0);
        }
    }

    public boolean isSubset(ResourceSet superset) {
        for(ResourceType type : ResourceType.values()) {
            if(getAmount(type) > superset.getAmount(type)) {
                return false;
            }
        }
        return true;
    }

    public boolean isSuperset(ResourceSet subset) {
        for(ResourceType type : ResourceType.values()) {
            if(getAmount(type) < subset.getAmount(type)) {
                return false;
            }
        }
        return true;
    }

    public boolean removeResourceSet(ResourceSet set){
        if(!isSuperset(set))  {
            return false;
        }
        for(ResourceType type : ResourceType.values()) {
            removeResources(type, set.getAmount(type));
        }
        return true;
    }

    public boolean removeResources(ResourceType type, int amount) {
        if(getAmount(type) >= amount) {
            addResources(type, amount * -1);
            return true;
        } else {
            return false;
        }
    }

    public void addResources(ResourceType type, int amount) {
        resources.put(type.toString(), getAmount(type) + amount);
    }

    public int getAmount(ResourceType type) {
        return (int) resources.get(type.toString());
    }
}
