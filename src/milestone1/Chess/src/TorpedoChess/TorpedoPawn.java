package TorpedoChess;

import core.Color;
import core.pieces.Pawn;

public class TorpedoPawn extends Pawn {
    public TorpedoPawn(Color color) {
        super(color);
    }

    @Override
    protected boolean isDoubleMovePossible() {
        return true;
    }
}
