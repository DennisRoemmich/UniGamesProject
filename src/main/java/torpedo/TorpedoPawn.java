package torpedo;

import engine.pieces.PlayerColor;
import engine.pieces.Pawn;

/**
 * Special pawns for the torpedo game (can move two squares anytime)
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class TorpedoPawn extends Pawn {
    public TorpedoPawn(PlayerColor playerColor) {
        super(playerColor);
    }

    @Override
    protected boolean isDoubleMovePossible() {
        return true;
    }
}
