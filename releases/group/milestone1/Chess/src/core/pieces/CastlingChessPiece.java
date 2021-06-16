package core.pieces;

/**
 * Methods for obtaining info for the castling conditions.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public abstract class CastlingChessPiece extends ChessPiece {
    
	private boolean mHasMoved = false;

    protected CastlingChessPiece(boolean isWhite, ChessPieceType type) {
        super(isWhite, type);
    }

    public void registerMove() {
        mHasMoved = true;
    }

    public boolean hasMoved() {
        return mHasMoved;
    }
}
