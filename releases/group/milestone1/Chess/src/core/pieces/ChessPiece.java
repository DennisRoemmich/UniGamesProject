package core.pieces;

import core.CheckDetector;
import core.ChessBoard;
import core.positioning.Square;

import java.util.ArrayList;
import java.util.List;

public abstract class ChessPiece implements Cloneable {

    protected final ChessPieceType type;
    protected final boolean isWhite;

    /* Constructor */

    protected ChessPiece(boolean isWhite, ChessPieceType type) {
        this.isWhite = isWhite;
        this.type = type;
    }

    /* Functionality */

    public abstract List<Square> findMovesDisregardingCheck(Square pos, ChessBoard board);

    public final List<Square> findMoves(Square pos, ChessBoard board) {
        List<Square> movesDisregardingCheck = findMovesDisregardingCheck(pos, board);
        List<Square> validMoves = new ArrayList<Square>();
        for(Square uncheckedSquare : movesDisregardingCheck) {
            if(!CheckDetector.isInCheckAfterMove(board, isWhite, pos, uncheckedSquare)) {
                validMoves.add(uncheckedSquare);
            }
        }
        return validMoves;
    }

    /* Getter */

    public final String getName() {
        return type.name();
    }

    public final boolean isWhite() {
        return isWhite;
    }

    public final ChessPieceType getType() {
        return type;
    }

    /* General / Override */

    public final char toChar() {
        char c = ' ';
        switch (type) {
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
        if (isWhite) {
            c = Character.toLowerCase(c);
        }
        return c;
    }

    public final char toSymbol() {
        switch (type) {
            case PAWN:
                if (isWhite) {
                    return '♟';
                } else {
                    return '♙';
                }
            case KNIGHT:
                if (isWhite) {
                    return '♞';
                } else {
                    return '♘';
                }
            case BISHOP:
                if (isWhite) {
                    return '♝';
                } else {
                    return '♗';
                }
            case ROOK:
                if (isWhite) {
                    return '♜';
                } else {
                    return '♖';
                }
            case QUEEN:
                if (isWhite) {
                    return '♛';
                } else {
                    return '♕';
                }
            case KING:
                if (isWhite) {
                    return '♚';
                } else {
                    return '♔';
                }
        }
        return ' ';
    }

    @Override
    public final String toString() {
        return (isWhite ? "WHITE " : "BLACK ") + type.toString();
    }
    
    //Remove this "clone" implementation; use a copy constructor or copy factory instead.
    @Override
    public ChessPiece clone() {
        try {
            return getClass().getDeclaredConstructor(boolean.class).newInstance(isWhite);
        } catch (Exception e) {

        }
        return null;
    }
}
