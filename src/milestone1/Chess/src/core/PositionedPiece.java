package core;

import core.pieces.ChessPiece;
import core.positioning.Square;

public class PositionedPiece {
    private Square position;
    private ChessPiece piece;

    public PositionedPiece(Square position, ChessPiece piece) {
        this.position = position;
        this.piece = piece;
    }

    public PositionedPiece(PositionedPiece positionedPiece) {
        this.position = new Square(positionedPiece.getPosition());
        this.piece = positionedPiece.getPiece().clone();
    }

    public Square getPosition() {
        return position;
    }

    public void setPosition(Square position) {
        this.position = position;
    }

    public ChessPiece getPiece() {
        return piece;
    }

    public void setPiece(ChessPiece piece) {
        this.piece = piece;
    }
}
