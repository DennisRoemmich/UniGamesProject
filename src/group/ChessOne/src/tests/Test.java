package tests;

import engine.Chess;
import engine.board.ChessMove;
import java.util.List;

/**
 * Runs the test controller.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public abstract class Test {
	
	Chess mGame = new Chess();
	
    protected abstract List<ChessMove> getMoves();
    
    public abstract boolean runTest();

    public void runMoves() {
        for (ChessMove move : getMoves()) {
            mGame.makeMove(move);
        }
    }
}
