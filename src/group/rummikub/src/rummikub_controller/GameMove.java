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

    public GameMove(JSONObject jsn){

        type = ActionType.valueOf((String) jsn.get("actionType"));

        if ( type.usesPoints() ) {

            pointA = new Point(Integer.parseInt(jsn.get("PointAx").toString()),Integer.parseInt(jsn.get("PointAy").toString()));
            pointB = new Point(Integer.parseInt(jsn.get("PointBx").toString()),Integer.parseInt(jsn.get("PointBy").toString()));


        }

    }

    public JSONObject toJSON(){

        var obj = new JSONObject();

        obj.put("actionType", type.toString());

        if ( type.usesPoints() ) {

            obj.put("PointAx", Integer.toString(pointA.x) );
            obj.put("PointAy", Integer.toString(pointA.y));
            obj.put("PointBx", Integer.toString(pointB.x));
            obj.put("PointBy", Integer.toString(pointB.y));

        }

        return obj;

    }

}
