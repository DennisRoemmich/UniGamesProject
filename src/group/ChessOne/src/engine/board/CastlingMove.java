package engine.board;

import engine.squares.Square;
import org.json.simple.JSONObject;

public class CastlingMove extends ChessMove {

    public CastlingMove(Square origin, Square destination) {
        super(origin, destination);
    }

    @SuppressWarnings("unchecked")
				@Override
    public JSONObject toJSon() {
        JSONObject regularJSon = super.toJSon();
        regularJSon.put("specialmove", "castling");
        return regularJSon;
    }
}
