package core.pieces;

public class Queen extends ChessPiece  {

    final ChessPieceType type = ChessPieceType.QUEEN;

    public Queen(boolean isWhite){
        this.isWhite = isWhite;
        super.type = this.type;
    }
}
