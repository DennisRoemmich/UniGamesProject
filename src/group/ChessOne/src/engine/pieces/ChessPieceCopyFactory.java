package engine.pieces;

public final class ChessPieceCopyFactory {
	private ChessPieceCopyFactory() {
		
	}
	
    public static ChessPiece getCopy(ChessPiece piece) {
        return switch (piece.getType()) {
            case PAWN -> new Pawn(piece.getColor());
            case KNIGHT -> new Knight(piece.getColor());
            case BISHOP -> new Bishop(piece.getColor());
            case ROOK -> new Rook(piece.getColor());
            case QUEEN -> new Queen(piece.getColor());
            case KING -> new King(piece.getColor());
        };
    }
}
