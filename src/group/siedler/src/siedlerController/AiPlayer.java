package siedlerController;

import diceRolling.DiceRolling;
import helper.QuickJSON;
import org.json.simple.JSONObject;
import siedlerFramework.Player;

public class AiPlayer implements Player {

    private Controller controller;

    public AiPlayer(Controller controller) {
        this.controller = controller;
    }

    @Override
    public JSONObject requestMove(JSONObject inputType) {
        if (!inputType.containsKey("move")) {
            return QuickJSON.create("reply", "invalid input");
        }
        switch (inputType.get("move").toString()) {
            case "diceRolling":
                DiceRolling.reRoll();

        }
        return QuickJSON.create("reply", "valid");
    }
}
