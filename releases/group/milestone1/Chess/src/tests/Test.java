package tests;

import org.json.simple.JSONObject;
import sample.Controller;


import java.util.List;

public abstract class Test {
    protected abstract List<JSONObject> getMoves();
    Controller testController = new Controller();
    public abstract boolean runTest();

    public void runMoves() {
        for(JSONObject move : getMoves()) {
            testController.executeMove(move);
        }
    }
}
