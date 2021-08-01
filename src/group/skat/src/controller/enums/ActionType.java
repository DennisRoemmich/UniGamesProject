package controller.enums;

/**
 *  This enum consists of all possible actions in the game
 */
public enum ActionType {

    NEW_SET,
    NEW_GAME,
    SORT,
    RAISE_OR_ACCEPT,
    PASS,
    ON_HAND,
    ON_SKATHAND,
    DROP_SKAT,
    SET_TRUMP,
    PLAY_CARD;

    public boolean isSkatMove() {

        return ( !(this == NEW_SET || this == NEW_GAME ) );

    }

    public boolean usesIndices() {

        return ( this == ON_HAND || this == ON_SKATHAND );

    }

    public boolean usesIndex() {

        return ( this == PLAY_CARD );

    }

    public boolean usesTrump() {

        return ( this == SET_TRUMP );

    }

}
