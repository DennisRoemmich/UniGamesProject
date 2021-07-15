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
    	return this.getNumberOfVillages();
    }
    
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
    	return this.getNumberOfTowns();
    }

    public PlayerColor getColor() {
        return color;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }
}
