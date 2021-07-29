package materials;

import org.json.simple.JSONObject;

/**
 * Getter methods to access the tiles, nodes and edges on the map. 
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public class MaterialSet {
    JSONObject mResources;
    boolean mTradeImpossible;

    @SuppressWarnings("unchecked")
	public MaterialSet() {
        mResources = new JSONObject();
        for (MaterialType type : MaterialType.values()) {
            mResources.put(type.toString(), 0);
        }
    }

    public boolean isSubset(MaterialSet superset) {
        for (MaterialType type : MaterialType.values()) {
            if (getAmount(type) > superset.getAmount(type)) {
                return false;
            }
        }
        return true;
    }

    public boolean isSuperset(MaterialSet subset) {
        for (MaterialType type : MaterialType.values()) {
            if (getAmount(type) < subset.getAmount(type)) {
                return false;
            }
        }
        return true;
    }

    public boolean removeResourceSet(MaterialSet set) {
        if (!isSuperset(set))  {
            return false;
        }
        for (MaterialType type : MaterialType.values()) {
            removeResources(type, set.getAmount(type));
        }
        return true;
    }

    public boolean removeResources(MaterialType type, int amount) {
        if (getAmount(type) >= amount) {
            addResources(type, amount * -1);
            return true;
        } else {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
	public void addResources(MaterialType type, int amount) {
        mResources.put(type.toString(), getAmount(type) + amount);
    }

    public int getAmount(MaterialType type) {
        return (int) mResources.get(type.toString());
    }

    @Override
    public String toString() {
    	StringBuilder output = new StringBuilder();
        for (MaterialType type : MaterialType.values()) {
            output.append(type.toString() + ": " + output.append( String.valueOf(getAmount(type)) + ", "));
        }
        return output.toString();
    }

    public static MaterialSet getFullHand() {
        MaterialSet materialSet = new MaterialSet();
        for (MaterialType type : MaterialType.values()) {
            materialSet.addResources(type, 0);
        }
        return materialSet;
    }
    
    public MaterialSet tradeWithBank(MaterialSet materialSet, MaterialType purchase, MaterialType sale) { 
    	if (materialSet.getAmount(sale) >= 4) {
    		mTradeImpossible = false;
    		materialSet.addResources(purchase, 1);
    		materialSet.removeResources(sale, 4);
    		
    	} else {
    		mTradeImpossible = true;
    	}
    	return materialSet;
    }
    
    public boolean isTradeImpossible() {
		return mTradeImpossible;
	}
    
    public void setTradePossible() {
		mTradeImpossible = false;
	}
}
