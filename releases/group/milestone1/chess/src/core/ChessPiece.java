package core;

public class ChessPiece {

    private int lastMove = 0;

    private ChessPieceType type;
    private boolean isWhite;

    protected ChessPiece(ChessPieceType type, boolean isWhite) {
        this.type = type;
        this.isWhite = isWhite;
    }

    public final String getName() {
        return type.name();
    }

    public boolean isWhite() {
        return isWhite;
    }

    public int getLastMove() {
        return lastMove;
    }

    public void setLastMove(int i) {
        if(i > lastMove){
            lastMove = i;
        }
    }

    public ChessPieceType getType() {
        return type;
    }

    public String toString() {
        return (isWhite ? "WHITE " : "BLACK ") + type.toString();
    }
}
