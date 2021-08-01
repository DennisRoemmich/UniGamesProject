package guiengine;

import guiconsole.Print;
import guiengine.enums.CardColor;
import guiengine.enums.CardValue;
import guiengine.enums.GameMode;

/**
 * class for the hands of the players
 */
public class Hand {

    private Card[] mCards;
    private Trump mTrump;

    private int mTrumpLine;

    /* CONSTRUCTOR */

    public Hand(Trump trump) {

        mCards = new Card[12];

        this.mTrump = trump;

        mTrumpLine = -1;
    }

    /* GETTER */

    /**
     * @return number of cards on the hand
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
     * @return true if hand is empty, false if not
     */
    public boolean isEmpty() {

        return getSize() == 0;

    }

    /**
     * @param index of card
     * @return card at given index
     */
    public Card getCardAt(int index) {

        return mCards[index];
    }

    /**
     * @return array of cards on the hand
     */
    public Card[] getCardsArray() {

        return mCards;
    }

    /**
     * @return skat if available - if not an empty array
     */
    public Card[] getSkat() {

        if(getSize() != 12) {
            Print.debug("WARNING", "getSkat() was called on a player that doesn't have the skat on his hand.");
            return null;
        }

        return new Card[]{mCards[10], mCards[11]};
    }

    /**
     * @return number of trumps in a row to calculate the value of a game, if this player is declarer
     */
    public int getTrumpLine() {

        return mTrumpLine;
    }

    /* SETTER */

    /* ELSE */

    public void addCard(Card card) {

        for (var i = 0; i < mCards.length; i++) {

            if ( mCards[i] == null ) {

                mCards[i] = card;
                return;
            }
        }
    }

    public void addCardAt(int index, Card card) {

        var firstEmpty = index + 1;
        while ( mCards[firstEmpty] != null ) {

            firstEmpty++;
        }

        for (var i = firstEmpty; i > index; i--) {

            var help = mCards[i];
            mCards[i] = mCards[i - 1];
            mCards[i - 1] = help;
        }

        mCards[index] = card;
    }

    public void removeCard(int index) {

        mCards[index] = null;

        if (index < mCards.length - 3) {

            for (var i = index; i <= mCards.length - 3; i++) {

                swap(i, i + 1);
            }
            mCards[mCards.length - 2] = null;
        }
    }

    /**
     * sorts the hand for a given trump
     * @param trump trump
     */
    public void sort(Trump trump) {

        for (var i = 0; i < mCards.length - 2; i++) {

            var maxCardIndex = i;

            for (var j = i + 1; j < mCards.length - 2; j++) {

                if (mCards[j] != null && mCards[maxCardIndex] != null
                        && mCards[j].getStrength(trump, null) > mCards[maxCardIndex].getStrength(trump, null)) {

                    maxCardIndex = j;
                }
            }
            swap(i, maxCardIndex);
        }
    }

    private void swap(int index1, int index2) {

        var help = mCards[index1];
        mCards[index1] = mCards[index2];
        mCards[index2] = help;
    }

    /**
     * calculates the trumps in a row
     */
    public void setTrumpLine() {

        if (mTrump.getGameMode() == GameMode.NULL) {

            mTrumpLine = 0;
            return;
        }

        var trumps = 0;
        var jacks = 0;


        var clubsJack = false;
        var spadesJack = false;
        var heartsJack = false;
        var diamondsJack = false;

        for (Card card : mCards) {

            if (card.getCardValue() == CardValue.JACK) {

                var cardColor = card.getCardColor();

                if (cardColor == CardColor.CLUBS) {

                    clubsJack = true;

                } else if (cardColor == CardColor.SPADES) {

                    spadesJack = true;

                } else if (cardColor == CardColor.HEARTS) {

                    heartsJack = true;

                } else if (cardColor == CardColor.DIAMONDS) {

                    diamondsJack = true;
                }
            }
        }

        jacks = setJacks(clubsJack, spadesJack, heartsJack, diamondsJack);

        if (mTrump.getGameMode() == GameMode.SUIT && jacks == 4) {

            trumps = setFurtherTrumps(diamondsJack);
        }

        mTrumpLine = jacks + trumps;
    }

    /**
     * help for trumpline calculation
     * @return number of jacks in a row
     */
    private int setJacks(boolean clubsJack, boolean spadesJack, boolean heartsJack, boolean diamondsJack) {

        var jacks = 1;

        if ( clubsJack == spadesJack ) {

            if ( spadesJack == heartsJack ) {

                if (heartsJack == diamondsJack ) {

                    jacks = 4;

                } else {

                    jacks = 3;
                }
            } else {

                jacks = 2;
            }
        }
        return jacks;
    }

