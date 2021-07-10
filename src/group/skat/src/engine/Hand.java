package engine;

import console.Print;
import engine.enums.CardColor;
import engine.enums.CardValue;
import engine.enums.GameMode;

public class Hand {

    private Card[] cards;
    private Trump trump;

    private int trumpLine;

    /* CONSTRUCTOR */

    public Hand(Trump trump) {

        cards = new Card[12];

        this.trump = trump;

        trumpLine = -1;
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

    public boolean isEmpty(){

        return getSize() == 0;

    }

    public Card getCardAt(int index) {

        return cards[index];
    }

    public Card[] getCardsArray(){

        return cards;
    }

    public Card[] getSkat(){

        if(getSize() != 12){
            Print.debug("WARNING", "getSkat() was called on a player that doesn't have the skat on his hand.");
            return null;
        }

        return new Card[]{cards[10], cards[11]};
    }

    public int getTrumpLine() {

        return trumpLine;
    }

    /* SETTER */

    /* ELSE */

    public void addCard(Card card) {

        for (var i = 0; i < cards.length; i++) {

            if ( cards[i] == null ) {

                cards[i] = card;
                return;
            }
        }
    }

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
    }

    public void sort(Trump trump) {

        Print.debug("WARNING", "sort in Hand entered");

        Print.debug("WARNING", cards[0].getStrength(trump, null) + ", " +
                cards[1].getStrength(trump, null) + ", " +
                cards[2].getStrength(trump, null) + ", " +
                cards[3].getStrength(trump, null) + ", " +
                cards[4].getStrength(trump, null) + ", " +
                cards[5].getStrength(trump, null) + ", " +
                cards[6].getStrength(trump, null) + ", " +
                cards[7].getStrength(trump, null) + ", " +
                cards[8].getStrength(trump, null) + ", " +
                cards[9].getStrength(trump, null) + ", ");

        for (var i = 0; i < 10; i++) {

            var maxCardIndex = i;

            for (var j = i + 1; j < 10; j++) {

                Print.debug("WARNING", "second for-loop " + maxCardIndex + ", " + j);

                Print.debug("WARNING", cards[j].getStrength(trump, null) + " vs " + cards[maxCardIndex].getStrength(trump, null));

                if (cards[j] != null && cards[maxCardIndex] != null
                        && cards[j].getStrength(trump, null) > cards[maxCardIndex].getStrength(trump, null)) {

                    maxCardIndex = j;
                }
            }
            swap(i, maxCardIndex);
        }
    }

    private void swap(int index1, int index2) {

        Print.debug("WARNING", "index1: " + cards[index1].getColorValue() + " " + cards[index1].getPoints() + " index2: " + cards[index1].getColorValue() + " " + cards[index1].getPoints());

        var help = cards[index1];
        cards[index1] = cards[index2];
        cards[index2] = help;

        Print.debug("WARNING", "swaped: " + index1 + " and " + index2);
        Print.debug("WARNING", "index1: " + cards[index1].getColorValue() + " " + cards[index1].getPoints() + " index2: " + cards[index1].getColorValue() + " " + cards[index1].getPoints());
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

    public void setTrumpLine() {

        if (trump.getGameMode() == GameMode.NULL) {

            trumpLine = 0;
        }

        var trumps = 0;
        var jacks = 0;


        var clubsJack = false;
        var spadesJack = false;
        var heartsJack = false;
        var diamondsJack = false;

        for (Card card : cards) {

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

        if (trump.getGameMode() == GameMode.SUIT && jacks == 4) {

            trumps = setFurtherTrumps(diamondsJack);
        }

        trumpLine = jacks + trumps;
    }

    private int setJacks(boolean clubsJack, boolean spadesJack, boolean heartsJack, boolean diamondsJack) {

        var jacks = 0;

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
        } else {

            jacks = 1;
        }
        return jacks;
    }

    private int setFurtherTrumps(boolean diamondsJack) {

        var trumps = 0;

        var trumpAce = false;
        var trumpTen = false;
        var trumpKing = false;
        var trumpQueen = false;
        var trumpNine = false;
        var trumpEight = false;
        var trumpSeven = false;

        for (Card card : cards) {

            if (card.isTrump(trump) && card.getCardValue() != CardValue.JACK) {

                switch (card.getCardValue()) {

                    case ACE -> trumpAce = true;
                    case TEN -> trumpTen = true;
                    case KING -> trumpKing = true;
                    case QUEEN -> trumpQueen = true;
                    case NINE -> trumpNine = true;
                    case EIGHT -> trumpEight = true;
                    case SEVEN -> trumpSeven = true;
                    default -> Print.debug("ERROR", "jack in trumpCheck, somehow?!");
                }
            }
        }

        if (diamondsJack == trumpAce) {

            if (trumpAce == trumpTen) {

                if (trumpTen == trumpKing) {

                    if (trumpKing == trumpQueen) {

                        if (trumpQueen == trumpNine) {

                            if (trumpNine == trumpEight) {

                                if (trumpEight == trumpSeven) {

                                    trumps = 7;

                                } else {
                                    trumps = 6;
                                }
                            } else {
                                trumps = 5;
                            }
                        } else {
                            trumps = 4;
                        }
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

    public boolean moveCardIsValid(int indexFrom, int indexTo) {

        return indexFrom < 10 && indexTo < 10 && indexFrom != indexTo;
    }

    public boolean moveSkatCardIsValid(int indexFrom, int indexTo) {

        return indexFrom < 12 && indexTo < 12 && indexFrom != indexTo;
    }

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

    public void moveCardOnSkatHand(int indexFrom, int indexTo) {

        if (indexFrom < 10 && indexTo < 10) {

            moveCardOnHand(indexFrom, indexTo);

        } else if (indexFrom > 9 && indexTo > 9) {

            swap(10, 11);

        } else if (indexFrom > 9) {

            addCardAt(indexFrom, cards[indexFrom]);

            cards[indexFrom] = null;

        } else {

            addCardAt(indexTo, cards[indexFrom]);
            removeCard(indexFrom);
        }
    }
}
