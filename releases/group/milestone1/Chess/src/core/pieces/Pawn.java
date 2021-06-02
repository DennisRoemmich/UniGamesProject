package core.pieces;

public class Pawn extends ChessPiece {

    final ChessPieceType type = ChessPieceType.PAWN;
    private int numberOfMoves = 0;
    private int lastMoved = 0;

    public Pawn(boolean isWhite){
        this.isWhite = isWhite;
        super.type = this.type;
    }

    public void registerMove(int moveNumber) {
        numberOfMoves++;
        lastMoved = moveNumber;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public int getLastMoved() {
        return lastMoved;
    }
}
