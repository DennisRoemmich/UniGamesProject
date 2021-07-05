package engine;

public class SkatPlayer {

    private Hand hand;
    private Tricks tricks;

    private boolean isBidding;
    private boolean isAsking;
    private boolean isDeclarer;

    private int finalScore;

    /* CONSTRUCTOR */

    public SkatPlayer(Trump trump) {

        hand = new Hand(trump);
        tricks = new Tricks();

        isBidding = true;
        isDeclarer = false;
    }

    /* GETTER */

    public Hand getHand() {

        return hand;
    }

    public Tricks getTricks() {

        return tricks;
    }

    public boolean isBidding() {

        return isBidding;
    }

    public boolean isAsking() {

        return isAsking;
    }

    public boolean isDeclarer() {

        return isDeclarer;
    }

    public int getFinalScore() {

        return finalScore;
    }

    /* SETTER */

    public void setBidding(boolean b) {

        isBidding = b;
    }

    public void setAsking(boolean b) {

        isAsking = b;
    }

    public void setDeclarer(boolean b) {

        isDeclarer = b;
    }

    public void setTricks(Tricks tricks) {

        this.tricks = tricks;
    }

    /* ELSE */

    public void calculateFinalScore() {

        finalScore = tricks.getValue();
    }
}
