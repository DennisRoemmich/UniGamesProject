package materials;

import org.json.simple.JSONObject;

public class MaterialSet {
    JSONObject resources;

    public MaterialSet() {
        resources = new JSONObject();
        for(MaterialType type : MaterialType.values()) {
            resources.put(type.toString(), 0);
        }
    }

    public boolean isSubset(MaterialSet superset) {
        for(MaterialType type : MaterialType.values()) {
            if(getAmount(type) > superset.getAmount(type)) {
                return false;
            }
        }
        return true;
    }

    public boolean isSuperset(MaterialSet subset) {
        for(MaterialType type : MaterialType.values()) {
            if(getAmount(type) < subset.getAmount(type)) {
                return false;
            }
        }
        return true;
    }

    public boolean removeResourceSet(MaterialSet set){
        if(!isSuperset(set))  {
            return false;
        }
        for(MaterialType type : MaterialType.values()) {
            removeResources(type, set.getAmount(type));
        }
        return true;
    }

    public boolean removeResources(MaterialType type, int amount) {
        if(getAmount(type) >= amount) {
            addResources(type, amount * -1);
            return true;
        } else {
            return false;
        }
    }

    public void addResources(MaterialType type, int amount) {
        resources.put(type.toString(), getAmount(type) + amount);
    }

    public int getAmount(MaterialType type) {
        return (int) resources.get(type.toString());
    }

    public String toString() {
        String output = "";
        for(MaterialType type : MaterialType.values()) {
            output += type.toString() + ": " + String.valueOf(getAmount(type)) + ", ";
        }
        return output;
    }
}
