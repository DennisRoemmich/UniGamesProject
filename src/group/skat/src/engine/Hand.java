package engine;

public class Hand {

    private Card[] cards;
    private Trump trump;

    /* CONSTRUCTOR */

    public Hand() {

        cards = new Card[12];
    }

    /* GETTER */

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

    // TODO: sort hand
    public void sort() {


    }
}
