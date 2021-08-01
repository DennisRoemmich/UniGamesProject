package tests;

import engine.analysis.ChessResult;
import engine.board.ChessMove;
import engine.squares.Square;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests the checkmate feature.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class CheckMateTest extends Test {

    Square[][] mMoves = new Square[][]{{new Square("f2"), new Square("f3")},
            {new Square("e7"), new Square("e5")},
            {new Square("g2"), new Square("g4")},
            {new Square("d8"), new Square("h4")}};

    protected List<ChessMove> getMoves() {
        List<ChessMove> list = new ArrayList<>();
        for (Square[] originDestinationPair : mMoves) {
            ChessMove move = new ChessMove(originDestinationPair[0], originDestinationPair[1]);
            list.add(move);
        }
        return list;
    }

    public boolean runTest() {
        super.runMoves();
        ChessResult result = mGame.getResult();
        return result == ChessResult.CHECKMATE;
    }
}
