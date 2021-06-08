package core.pieces;

public enum ChessPieceType {
    PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING;

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
}
