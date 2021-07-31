package torpedo;

import engine.pieces.PlayerColor;
import engine.pieces.Pawn;

public class TorpedoPawn extends Pawn {
    public TorpedoPawn(PlayerColor playerColor) {
        super(playerColor);
    }

    @Override
    protected boolean isDoubleMovePossible() {
        return true;
    }
}
