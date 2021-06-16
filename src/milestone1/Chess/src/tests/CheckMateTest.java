package tests;

import core.Chess;
import core.ChessResult;
import core.positioning.Square;
import org.json.simple.JSONObject;
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

    protected List<JSONObject> getMoves() {
        List<JSONObject> list = new ArrayList<>();
        for (Square[] move : mMoves) {
            JSONObject newJSonMove = new JSONObject();
            newJSonMove.put("origin", move[0].toString());
            newJSonMove.put("destination", move[1].toString());
            list.add(newJSonMove);
        }
        return list;
    }

    public boolean runTest() {
        Chess.resetGame();
        super.runMoves();
        ChessResult result = Chess.getResult();
        return result == ChessResult.CHECKMATE;
    }
}
