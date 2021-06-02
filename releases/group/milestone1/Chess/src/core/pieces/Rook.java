package core.pieces;

public class Rook extends ChessPiece  {

    final ChessPieceType type = ChessPieceType.ROOK;

    private boolean hasMoved = false;

    public Rook(boolean isWhite){
        this.isWhite = isWhite;
        super.type = this.type;
    }
}
