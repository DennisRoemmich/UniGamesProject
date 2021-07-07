package controller.enums;

public enum ActionType {

    NEW_SET,
    NEW_GAME,
    SORT,
    RAISE_OR_ACCEPT,
    PASS,
    ON_HAND,
    ON_SKATHAND, // TODO: Discuss: we don't need skat_to_hand and hand_to_skat and if we do, we'd also need on_skat. Just see Skat as Hand 11 & 12.
    DROP_SKAT,
    SET_TRUMP,
    PLAY_CARD;

    public boolean isSkatMove(){

        return ( !(this == NEW_SET || this == NEW_GAME ) );

    }

    public boolean usesIndices(){

        return ( this == ON_HAND || this == ON_SKATHAND );

    }

    public boolean usesIndex(){

        return ( this == PLAY_CARD );

    }

    public boolean usesTrump(){

        return ( this == SET_TRUMP );

    }

}
