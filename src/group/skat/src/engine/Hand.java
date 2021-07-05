package engine;

import engine.enums.CardColor;
import engine.enums.CardValue;
import engine.enums.GameMode;

public class Hand {

    private Card[] cards;
    private Trump trump;

    private Trump sorting;

    /* CONSTRUCTOR */

    public Hand(Trump trump) {

        cards = new Card[12];

        this.trump = trump;

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
    public int getMaxSuitGameValue() {

        var clubsJack = false;
        var spadesJack = false;
        var heartsJack = false;
        var diamondsJack = false;

        for ( var i = 0; i < cards.length; i++ ) {

            if ( cards[i].getCardValue() == CardValue.JACK ) {

                switch ( cards[i].getCardColor() ) {

                    case CLUBS -> clubsJack = true;
                    case SPADES -> spadesJack = true;
                    case HEARTS -> heartsJack = true;
                    case DIAMONDS -> diamondsJack = true;
                }
            }
        }

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

        return (1 + jacks) * CardColor.CLUBS.getCardColorValue();
    }

    // only for endresult, funzt not yet - vlt starthand merken
    public int getResultValue(boolean declarerWon, boolean isSchneider, boolean isSchwarz) {

        if ( trump.getGameMode() == GameMode.NULL ) {

            return GameMode.NULL.getModeValue();
        }

        var trumpLine = getTrumpLine(trump);

        var win = -2;
        var schneider = 0;

        if ( declarerWon ) {

            win = 1;
        }
        if ( isSchneider ) {

            schneider = 1;
        }
        if ( isSchwarz ) {

            schneider = 2;
        }

        int gameValue;
        if ( trump.getGameMode() == GameMode.GRAND ) {

            gameValue = win * (1 + trumpLine + schneider) * GameMode.GRAND.getModeValue();

        } else {

            gameValue = win *  (1 + trumpLine + schneider) * trump.getColor().getCardColorValue();
        }

        return gameValue;
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

        for ( var i = 0; i < cards.length; i++ ) {

            var maxCardIndex = i;

            for ( var j = i + 1; j < cards.length; j++ ) {

                if ( cards[j] != null && cards[maxCardIndex] != null
                        && cards[j].getStrength(trump, null) > cards[maxCardIndex].getStrength(trump, null) ) {

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

    public int getTrumpLine(Trump trump) {

        var trumps = 0;
        var jacks = 0;


        var clubsJack = false;
        var spadesJack = false;
        var heartsJack = false;
        var diamondsJack = false;

        var trumpAce = false;
        var trumpTen = false;
        var trumpKing = false;
        var trumpQueen = false;
        var trumpNine = false;
        var trumpEight = false;
        var trumpSeven = false;

        for ( Card card : cards ) {

            if ( card.getCardValue() == CardValue.JACK ) {

                switch ( card.getCardColor() ) {

                    case CLUBS -> clubsJack = true;
                    case SPADES -> spadesJack = true;
                    case HEARTS -> heartsJack = true;
                    case DIAMONDS -> diamondsJack = true;
                }
            }
        }

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

    /*    if ( trump.getGameMode() == GameMode.SUIT && jacks == 4 ) {


        } else {

            return jacks;
        }*/

        return jacks;
    }
}
