package core;

public abstract class ChessPiece {

    protected ChessPieceType type;
    protected boolean isWhite;

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
}
