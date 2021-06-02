package core.pieces;

import core.CheckDetector;
import core.ChessBoard;
import core.positioning.Position;

import java.util.List;

public abstract class ChessPiece implements Cloneable {

    protected final ChessPieceType type;
    protected final boolean isWhite;

    public final String getName() {
        return type.name();
    }

    public final boolean isWhite() {
        return isWhite;
    }

    public final ChessPieceType getType() {
        return type;
    }

    public final String toString() {
        return (isWhite ? "WHITE " : "BLACK ") + type.toString();
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

    public abstract List<Position> findMoves(Position pos, ChessBoard board);

    protected ChessPiece(boolean isWhite, ChessPieceType type) {
        this.isWhite = isWhite;
        this.type = type;
    }

    @Override
    public ChessPiece clone() {
        try {
            ChessPiece clone = getClass().getDeclaredConstructor(boolean.class).newInstance(isWhite);
            return clone;
        } catch (Exception e) {

        }
        return null;
    }
}
