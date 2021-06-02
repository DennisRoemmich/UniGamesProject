package core.pieces;

public class King extends ChessPiece  {

    final ChessPieceType type = ChessPieceType.KING;

    private boolean hasMoved = false;

    public King(boolean isWhite){
        this.isWhite = isWhite;
        super.type = this.type;
    }
}
