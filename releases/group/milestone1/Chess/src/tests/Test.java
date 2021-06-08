package tests;

import org.json.simple.JSONObject;
import sample.Controller;
import java.util.List;

public abstract class Test {
	
	Controller mTestController = new Controller();
	
    protected abstract List<JSONObject> getMoves(); 
    
    public abstract boolean runTest();

    public void runMoves() {
        for (JSONObject move : getMoves()) {
            mTestController.executeMove(move);
        }
    }
}
