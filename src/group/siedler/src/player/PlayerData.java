package player;

import materials.MaterialSet;

public class PlayerData {
    protected MaterialSet hand;
    protected PlayerColor color;
    int numberOfVillages = 0;
    int numberOfTowns = 0;

    public PlayerData(PlayerColor color) {
        this.color = color;
        this.hand = new MaterialSet();
    }


    public void setHand(MaterialSet hand) {
        this.hand = hand;
    }

    public MaterialSet getHand() {
        return hand;
    }

    public int getNumberOfVillages() {
    	return numberOfVillages;
    }

    // Redundant : Can be read of the map
    public void increaseNumberOfVillages() {
    	this.numberOfVillages++; 
    }
    
    public void decreaseNumberOfVillages() {
    	this.numberOfVillages--; 
    }
    
    public void increaseNumberOfTowns() {
    	this.numberOfTowns++; 
    }

    public int getNumberOfTowns() {
    	return numberOfTowns;
    }

    public PlayerColor getColor() {
        return color;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }
}
