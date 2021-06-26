package core.pieces;

public class ChessPieceFactory {
    public ChessPiece copyPiece(ChessPiece piece) {
        return valueOf(piece.getType(), piece.isWhite());
    }

    public ChessPiece valueOf(ChessPieceType type, boolean isWhite) {
        switch (type) {
            case PAWN -> {
                return new Pawn(isWhite);
            }
            case KNIGHT -> {
                return new Knight(isWhite);
            }
            case BISHOP -> {
                return new Bishop(isWhite);
            }
            case ROOK -> {
                return new Rook(isWhite);
            }
            case QUEEN -> {
                return new Queen(isWhite);
            }
            case KING -> {
                return new King(isWhite);
            }
            default -> {
                throw new IllegalArgumentException();
            }
        }
    }

}
