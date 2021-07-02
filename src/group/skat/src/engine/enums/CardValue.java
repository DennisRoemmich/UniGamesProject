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

    CardValue(int value){
        this.value = value;
    }


    /**
     * Returns the card value that is used to calculate the sum of the points at the end of game.
     */
    public int getCardValue(){

        return value;

    }

    /**
     * This value can be used to calculate the strength of two card-colors in dependence of a given trump.
     * CardColor is not taken into account.
     */
    public int getStrengthValue(Trump trump){

        var tenValue = 8;
        var jackValue = 10;

        if ( trump.getGameMode() == GameMode.NULL ) {

            tenValue = 4;
            jackValue = 5;

        }



        return switch ( this ) {

            case ACE -> 9;
            case TEN -> tenValue;
            case KING -> 7;
            case QUEEN -> 6;
            case JACK -> jackValue;
            case NINE -> 3;
            case EIGHT -> 2;
            case SEVEN -> 1;

        };

    }

}
