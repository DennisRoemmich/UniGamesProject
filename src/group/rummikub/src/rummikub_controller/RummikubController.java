package rummikub_controller;

import framework.GameController;
import org.json.simple.JSONObject;

import java.awt.*;

public class RummikubController extends GameController {

    /*

    - Sort group
    - Sort Run
     */

    public boolean makeMove(gamePoint pointA, gamePoint pointB){



        return false;
    }

    @Override
    public void executeMove(JSONObject move) {




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

class gamePoint {

    gameArea area = null;

    Point point;

}

enum gameArea {

    RACK,
    BOARD;

}