package rummikub_controller;

import org.json.simple.JSONObject;

import java.awt.*;

public class GameMove {

    private ActionType mType;

    private Point mPointA;
    private Point mPointB;

    private boolean mToBeRelogged = false;

    public GameMove(ActionType type) {
        this.mType = type;
    }

    public GameMove(ActionType type, Point a, Point b) {

        this.mType = type;
        this.mPointA = a;
        this.mPointB = b;

    }


    public JSONObject toJSON() {

        var obj = new JSONObject();

        obj.put("actionType", mType.toString());

        if ( mType.usesPoints() ) {

            obj.put("PointAx", Integer.toString(mPointA.x) );
            obj.put("PointAy", Integer.toString(mPointA.y));
            obj.put("PointBx", Integer.toString(mPointB.x));
            obj.put("PointBy", Integer.toString(mPointB.y));

        }

        obj.put("toBeRelogged", Boolean.toString(mToBeRelogged));

        return obj;
    }

    public void swapCoordinates() {

        mPointA = new Point(mPointA.y, mPointA.x);
        mPointB = new Point(mPointB.y, mPointB.x);

    }

    public ActionType getType() {
        return this.mType;
    }

    public Point getPointA() {
        return mPointA;
    }

    public Point getPointB() {
        return mPointB;
    }
}
