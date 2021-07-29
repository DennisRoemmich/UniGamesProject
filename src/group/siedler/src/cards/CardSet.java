package cards;

import org.json.simple.JSONObject;
import materials.MaterialSet;
import materials.MaterialType;

/**
 * Represents a set of development cards.
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public class CardSet {
    private JSONObject mCards;

	public CardSet() {
        mCards = new JSONObject();
        for (CardType type : CardType.values()) {
            mCards.put(type.toString(), 0);
        }
    }

    public boolean removeCard(CardType type) {
        if (getAmount(type) > 0) {
            mCards.put(type.toString(), getAmount(type) - 1);
            return true;
        } else {
            return false;
        }
    }

	public void addCard(CardType type) {
        mCards.put(type.toString(), getAmount(type) + 1);
    }

    public int getAmount() {
        int amount = 0;
        for (CardType cardType : CardType.values()) {
            amount += getAmount(cardType);
        }
        return amount;
    }

    public int getAmount(CardType type) {
        return (int) mCards.get(type.toString());
    }
    
    @Override
    public String toString() {
    	StringBuilder output = new StringBuilder();
        for (CardType type : CardType.values()) {
            output.append(type.toString() + ": " + output.append(String.valueOf(getAmount(type)) + ", "));
        }
        return output.toString();
    }
    
    public static MaterialSet getCost() {
        MaterialSet materials = new MaterialSet();
        materials.addResources(MaterialType.ORE, 1);
        materials.addResources(MaterialType.WHEAT, 1);
        materials.addResources(MaterialType.WOOL, 1);
        return materials;
    }
}