    /**
     * help for trumpline calculation
     * @param diamondsJack true if diamondsjack is present, false if not
     * @return number of trumps in a row
     */
    private int setFurtherTrumps(boolean diamondsJack) {

        var trumpAce = false;
        var trumpTen = false;
        var trumpKing = false;
        var trumpQueen = false;
        var trumpNine = false;
        var trumpEight = false;
        var trumpSeven = false;

        for (Card card : mCards) {

            if (card.isTrump(mTrump) && card.getCardValue() != CardValue.JACK) {

                if (card.getCardValue() == CardValue.ACE) {

                    trumpAce = true;

                } else if (card.getCardValue() == CardValue.TEN) {

                    trumpTen = true;

                } else if (card.getCardValue() == CardValue.KING) {

                    trumpKing = true;

                } else if (card.getCardValue() == CardValue.QUEEN) {

                    trumpQueen = true;

                } else if (card.getCardValue() == CardValue.NINE) {

                    trumpNine = true;

                } else if (card.getCardValue() == CardValue.EIGHT) {

                    trumpEight = true;

                } else if (card.getCardValue() == CardValue.SEVEN) {

                    trumpSeven = true;
                }
            }
        }

        var trumps = setFurtherTrumpsUno(diamondsJack, trumpAce, trumpTen, trumpKing);

        if (trumps == 3) {

            trumps += setFurtherTrumpsDos(trumpKing, trumpQueen, trumpNine, trumpEight, trumpSeven);
        }

        return trumps;
    }

    /**
     * help for trumpline calculation
     * @return number of trumps in a row
     */
    private int setFurtherTrumpsUno(boolean diamondsJack, boolean trumpAce, boolean trumpTen, boolean trumpKing) {

        var trumps = 0;

        if (diamondsJack == trumpAce) {

            if (trumpAce == trumpTen) {

                if (trumpTen == trumpKing) {

                    trumps = 3;

                } else {

                    trumps = 2;
                }
            } else {

                trumps = 1;
            }
        }

        return trumps;
    }

    /**
     * help for trumpline calculation
     * @return number of trumps in a row
     */
    private int setFurtherTrumpsDos(boolean trumpKing, boolean trumpQueen, boolean trumpNine, boolean trumpEight, boolean trumpSeven) {

        var trumps = 0;

        if (trumpKing == trumpQueen) {

            if (trumpQueen == trumpNine) {

                if (trumpNine == trumpEight) {

                    if (trumpEight == trumpSeven) {

                        trumps = 4;

                    } else {
                        trumps = 3;
                    }
                } else {
                    trumps = 2;
                }
            } else {
                trumps = 1;
            }
        }
        return trumps;
    }

    /**
     * @param indexFrom index of card to move
     * @param indexTo index of target
     * @return true if move is possible, false if not
     */
    public boolean moveCardIsValid(int indexFrom, int indexTo) {

        return mCards[indexFrom] != null && mCards[indexTo] != null && indexFrom != indexTo;
    }

    /**
     * switches a card on the hand
     * @param indexFrom index of card to move
     * @param indexTo index of target
     */
    public void moveCardOnHand(int indexFrom, int indexTo) {

        var indexTarget = indexTo;
        if (indexTo >= getSize()) {

            indexTarget = getSize() - 1;
        }

        if (indexFrom < indexTarget) {

            for (var i = indexFrom; i < indexTarget; i++) {

                swap(i, i + 1);
            }

        } else {

            for (var i = indexFrom; i > indexTarget; i--) {

                swap(i, i - 1);
            }
        }
    }

    /**
     * skat is available and card is switched on hand
     * @param indexFrom index of card to move
     * @param indexTo index of target
     */
    public void moveCardOnSkatHand(int indexFrom, int indexTo) {

        if (indexFrom < 10 && indexTo < 10) {

            moveCardOnHand(indexFrom, indexTo);

        } else {

            swap(indexFrom, indexTo);
        }
    }

    /**
     * @return true if player can play a trump-card, false if not
     */
    public boolean canFollowTrump() {

        for (var i = 0; i < getSize(); i++) {

            if (mCards[i].isTrump(mTrump)) {

                return true;
            }
        }
        return false;
    }

    /**
     * @param color of current trick
     * @return true if player can play this color, false if not
     */
    public boolean canFollowSuit(CardColor color) {

        for (var i = 0; i < getSize(); i++) {

            if (mCards[i].getCardColor() == color && !mCards[i].isTrump(mTrump)) {

                return true;
            }
        }
        return false;
    }
}
