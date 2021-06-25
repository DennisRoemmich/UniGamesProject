package TorpedoChess;

import core.pieces.Pawn;

public class TorpedePawn extends Pawn {
    public TorpedePawn(boolean isWhite) {
        super(isWhite);
    }

    @Override
    protected boolean isDoubleMovePossible() {
        return true;
    }
}
