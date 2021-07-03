package engine;

import engine.enums.CardColor;

public class Trick {

    private Card[] cards;
    private Trump trump;
    private CardColor color;

    /* CONSTRUCTOR */

    public Trick(Trump trump, Card card) {

        this.trump = trump;
        color = card.getCardColor();

        cards = new Card[3];
        cards[0] = card;
    }

    /* GETTER */

    public Card getCardAt(int index) {

        return cards[index];
    }

    public int getSize() {

        for ( var i = 0; i < cards.length; i++ ) {

            if ( cards[i] == null ) {

                return i;
            }
        }
        return cards.length;
    }

    public CardColor getColor() {

        return color;
    }

    public int getValue() {

        var sum = 0;

        for ( Card card : cards ) {

            sum += card.getPoints();
        }

        return sum;
    }

    public int getWinnerIndex() {

        var pole = 0;

        for ( var i = 1; i < cards.length; i++ ) {

            if ( isStrongerCard(cards[pole], cards[i]) ) {

                pole = i;
            }
        }

        return pole;
    }

    /* ELSE */

    public void addCard(Card card) {

        for ( var i = 0; i < cards.length; i++ ) {

            if ( cards[i] == null ) {

                cards[i] = card;
                return;
            }
        }
    }

    private boolean isStrongerCard(Card card1, Card card2) {

        return card1.getStrength(trump, color) < card2.getStrength(trump, color);
    }
}
