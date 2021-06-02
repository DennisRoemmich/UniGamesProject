package core.pieces;

public class Knight extends ChessPiece  {

    final ChessPieceType type = ChessPieceType.KNIGHT;

    public Knight(boolean isWhite){
        this.isWhite = isWhite;
        super.type = this.type;
    }
}
