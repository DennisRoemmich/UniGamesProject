package core.player;

import core.rohstoffe.ResourceSet;

public abstract class SiedlerPlayer {
    protected ResourceSet hand;

    public SiedlerPlayer() {
        this.hand = new ResourceSet();
    }

    public void setHand(ResourceSet hand) {
        this.hand = hand;
    }
}
