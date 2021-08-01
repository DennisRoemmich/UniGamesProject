package engine.pieces;

import engine.*;
import engine.analysis.CheckDetector;
import engine.board.ChessMove;
import engine.squares.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Abstract ChessPiece class that is the super class to all the chess pieces.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public abstract class ChessPiece {

    private final ChessPieceType mType;
    private final PlayerColor mPlayerColor;
    private int mNumberOfMoves = 0;

    /* Constructor */

    protected ChessPiece(PlayerColor playerColor, ChessPieceType type) {
        this.mPlayerColor = playerColor;
        this.mType = type;
    }

    /* Functionality */

    public abstract List<Square> findCoveredSquares(Chess game);

    public List<ChessMove> findMoves(Chess game) {
        
    	Optional<Square> s = game.getBoard().getSquare(this);
    	if (!s.isPresent()) {
        	return new ArrayList<>();
        }

        Square origin = s.get();

        List<Square> coveredSquares = findCoveredSquares(game);
        List<ChessMove> validMoves = new ArrayList<>();

        for (Square destination : coveredSquares) {
            if (isSquareFreeOrOpponent(game, destination)) {
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
        mNumberOfMoves++;
    }

    public void undoMoveRegistry() {
        mNumberOfMoves--;
    }

    /* Getter */

    public final int getNumberOfMoves() {
        return mNumberOfMoves;
    }

    public final String getName() {
        return mType.name();
    }

    public PlayerColor getColor() {
        return mPlayerColor;
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
        if (mPlayerColor.isWhite()) {
            c = Character.toLowerCase(c);
        }
        return c;
    }

    public final char toSymbol() {
        switch (mType) {
            case PAWN:
                return  mPlayerColor.isWhite() ? '♙' : '♟';
            case KNIGHT:
                return  mPlayerColor.isWhite() ? '♘' : '♞';
            case BISHOP:
                return  mPlayerColor.isWhite() ? '♗' : '♝';
            case ROOK:
                return  mPlayerColor.isWhite() ? '♖' : '♜';
            case QUEEN:
                return  mPlayerColor.isWhite() ? '♕' : '♛';
            case KING:
                return  mPlayerColor.isWhite() ? '♔' : '♚';
        }
        return ' ';
    }

    @Override
    public final String toString() {
        return (mPlayerColor.isWhite() ? "WHITE_" : "BLACK_") + mType.toString();
    }

    public double getSignedValue() {
        return mType.getValue() * mPlayerColor.getScoreFactor();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
        	return true;
        }
        if (o == null || getClass() != o.getClass()) {
        	return false;
        }
        ChessPiece piece = (ChessPiece) o;
        return mType == piece.mType && getColor() == piece.getColor();
    }

    @Override
    public int hashCode() {
        return Objects.hash(mType, getColor().isWhite());
    }
}
