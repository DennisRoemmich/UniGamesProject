package player;

import materials.MaterialSet;

public class PlayerData {
    protected MaterialSet hand;
    protected PlayerColor color;

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

    public PlayerColor getColor() {
        return color;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }
}
