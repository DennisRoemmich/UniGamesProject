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
    UNDOLASTMOVE(9);

    int value;

    ActionType(int value) {
        this.value = value;
    }

    boolean usesPoints() {
        return ( value == 1 || value == 2 || value == 3 || value == 8 );
    }

}
