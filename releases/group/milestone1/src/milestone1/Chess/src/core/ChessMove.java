package core;

import core.positioning.Square;
import org.json.simple.JSONObject;

public class ChessMove {
    private Square mOrigin;
    private Square mDestination;

    public ChessMove(Square origin, Square destination) {
        this.mOrigin = origin;
        this.mDestination = destination;
    }

    public Square getOrigin() {
        return mOrigin;
    }

    public Square getDestination() {
        return mDestination;
    }

    public JSONObject toJSon() {
        JSONObject moveObject = new JSONObject();
        moveObject.put("origin", mOrigin.toString());
        moveObject.put("destination", mDestination.toString());
        return moveObject;
    }
}
