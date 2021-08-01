package engine.pieces;

/**
 * Controller for the GUI options interface
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public final class ChessPieceCopyFactory {
	
	private ChessPieceCopyFactory() {
		//Unused
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
