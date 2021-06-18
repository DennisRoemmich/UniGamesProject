package tests;

import core.ChessBoard;
import core.pieces.ChessPieceType;
import core.positioning.File;
import core.positioning.Rank;
import core.positioning.Square;
import org.json.simple.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Tests the castling feature.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class CastlingTest extends Test {

    Square[][] mMove = new Square[][]{{new Square("e2"), new Square("e4")},
            {new Square("e7"), new Square("e5")},
            {new Square("g1"), new Square("f3")},
            {new Square("b8"), new Square("c6")},
            {new Square("f1"), new Square("c4")},
            {new Square("f8"), new Square("c5")},
            {new Square("e1"), new Square("g1")}};

    protected List<JSONObject> getMoves() {
        List<JSONObject> list = new ArrayList<>();
        for (Square[] move : mMove) {
        	HashMap<String, String> rawNewJsonMove = new HashMap<>();           
        	rawNewJsonMove.put("origin", move[0].toString());
        	rawNewJsonMove.put("destination", move[1].toString());
            JSONObject newJSonMove = new JSONObject(rawNewJsonMove);
            list.add(newJSonMove);
        }
        return list;
    }

    public boolean runTest() {
        super.mTestController.createGame();
        super.runMoves();
        ChessBoard board = super.mTestController.getGame().getBoard();
        Square rookSquare = new Square(Rank.M1, File.F);
        return !board.isFieldFree(rookSquare) && board.getPiece(rookSquare).getType() == ChessPieceType.ROOK;
    }
}
