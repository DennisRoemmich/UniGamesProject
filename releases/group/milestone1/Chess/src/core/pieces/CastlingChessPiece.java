package core.pieces;

public abstract class CastlingChessPiece extends ChessPiece {
    private boolean hasMoved = false;

    protected CastlingChessPiece(boolean isWhite, ChessPieceType type) {
        super(isWhite, type);
    }

    public void registerMove() {
        hasMoved = true;
    }

    public boolean hasMoved() {
        return hasMoved;
    }
}
