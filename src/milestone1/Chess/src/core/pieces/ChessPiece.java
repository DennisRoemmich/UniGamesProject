package core.pieces;

import core.CheckDetector;
import core.ChessBoard;
import core.ChessMove;
import core.Color;
import core.positioning.Square;
import framework.WriteError;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Abstract ChessPiece class that is the super class to all the chess pieces.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public abstract class ChessPiece implements Cloneable {

    private final ChessPieceType mType;
    private final Color color;
    private int numberOfMoves = 0;

    /* Constructor */

    protected ChessPiece(Color color, ChessPieceType type) {
        this.color = color;
        this.mType = type;
    }

    /* Functionality */

    public abstract List<Square> findCoveredSquares(ChessBoard board);

    public List<ChessMove> findMoves(ChessBoard board) {
        if(board.getSquare(this).isEmpty()) return new ArrayList<>();

        Square origin = board.getSquare(this).get();

        List<Square> coveredSquares = findCoveredSquares(board);
        List<ChessMove> validMoves = new ArrayList<>();
        for (Square destination : coveredSquares) {
            ChessMove move = new ChessMove(origin, destination);
            if (!CheckDetector.isInCheckAfterMove(board, color, move)) {
                validMoves.add(move);
            }
        }
        return validMoves;
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
               return  color.isWhite() ? '♙' : '♟';
            case KNIGHT:
                return  color.isWhite() ? '♘' : '♞';
            case BISHOP:
                return  color.isWhite() ? '♗' : '♝';
            case ROOK:
                return  color.isWhite() ? '♖' : '♜';
            case QUEEN:
                return  color.isWhite() ? '♕' : '♛';
            case KING:
                return  color.isWhite() ? '♔' : '♚';
        }
        return ' ';
    }

    @Override
    public final String toString() {
        return (color.isWhite() ? "WHITE " : "BLACK ") + mType.toString();
    }

}
