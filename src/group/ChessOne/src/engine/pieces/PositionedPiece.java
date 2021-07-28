package engine.pieces;

import engine.squares.Square;

import java.util.Objects;

public class PositionedPiece {
    private Square mPosition;
    private ChessPiece mPiece;

    public PositionedPiece(Square position, ChessPiece piece) {
        this.mPosition = position;
        this.mPiece = piece;
    }

    public PositionedPiece(PositionedPiece positionedPiece) {
        this.mPosition = new Square(positionedPiece.getPosition());
        this.mPiece = ChessPieceCopyFactory.getCopy(positionedPiece.getPiece());
    }

    public Square getPosition() {
        return mPosition;
    }

    public ChessPiece getPiece() {
        return mPiece;
    }

    public void setPiece(ChessPiece piece) {
        this.mPiece = piece;
    }

    @Override
    public String toString() {
        return mPiece + " @ " + mPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
        	return true;
        }
        if (o == null || getClass() != o.getClass()) {
        	return false;
        }
        PositionedPiece that = (PositionedPiece) o;
        return Objects.equals(getPosition(), that.getPosition()) && Objects.equals(getPiece(), that.getPiece());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPosition(), getPiece());
    }
}
