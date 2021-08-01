package rummikub_controller;

import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;

public class GameMove {

    private ActionType type;

    private Point pointA;
    private Point pointB;

    private boolean toBeRelogged = false;

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

        toBeRelogged = Boolean.parseBoolean((String) jsn.get("toBeRelogged"));

        if ( type.usesPoints() ) {

            pointA = new Point(Integer.parseInt(jsn.get("PointAx").toString()),Integer.parseInt(jsn.get("PointAy").toString()));
            pointB = new Point(Integer.parseInt(jsn.get("PointBx").toString()),Integer.parseInt(jsn.get("PointBy").toString()));

            if(pointA == null ||pointB == null){

                System.out.println("Break3: " + type.toString());

            }

        } else {

                System.out.println("Break5: " + type.toString());


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

        obj.put("toBeRelogged", Boolean.toString(toBeRelogged));

        return obj;
    }

    public void swapCoordinates(){

        pointA = new Point(pointA.y, pointA.x);
        pointB = new Point(pointB.y, pointB.x);

    }

    public ActionType getType() {
        return this.type;
    }

    public Point getPointA() {
        return pointA;
    }

    public Point getPointB() {
        return pointB;
    }
}
