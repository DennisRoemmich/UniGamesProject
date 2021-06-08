package tests;

import core.positioning.File;
import core.positioning.Rank;
import core.positioning.Square;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EnPassantTest extends Test {

    Square[][] moves = new Square[][]{{new Square("e2"), new Square("e4")},
            {new Square("c7"), new Square("c5")},
            {new Square("e4"), new Square("e5")},
            {new Square("f7"), new Square("f5")},
            {new Square("e5"), new Square("f6")}};

    protected List<JSONObject> getMoves() {
        List<JSONObject> list = new ArrayList<>();
        for(Square[] move : moves) {
            JSONObject newJSONmove = new JSONObject();
            newJSONmove.put("origin", move[0].toString());
            newJSONmove.put("destination", move[1].toString());
            list.add(newJSONmove);
        }
        return list;
    }

    public boolean runTest() {
        super.testController.createGame();
        super.runMoves();
        Square capturedSquare = new Square(Rank.M5, File.F);
        return testController.getGame().getBoard().getPiece(capturedSquare) == null;
    }
}
