package developmentCards;

import org.json.simple.JSONObject;

import materials.MaterialSet;
import materials.MaterialType;

import org.json.simple.JSONObject;

public class CardSet {
    JSONObject resources;
    boolean tradeImpossible;

    public CardSet() {
        resources = new JSONObject();
        for (CardType type : CardType.values()) {
            resources.put(type.toString(), 0);
        }
    }

    public boolean isSubset(CardSet superset) {
        for(CardType type : CardType.values()) {
            if(getAmount(type) > superset.getAmount(type)) {
                return false;
            }
        }
        return true;
    }

    public boolean isSuperset(CardSet subset) {
        for(CardType type : CardType.values()) {
            if(getAmount(type) < subset.getAmount(type)) {
                return false;
            }
        }
        return true;
    }

    public boolean removeResourceSet(CardSet set){
        if(!isSuperset(set))  {
            return false;
        }
        for(CardType type : CardType.values()) {
            removeResources(type, set.getAmount(type));
        }
        return true;
    }

    public boolean removeResources(CardType type, int amount) {
        if(getAmount(type) >= amount) {
            addResources(type, amount * -1);
            return true;
        } else {
            return false;
        }
    }

    public void addResources(CardType type, int amount) {
        resources.put(type.toString(), getAmount(type) + amount);
    }

    public int getAmount(CardType type) {
        return (int) resources.get(type.toString());
    }

    public String toString() {
        String output = "";
        for(CardType type : CardType.values()) {
            output += type.toString() + ": " + String.valueOf(getAmount(type)) + ", ";
        }
        return output;
    }

    public static CardSet getFullHand() {
        CardSet cardSet = new CardSet();
        for(CardType type : CardType.values()) {
            cardSet.addResources(type, 0);
        }
        return cardSet;
    }
    
    public static MaterialSet getCost() {
        MaterialSet materials = new MaterialSet();
        
        materials.addResources(MaterialType.ORE, 1);
        materials.addResources(MaterialType.WHEAT, 1);
        materials.addResources(MaterialType.WOOL, 1);
        
        return materials;
    }

}