package materials;

import org.json.simple.JSONObject;

public class MaterialSet {
    JSONObject resources;

    public MaterialSet() {
        resources = new JSONObject();
        for (MaterialType type : MaterialType.values()) {
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

    public static MaterialSet getFullHand() {
        MaterialSet materialSet = new MaterialSet();
        for(MaterialType type : MaterialType.values()) {
            materialSet.addResources(type, 22);
        }
        return materialSet;
    }
    
    public MaterialSet tradeWithBank(MaterialSet materialSet, MaterialType purchase, MaterialType sale) { 
    	if(materialSet.getAmount(sale) >= 4) {
    		materialSet.addResources(purchase, 1);
    		materialSet.removeResources(sale, 4);
    	}
    	return materialSet;
    }
    
    public MaterialSet tradeWithPlayer(MaterialSet materialSet, MaterialType purchase, MaterialType sale, int purchased, int sold) {
    	materialSet.addResources(purchase, purchased);
    	materialSet.removeResources(sale, sold);
    	
    	return materialSet;
    }

}
