package engine;

import engine.enums.CardColor;
import engine.enums.GameMode;

public class Hand {

    private Card[] cards;
    private Trump trump;

    private Trump sorting;

    /* CONSTRUCTOR */

    public Hand() {

        cards = new Card[12];

        sorting = new Trump(GameMode.GRAND);
    }

    /* GETTER */

    public int getSize() {

        for ( var i = 0; i < cards.length; i++ ) {

            if ( cards[i] == null ) {

                return i;
            }
        }
        return cards.length;
    }

    public Card getCardAt(int index) {

        return cards[index];
    }

    // TODO: getMaxGameValue of hand
    public int getMaxGameValue() {

        return 0;
    }

    /* SETTER */

    public void setTrump(Trump trump) {

        this.trump = trump;
    }

    /* ELSE */

    public void addCard(Card card) {

        for (var i = 0; i < cards.length; i++) {

            if ( cards[i] == null ) {

                cards[i] = card;
                return;
            }
        }

        sort(sorting);
    }

    // evtl unnötig wenn nicht onHand verschiebungen
    public void addCardAt(int index, Card card) {

        var firstEmpty = index + 1;
        while ( cards[firstEmpty] != null ) {

            firstEmpty++;
        }

        for (var i = firstEmpty; i > index; i--) {

            var help = cards[i];
            cards[i] = cards[i - 1];
            cards[i - 1] = help;
        }

        cards[index] = card;
    }

    public void removeCard(int index) {

        cards[index] = null;

        for (int i = index; i < cards.length; i++) {

            cards[i] = cards[i + 1];
        }

        sort(sorting);
    }

    public void sort(Trump trump) {

        var size = getSize();

        for ( var i = 0; i < cards.length; i++ ) {

            var maxCardIndex = i;

            for ( var j = i + 1; j < cards.length; j++ ) {

                if ( cards[j].getStrength(trump, null) > cards[maxCardIndex].getStrength(trump, null) ) {

                    maxCardIndex = j;
                }
            }
            swap(i, maxCardIndex);
        }
    }

    private void swap(int index1, int index2) {

        var help = cards[index1];
        cards[index1] = cards[index2];
        cards[index2] = help;
    }

    public boolean canFollowTrump() {

        for ( Card card : cards ) {

            if ( card.isTrump(trump) ) {

                return true;
            }
        }
        return false;
    }

    public boolean canFollowSuit(CardColor color) {

        for ( Card card : cards ) {

            if ( card.getCardColor() == color && !card.isTrump(trump) ) {

                return true;
            }
        }
        return false;
    }
}
