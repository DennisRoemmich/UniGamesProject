package torpedo;

import engine.pieces.Color;
import engine.pieces.Pawn;

public class TorpedoPawn extends Pawn {
    public TorpedoPawn(Color color) {
        super(color);
    }

    @Override
    protected boolean isDoubleMovePossible() {
        return true;
    }
}
