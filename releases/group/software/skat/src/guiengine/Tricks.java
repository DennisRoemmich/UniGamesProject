package guiengine;

import java.util.ArrayList;

/**
 * class for tricks
 */
public class Tricks {

    private ArrayList<Trick> mTricks = new ArrayList<>();
    private Card[] mSkat;

    /* CONSTRUCTOR */

    /**
     * initialised without skat
     */
    public Tricks() {

        mSkat = null;
    }

    /**
     * initialised with skat
     */
    public Tricks(Card[] skat) {

        this.mSkat = skat;
    }

    /* GETTER */

    /**
     * @return value of all tricks (and skat if declarer owns these tricks)
     */
    public int getValue() {

        var sum = 0;

        if ( mSkat != null ) {

            sum += mSkat[0].getPoints() + mSkat[1].getPoints();
        }

        for ( Trick trick : mTricks) {

            sum += trick.getValue();
        }

        return sum;
    }

    /**
     * @return amount of tricks
     */
    public int getSize() {

        return mTricks.size();
    }

    /**
     * @return true if declarer owns these tricks, false if not
     */
    public boolean skatIsDropped() {

        return mSkat != null;
    }

    /**
     * @return all cards in these tricks
     */
    public ArrayList<Card> getCards() {

        var cards = new ArrayList<Card>();

        for (Trick trick : mTricks) {

            for (var i = 0; i < trick.getSize(); i++) {

                cards.add(trick.getCardAt(i));
            }
        }

        return cards;
    }

    /* OTHER */

    /**
     * adds skat
     * @param skat skat
     */
    public void addSkat(Card[] skat) {

        this.mSkat = skat;
    }

    /**
     * adds a trick to the already won tricks
     * @param trick trick
     */
    public void addTrick(Trick trick) {

        mTricks.add(trick);
    }

    /**
     * @param index index of trick
     * @return trick at index
     */
    public Trick getTrickAt(int index) {

        return mTricks.get(index);
    }
}
