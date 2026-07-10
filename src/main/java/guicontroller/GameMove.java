package guicontroller;

import guiconsole.enums.ConsoleActionType;
import guicontroller.enums.ActionType;
import org.json.simple.JSONObject;

/**
 * This class handles the possible game moves which can be done
 */
public class GameMove {

    protected ActionType mType;
    ConsoleActionType mConsoleType = ConsoleActionType.SKATMOVE;
    boolean isTestMove = false;

    public ActionType getType() {

        return mType;
    }

    public ConsoleActionType getConsoleType() {

        return mConsoleType;
    }

    public JSONObject toJSON() {

        return null;

    }

    public boolean isTestMove() {
        return isTestMove;
    }

    public void testMove() {
        isTestMove = true;
    }

}
