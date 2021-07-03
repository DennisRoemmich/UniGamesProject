package engine.enums;

import engine.Trump;

public enum CardColor {

    CLUBS(12),
    SPADES(11),
    HEARTS(10),
    DIAMONDS(9);

    final int value;

    CardColor(int value) {

        this.value = value;
    }

    public int getCardColorValue() {

        return value;
    }

    public int getColorStrength(Trump trump, CardColor trickColor) {

        var clubsValue = 30;
        var spadesValue = 20;
        var heartsValue = 10;
        var diamondsValue = 0;

        switch ( trickColor ) {

            case CLUBS -> clubsValue = 40;
            case SPADES -> spadesValue = 40;
            case HEARTS -> heartsValue = 40;
            case DIAMONDS -> diamondsValue = 40;
        }

        switch ( trump.getColor() ) {

            case CLUBS -> clubsValue = 50;
            case SPADES -> spadesValue = 50;
            case HEARTS -> heartsValue = 50;
            case DIAMONDS -> diamondsValue = 50;
            default -> { /* gameMode not SUIT */ }
        }

        return switch ( this ) {

            case CLUBS -> clubsValue;
            case SPADES -> spadesValue;
            case HEARTS -> heartsValue;
            case DIAMONDS -> diamondsValue;
        };
    }
}
