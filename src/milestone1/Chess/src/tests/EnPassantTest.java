package tests;

import core.Chess;
import core.positioning.File;
import core.positioning.Rank;
import core.positioning.Square;
import org.json.simple.JSONObject;
import sample.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests the En-Passant moves in a chess game.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class EnPassantTest extends Test {

    Square[][] mMoves = new Square[][]{{new Square("e2"), new Square("e4")},
            {new Square("c7"), new Square("c5")},
            {new Square("e4"), new Square("e5")},
            {new Square("f7"), new Square("f5")},
            {new Square("e5"), new Square("f6")}};

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
        Square capturedSquare = new Square(Rank.M5, File.F);
        return Chess.getBoard().getPiece(capturedSquare) == null;
    }
}
