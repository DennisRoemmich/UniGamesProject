package tests;

import org.json.simple.JSONObject;
import sample.Controller;
import sample.Player;

import java.util.List;

public abstract class Test {
    private int currentIndex = 0;
    protected abstract List<JSONObject> getMoves();
    Controller testController = new Controller();
    public abstract boolean runTest();

    public void runMoves() {
        for(JSONObject move : getMoves()) {
            testController.executeMove(move);
        }
    }
}
