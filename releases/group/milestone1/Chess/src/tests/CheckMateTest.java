package tests;

import core.ChessBoard;
import core.ChessResult;
import core.pieces.ChessPieceType;
import core.positioning.File;
import core.positioning.Rank;
import core.positioning.Square;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CheckMateTest extends Test {

    Square[][] moves = new Square[][]{{new Square("f2"), new Square("f3")},
            {new Square("e7"), new Square("e5")},
            {new Square("g2"), new Square("g4")},
            {new Square("d8"), new Square("h4")}};

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
        ChessResult result = testController.getGame().getResult();
        return result == ChessResult.CHECKMATE;
    }
}
