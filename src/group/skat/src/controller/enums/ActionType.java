package controller.enums;

public enum ActionType {

    NEW_SET,
    NEW_GAME,
    SORT,
    ON_HAND,
    RAISE_OR_ACCEPT,
    PASS,
    SKAT_TO_HAND,
    HAND_TO_SKAT,
    DROP_SKAT,
    SET_TRUMP,
    PLAY_CARD;

    public boolean isSkatMove(){

        return ( !(this == NEW_SET || this == NEW_GAME ) );

    }

    public boolean usesIndices(){

        return ( this == ON_HAND || this == SKAT_TO_HAND || this == HAND_TO_SKAT );

    }

    public boolean usesIndex(){

        return ( this == PLAY_CARD );

    }

    public boolean usesTrump(){

        return ( this == SET_TRUMP );

    }

}
