package engine.enums;

public enum GameMode {

    SUIT(1),
    GRAND(24),
    NULL(23);

    final int value;

    GameMode(int value) {

        this.value = value;
    }
}
