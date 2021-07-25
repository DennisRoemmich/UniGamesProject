package core.pieces;

import core.CheckDetector;
import core.ChessBoard;
import core.Color;
import core.positioning.Square;
import framework.WriteError;
import java.util.ArrayList;
import java.util.List;

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

    public abstract List<Square> findCoveredSquares(ChessBoard board, Square origin);

    public List<Square> findMoves(ChessBoard board, Square square) {
        List<Square> coveredSquares = findCoveredSquares(board, square);
        List<Square> validMoves = new ArrayList<>();
        for (Square uncheckedSquare : coveredSquares) {
            if (!CheckDetector.isInCheckAfterMove(board, color, square, uncheckedSquare)) {
                validMoves.add(uncheckedSquare);
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
        char c = ' ';
        switch (mType) {
            case PAWN:
                c = 'P';
                break;
            case KNIGHT:
                c = 'N';
                break;
            case BISHOP:
                c = 'B';
                break;
            case ROOK:
                c = 'R';
                break;
            case QUEEN:
                c = 'Q';
                break;
            case KING:
                c = 'K';
                break;
        }
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
    
    ///TODO: Eliminate clone method. Use copy constructor.
    @Override
    public ChessPiece clone() {
        
    	//Empty piece
    	ChessPiece piece = new Pawn(true);
    	try {
            return getClass().getDeclaredConstructor(boolean.class).newInstance(color.isWhite());
        } catch (Exception e) {
        	WriteError.writeErrorLog("");
        }
        return piece;
    }
}
