package siedlerController;

import buildings.Building;
import buildings.BuildingType;
import helper.QuickJSON;
import map.BuildRules;
import org.json.simple.JSONObject;
import player.PlayerColor;
import positions.EdgePosition;
import positions.NodePosition;
import siedlerFramework.Player;
import streets.Street;
import streets.StreetType;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AiPlayer implements Player {

    private Controller controller;

    public AiPlayer(Controller controller) {
        this.controller = controller;
    }

    @Override
    public JSONObject requestMove(JSONObject inputType) {
        if (!inputType.containsKey("type")) {
            return QuickJSON.create("reply", "invalid input");
        }
        switch (inputType.get("type").toString()) {
            case "rollDices":
                controller.handleRoll();
                break;
            case "optionalMove":
                for(int i = 0; i < 3; i++) {
                    tryCreatingBuilding(BuildingType.VILLAGE);
                    tryCreatingBuilding(BuildingType.TOWN);
                    tryCreatingStreet(StreetType.ROAD);
                    tryCreatingStreet(StreetType.SHIP);
                }
                controller.endMove();
                break;
        }
        return QuickJSON.create("reply", "valid");
    }

    public void tryCreatingBuilding(BuildingType type) {
        if(controller.getCurrentPlayerHand().isSuperset(Building.getCost(type))) {
            PlayerColor color = controller.getCurrentPlayerColor();
            List<NodePosition> possiblePositions = BuildRules.getValidNodePositions(controller.getMap(), color, type);
            if (possiblePositions.size() > 0) {
                int index = ThreadLocalRandom.current().nextInt(0, possiblePositions.size());
                controller.placeBuilding(possiblePositions.get(index));
            }
        }
    }

    public void tryCreatingStreet(StreetType type) {
        if(!BuildRules.getValidNodePositions(controller.getMap(), controller.getCurrentPlayerColor(), BuildingType.VILLAGE).isEmpty()) {
            return;
        }
        if(controller.getCurrentPlayerHand().isSuperset(Street.getCost(type))) {
            PlayerColor color = controller.getCurrentPlayerColor();
            List<EdgePosition> possiblePositions = BuildRules.getValidEdgePositions(controller.getMap(), color, type);
            if (possiblePositions.size() > 0) {
                int index = ThreadLocalRandom.current().nextInt(0, possiblePositions.size());
                controller.placeStreet(possiblePositions.get(index), type);
            }
        }
    }
}
