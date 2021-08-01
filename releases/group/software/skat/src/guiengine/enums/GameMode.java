package guiengine.enums;

/**
 * enum for possible gamemodes
 */
public enum GameMode {

    SUIT(1),
    GRAND(24),
    NULL(23);

    final int value;

    /* CONSTRUCTOR */

    GameMode(int value) {

        this.value = value;
    }

    /* GETTER */

    public int getModeValue() {

        return value;
    }
}
