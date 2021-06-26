package tests;

import org.json.simple.JSONObject;
import console.Controller;
import java.util.List;

/**
 * Runs the test controller.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public abstract class Test {
	
	Controller mTestController = new Controller();
	
    protected abstract List<JSONObject> getMoves(); 
    
    public abstract boolean runTest();

    public void runMoves() {
        /*for (JSONObject move : getMoves()) {
            mTestController.executeMove(move);
        }*/
    }
}
