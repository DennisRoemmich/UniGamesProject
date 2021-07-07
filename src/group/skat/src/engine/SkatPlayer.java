package engine;

public class SkatPlayer {

    private Hand hand;
    private Tricks tricks;

    private boolean isBidding;
    private boolean isAsking;
    private boolean isDeclarer;

    private int gameIndex;

    private int finalScore;

    /* CONSTRUCTOR */

    public SkatPlayer(Trump trump, int gameIndex) {

        this.gameIndex = gameIndex;

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

    public int getTricksScore() {

        return tricks.getValue();
    }

    public int getTricksAmount() {

        return tricks.getSize();
    }

    public int getFinalScore() {

        return finalScore;
    }

    public int getGameIndex() {

        return gameIndex;
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

    public void setFinalScore(int score) {

        finalScore = score;
    }

    /* OTHER */


}
