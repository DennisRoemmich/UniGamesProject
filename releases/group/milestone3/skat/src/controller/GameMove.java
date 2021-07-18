package controller;

import console.enums.ConsoleActionType;
import controller.enums.ActionType;
import org.json.simple.JSONObject;

public class GameMove {

    protected ActionType type;
    ConsoleActionType consoleType = ConsoleActionType.SKATMOVE;

    public ActionType getType() {
        return type;
    }

    public ConsoleActionType getConsoleType() {
        return consoleType;
    }

    public JSONObject toJSON(){


        return null;

    }


}
