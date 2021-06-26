package TorpedoChess;

import core.pieces.Pawn;

public class TorpedoPawn extends Pawn {
    public TorpedoPawn(boolean isWhite) {
        super(isWhite);
    }

    @Override
    protected boolean isDoubleMovePossible() {
        return true;
    }
}
