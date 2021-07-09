package engine.enums;

import engine.Trump;

public enum CardValue {

    ACE(11),
    TEN(10),
    KING(4),
    QUEEN(3),
    JACK(2),
    NINE(0),
    EIGHT(0),
    SEVEN(0);

    final int value;

    CardValue(int value) {

        this.value = value;
    }

    /**
     * Returns the card value that is used to calculate the sum of the points at the end of game.
     */
    public int getCardValue() {

        return value;
    }

    /**
     * This value can be used to calculate the strength of two card-colors in dependence of a given trump.
     * CardColor is not taken into account.
     */
    public int getValueStrength(Trump trump) {

    //    var tenValue = 7;
    //    var jackValue = 9;

        var jackValue = 4;

        var aceValue = 6;
        var tenValue = 5;
        var kingValue = 4;
        var queenValue = 3;

        if ( trump.getGameMode() == GameMode.NULL ) {

            tenValue = 3;
            queenValue = 5;
            kingValue = 6;
            aceValue = 7;
        }

        if ( trump.getGameMode() == GameMode.GRAND || trump.getGameMode() == GameMode.SUIT) {

            jackValue = 60;
        }

        return switch ( this ) {

            case ACE -> aceValue;
            case TEN -> tenValue;
            case KING -> kingValue;
            case QUEEN -> queenValue;
            case JACK -> jackValue;
            case NINE -> 2;
            case EIGHT -> 1;
            case SEVEN -> 0;
        };
    }

    public String getSymbol(){

        return switch ( this ){

            case ACE -> "·A·";
            case TEN -> "·10";
            case KING -> "·K·";
            case QUEEN -> "·Q·";
            case JACK -> "·J·";
            case NINE -> "·9·";
            case EIGHT -> "·8·";
            case SEVEN -> "·7·";

        };
    }

}
