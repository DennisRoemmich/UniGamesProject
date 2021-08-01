package player;

import cards.CardSet;
import materials.MaterialSet;

/**
 * All the important game information about each player.
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public class PlayerData {
    protected MaterialSet mHand;
    protected PlayerColor mColor;
    protected int mWinPoints;
    protected CardSet mCards;

	public PlayerData(PlayerColor color) {
        this.mColor = color;
        this.mHand = new MaterialSet();
        this.mWinPoints = 2;
        this.mCards = new CardSet();
    }

    public void setHand(MaterialSet hand) {
        this.mHand = hand;
    }

    public MaterialSet getHand() {
        return mHand;
    }

    public PlayerColor getColor() {
        return this.mColor;
    }

    public void setColor(PlayerColor color) {
        this.mColor = color;
    }
    
    public void setWinPoints(int winPoints) {
    	this.mWinPoints = winPoints;
    }
    
    public void increaseWinPoints() {
    	this.mWinPoints++;
    }
    
    public int getWinPoints() {
    	return this.mWinPoints;
    }
    
    public void addCard(CardSet cards) {
    	this.mCards = cards;
    }
    
    public CardSet getCards() {
    	return this.mCards;
    }
}
