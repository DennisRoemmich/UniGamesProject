package core.pieces;

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
