package rummikub_controller;

import org.json.simple.JSONObject;

import java.awt.*;

public class GameMove {

    ActionType type;

    Point pointA;
    Point pointB;

    public GameMove(ActionType type) {
        this.type = type;
    }

    public GameMove(ActionType type, Point a, Point b) {

        this.type = type;
        this.pointA = a;
        this.pointB = b;

    }

    public JSONObject toJSON(){

        var obj = new JSONObject();

        obj.put("actionType", type.toString());

        if ( type.usesPoints() ) {

            obj.put("PointAx", pointA.toString());
            obj.put("PointAy", pointA.toString());
            obj.put("PointB", pointB.toString());

        }

        return obj;

    }

}
