package core;

import core.pieces.ChessPiece;
import core.pieces.ChessPieceCopyFactory;
import core.positioning.Square;

public class PositionedPiece {
    private Square position;
    private ChessPiece piece;

    public PositionedPiece(Square position, ChessPiece piece) {
        if(position == null || piece == null) {
            throw new IllegalArgumentException();
        }
        this.position = position;
        this.piece = piece;
    }

    public PositionedPiece(PositionedPiece positionedPiece) {
        this.position = new Square(positionedPiece.getPosition());
        this.piece = ChessPieceCopyFactory.getCopy(positionedPiece.getPiece());
    }

    public Square getPosition() {
        return position;
    }

    public ChessPiece getPiece() {
        return piece;
    }

    public void setPiece(ChessPiece piece) {
        this.piece = piece;
    }

    @Override
    public String toString() {
        return piece + " @ " + position;
    }
}
