package engine.pieces;

import engine.*;
import engine.analysis.CheckDetector;
import engine.board.ChessMove;
import engine.squares.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Abstract ChessPiece class that is the super class to all the chess pieces.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public abstract class ChessPiece {

    private final ChessPieceType mType;
    private final Color color;
    private int numberOfMoves = 0;

    /* Constructor */

    protected ChessPiece(Color color, ChessPieceType type) {
        this.color = color;
        this.mType = type;
    }

    /* Functionality */

    public abstract List<Square> findCoveredSquares(Chess game);

    public List<ChessMove> findMoves(Chess game) {
        if(game.getBoard().getSquare(this).isEmpty()) return new ArrayList<>();

        Square origin = game.getBoard().getSquare(this).get();

        List<Square> coveredSquares = findCoveredSquares(game);
        List<ChessMove> validMoves = new ArrayList<>();

        for (Square destination : coveredSquares) {
            if(isSquareFreeOrOpponent(game, destination)) {
                ChessMove move = new ChessMove(origin, destination);
                if (!CheckDetector.isInCheckAfterMove(game, move)) {
                    validMoves.add(move);
                }
            }
        }
        return validMoves;
    }

    private boolean isSquareFreeOrOpponent(Chess game, Square square) {
        var piece = game.getBoard().getPiece(square);
        return piece.isEmpty() || piece.get().getColor().equals(getColor().getContrary());
    }

    public void registerMove() {
        numberOfMoves++;
    }

    public void undoMoveRegistry() {
        numberOfMoves--;
    }

    /* Getter */

    public final int getNumberOfMoves() {
        return numberOfMoves;
    }

    public final String getName() {
        return mType.name();
    }

    public Color getColor() {
        return color;
    }

    public final ChessPieceType getType() {
        return mType;
    }

    /* General / Override */

    public final char toChar() {
        char c = switch (mType) {
            case KNIGHT -> 'N';
            default -> mType.toString().charAt(0);
        };
        if (color.isWhite()) {
            c = Character.toLowerCase(c);
        }
        return c;
    }

    public final char toSymbol() {
        switch (mType) {
            case PAWN:
                return  color.isWhite() ? '♟' : '♙';
            case KNIGHT:
                return  color.isWhite() ? '♞' : '♘';
            case BISHOP:
                return  color.isWhite() ? '♝' : '♗';
            case ROOK:
                return  color.isWhite() ? '♜' : '♖';
            case QUEEN:
                return  color.isWhite() ? '♛' : '♕';
            case KING:
                return  color.isWhite() ? '♚' : '♔';
        }
        return ' ';
    }

    @Override
    public final String toString() {
        return (color.isWhite() ? "WHITE_" : "BLACK_") + mType.toString();
    }

    public double getSignedValue() {
        return mType.getValue() * color.getScoreFactor();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece piece = (ChessPiece) o;
        return mType == piece.mType && getColor() == piece.getColor();
    }

    @Override
    public int hashCode() {
        return Objects.hash(mType, getColor().isWhite());
    }
}
