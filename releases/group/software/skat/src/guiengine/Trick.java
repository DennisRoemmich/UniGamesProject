package guiengine;

import guiengine.enums.CardColor;

/**
 * class for trick
 */
public class Trick {

    private Card[] mCards;
    private Trump mTrump;
    private CardColor mColor;

    /* CONSTRUCTOR */

    /**
     * can be initialised with a trump
     * @param trump trump
     */
    public Trick(Trump trump) {

        this.mTrump = trump;

        mCards = new Card[3];
    }

    /**
     * can be initialised with the first card played and trump
     * @param trump trump
     * @param card first card played on trick
     */
    public Trick(Trump trump, Card card) {

        this.mTrump = trump;
        mColor = card.getCardColor();

        mCards = new Card[3];
        mCards[0] = card;
    }

    /* GETTER */

    /**
     * @param index index of card
     * @return card at index
     */
    public Card getCardAt(int index) {

        return mCards[index];
    }

    /**
     * @return size of trick
     */
    public int getSize() {

        for (var i = 0; i < mCards.length; i++ ) {

            if ( mCards[i] == null ) {

                return i;
            }
        }
        return mCards.length;
    }

    /**
     * @return color of first card played
     */
    public CardColor getColor() {

        return mColor;
    }

    /**
     * @return value of all cards played on trick
     */
    public int getValue() {

        var sum = 0;

        for (Card card : mCards) {

            sum += card.getPoints();
        }

        return sum;
    }

    /**
     * @return index of winner card on trick
     */
    public int getWinnerIndex() {

        var pole = 0;

        for (var i = 1; i < mCards.length; i++) {

            if (isStrongerCard(mCards[pole], mCards[i])) {

                pole = i;
            }
        }

        return pole;
    }

    /* OTHER */

    /**
     * adds card to be added to trick
     * @param card card to be added
     */
    public void addCard(Card card) {

        if (getSize() == 0) {

            mColor = card.getCardColor();
        }

        for (var i = 0; i < mCards.length; i++) {

            if ( mCards[i] == null ) {

                mCards[i] = card;
                break;
            }
        }
    }

    /**
     * needed to calculate the winner of the trick
     * @param card1, card 2
     * @return stronger of these two cards
     */
    public boolean isStrongerCard(Card card1, Card card2) {

        return card1.getStrength(mTrump, mColor) < card2.getStrength(mTrump, mColor);
    }
}
