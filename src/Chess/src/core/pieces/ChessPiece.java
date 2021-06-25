package core.pieces;

import core.CheckDetector;
import core.ChessBoard;
import core.positioning.Square;
import sample.WriteError;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract ChessPiece class that is the super class to all the chess pieces.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public abstract class ChessPiece implements Cloneable {

    private final ChessPieceType mType;
    private final boolean mIsWhite;
    private int numberOfMoves = 0;

    /* Constructor */

    protected ChessPiece(boolean isWhite, ChessPieceType type) {
        this.mIsWhite = isWhite;
        this.mType = type;
    }

    /* Functionality */

    public abstract List<Square> findCoveredSquares(ChessBoard board, Square origin);

    public List<Square> findMoves(ChessBoard board, Square square) {
        List<Square> coveredSquares = findCoveredSquares(board, square);
        List<Square> validMoves = new ArrayList<>();
        for (Square uncheckedSquare : coveredSquares) {
            if (!CheckDetector.isInCheckAfterMove(board, mIsWhite, square, uncheckedSquare)) {
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

    public final boolean isWhite() {
        return mIsWhite;
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
        if (mIsWhite) {
            c = Character.toLowerCase(c);
        }
        return c;
    }

    public final char toSymbol() {
        switch (mType) {
            case PAWN:
               return  mIsWhite ? '♙' : '♟';
            case KNIGHT:
                return  mIsWhite ? '♘' : '♞';
            case BISHOP:
                return  mIsWhite ? '♗' : '♝';
            case ROOK:
                return  mIsWhite ? '♖' : '♜';
            case QUEEN:
                return  mIsWhite ? '♕' : '♛';
            case KING:
                return  mIsWhite ? '♔' : '♚';
        }
        return ' ';
    }

    @Override
    public final String toString() {
        return (mIsWhite ? "WHITE " : "BLACK ") + mType.toString();
    }
    
    ///TODO: Eliminate clone method. Use copy constructor.
    @Override
    public ChessPiece clone() {
        
    	//Empty piece
    	ChessPiece piece = new Pawn(true);
    	try {
            return getClass().getDeclaredConstructor(boolean.class).newInstance(mIsWhite);
        } catch (Exception e) {
        	WriteError.writeErrorLog("");
        }
        return piece;
    }
}
