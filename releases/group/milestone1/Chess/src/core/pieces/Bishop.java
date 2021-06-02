package core.pieces;

public class Bishop extends ChessPiece {

    final ChessPieceType type = ChessPieceType.BISHOP;

    public Bishop(boolean isWhite){
        this.isWhite = isWhite;
        super.type = this.type;
    }
}
