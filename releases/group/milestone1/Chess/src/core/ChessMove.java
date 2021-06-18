package core;

import core.positioning.Square;

import java.util.HashMap;

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
        
        HashMap<String, String> rawMoveObject = new HashMap<>();
        rawMoveObject.put("origin", mOrigin.toString());
        rawMoveObject.put("destination", mDestination.toString());
        return new JSONObject(rawMoveObject);
    }
}
