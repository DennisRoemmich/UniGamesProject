package core;

import core.framework.Player;
import org.json.simple.JSONObject;

public class AiPlayer implements Player {

    private Controller controller;

    public AiPlayer(Controller controller) {
        this.controller = controller;
    }

    @Override
    public JSONObject requestMove(JSONObject inputType) {
        if (!inputType.containsKey("type")) {
            return new JSONObject();
        }
        JSONObject reply = new JSONObject();
        switch (inputType.get("type").toString()) {
            case "rollDice":
                reply.put("reply", true);
                break;
            case "getOptionalMove":
                reply.put("reply", "endMove");
                break;
            case "placeStartVillage":

                break;
            case "placeStartStreet":
                break;
            default:
                reply.put("reply", "unknown type");
        }
        return reply;
    }
}
