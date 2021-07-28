package engine.pieces;

/**
 * Stores the different chess pieces as enums.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public enum ChessPieceType {
    PAWN(1), KNIGHT(3), BISHOP(3), ROOK(5), QUEEN(9), KING(100);

    private final int mValue;

    ChessPieceType(int value) {
        this.mValue = value;
    }

    public static ChessPieceType valueOf(char c) {
        switch (c) {
            case 'p', 'P':
                return PAWN;
            case 'n', 'N':
                return KNIGHT;
            case 'b', 'B':
                return BISHOP;
            case 'r', 'R':
                return ROOK;
            case 'q', 'Q':
                return QUEEN;
            case 'k', 'K':
                return KING;
            default:
                throw new IllegalArgumentException();
        }
    }

    public int getValue() {
        return mValue;
    }
}
