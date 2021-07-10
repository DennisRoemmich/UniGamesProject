package player;

import rohstoffe.ResourceSet;

public abstract class SiedlerPlayer {
    protected ResourceSet hand;

    public SiedlerPlayer() {
        this.hand = new ResourceSet();
    }

    public void setHand(ResourceSet hand) {
        this.hand = hand;
    }
}
