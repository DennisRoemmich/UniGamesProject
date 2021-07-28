package engine.board;

import engine.squares.Square;
import org.json.simple.JSONObject;

public class CastlingMove extends ChessMove {

    public CastlingMove(Square origin, Square destination) {
        super(origin, destination);
    }

    @Override
    public JSONObject toJSon() {
        JSONObject regularJSON = super.toJSon();
        regularJSON.put("specialmove", "castling");
        return regularJSON;
    }
}
