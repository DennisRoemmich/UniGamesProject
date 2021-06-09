package rummikub_controller;

import framework.GameController;
import framework.GameLog;
import org.json.simple.JSONObject;
import rummikub_game.Rummikub;

import java.awt.*;

public class RummikubController extends GameController {

    private GameState state = GameState.STARTED;

    private final Rummikub rummiGame = new Rummikub(4, 0);

    RummikubController(){

    }

    public boolean makeMove(gameMove move){

        /* CHECK IF MOVE IS VALID */

        var successful = false;

        switch (move.type){

            case FINISHMOVE -> successful = rummiGame.finishMove();

            case ONRACK -> successful = rummiGame.getCurrentPlayer().getSketchRack().moveTile(move.pointA, move.pointB);

            case ONBOARD -> successful = rummiGame.getBoard().moveTile(move.pointA, move.pointB);

            case RACKTOBOARD -> successful = rummiGame.moveTileFromCurrentRackToBoard(move.pointA, move.pointB);

            case SORTGROUP -> {

                if ( state == GameState.RUNNING ) {

                    rummiGame.getCurrentPlayer().getSketchRack().sortForGroup();
                    successful = true;

                }

            }

            case SORTRUN -> rummiGame.getCurrentPlayer().getSketchRack().sortForRun();

            case RESET -> {

                rummiGame.resetMove();

            }

        }

        /* CREATE AND SAVE JSON OBJECT */

        if ( successful ) {

            moveLog.logMove(moveToJSON(move));

        }

        return successful;

    }

    private JSONObject moveToJSON(gameMove move){

        var obj = new JSONObject();

        obj.put("actionType", move.type.toString());

        if ( move.type.usesPoints() ) {

            obj.put("PointAx", move.pointA.toString());
            obj.put("PointAy", move.pointA.toString());
            obj.put("PointB", move.pointB.toString());

        }

        return obj;

    }

    @Override
    public void executeMove(JSONObject obj) {

        ActionType type = ActionType.valueOf((String) obj.get("actionType"));

        if (type.usesPoints()) {



        }


    }

    @Override
    public void resetGame() {

    }

    @Override
    public void addAIPlayer() {

    }

    @Override
    public JSONObject metaSettingsToJSON() {
        return null;
    }

    @Override
    public JSONObject gameSettingsToJSON() {
        return null;
    }

}

class gameMove {

    ActionType type;

    Point pointA;
    Point pointB;

    gameMove(ActionType type){
        this.type = type;
    }

    gameMove(ActionType type, Point a, Point b){

        this.type = type;
        this.pointA = a;
        this.pointB = b;

    }

}

enum GameState {
    STARTED,
    RUNNING,
    ENDED;
}

enum ActionType {

    FINISHMOVE(0),
    ONRACK(1),
    ONBOARD(2),
    RACKTOBOARD(3),
    SORTGROUP(4),
    SORTRUN(5),
    RESET(6);

    int value;

    ActionType(int value){
        this.value = value;
    }

    boolean usesPoints(){
        return (value == 1 || value == 2 ||value == 3);
    }

}