package controller;

import console.enums.ConsoleActionType;
import controller.enums.ActionType;
import org.json.simple.JSONObject;

/**
 * This class handles the possible game moves which can be done
 */
public class GameMove {

    protected ActionType mType;
    ConsoleActionType mConsoleType = ConsoleActionType.SKATMOVE;

    public ActionType getType() {

        return mType;
    }

    public ConsoleActionType getConsoleType() {

        return mConsoleType;
    }

    public JSONObject toJSON() {

        return null;

    }

}
