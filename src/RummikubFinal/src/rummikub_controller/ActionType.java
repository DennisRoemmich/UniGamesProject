package rummikub_controller;

public enum ActionType {

    FINISHMOVE(0),
    ONRACK(1),
    ONBOARD(2),
    RACKTOBOARD(3),
    SORTGROUP(4),
    SORTRUN(5),
    RESET(6),
    QUIT(7),
    BOARDTORACK(8),
    UNDOLASTMOVE(9),
    STARTGAME(10);

    int mValue;

    ActionType(int value) {
        this.mValue = value;
    }

    boolean usesPoints() {
        return ( mValue == 1 || mValue == 2 || mValue == 3 || mValue == 8 );
    }

}
