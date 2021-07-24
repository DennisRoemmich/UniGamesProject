package player;

import java.util.ArrayList;

import java.util.List;
import buildings.Building;
import developmentCards.CardSet;
import materials.MaterialSet;

public class PlayerData {
    protected MaterialSet hand;
    protected PlayerColor color;
    protected int winPoints;
    protected CardSet cards;

    public PlayerData(PlayerColor color) {
        this.color = color;
        this.hand = new MaterialSet();
        this.winPoints = 2;
        this.cards = new CardSet();
    }

    public void setHand(MaterialSet hand) {
        this.hand = hand;
    }

    public MaterialSet getHand() {
        return hand;
    }

    public PlayerColor getColor() {
        return color;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }
    
    public void setWinPoints(int winPoints) {
    	this.winPoints = winPoints;
    }
    
    public void increaseWinPoints() {
    	this.winPoints++;
    }
    
    public int getWinPoints() {
    	return winPoints;
    }
    
    public void addCard(CardSet cards) {
    	this.cards = cards;
    }
    
    public CardSet getCards() {
    	return this.cards;
    }
}
