package core;

import core.pieces.ChessPiece;
import core.pieces.ChessPieceCopyFactory;
import core.positioning.Square;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionedPiece that = (PositionedPiece) o;
        return Objects.equals(getPosition(), that.getPosition()) && Objects.equals(getPiece(), that.getPiece());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPosition(), getPiece());
    }
}
