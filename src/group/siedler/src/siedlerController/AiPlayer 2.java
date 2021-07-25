package siedlerController;

import buildings.Building;
import buildings.BuildingType;
import helper.QuickJSON;
import map.BuildRules;
import map.MapTools;
import materials.MaterialType;

import org.json.simple.JSONObject;
import player.PlayerColor;
import positions.EdgePosition;
import positions.NodePosition;
import positions.TilePosition;
import siedlerFramework.Player;
import streets.Street;
import streets.StreetType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        switch (controller.getState()) {
            case ROLL_DICES -> controller.handleRoll();
            case OPTIONAL_MOVES -> {
                for (int i = 0; i < 3; i++) {
                    tryCreatingBuilding(BuildingType.VILLAGE);
                    tryCreatingBuilding(BuildingType.TOWN);
                    tryCreatingStreet(StreetType.ROAD);
                    tryCreatingStreet(StreetType.SHIP);
                    tryTrading();
                }
                controller.endMove();
            }
            case SETUP_VILLAGE -> {
                List<NodePosition> possiblePositions = BuildRules.getStartNodePositions(controller.getMap());
                possiblePositions = possiblePositions.stream().filter(nP -> !Arrays.stream(MapTools.getTilesPositions(nP)).anyMatch(tP -> controller.getMap().getTile(tP).isEmpty())).toList();
                possiblePositions = possiblePositions.stream().filter(nP -> !Arrays.stream(MapTools.getTilesPositions(nP)).anyMatch(tP -> controller.getMap().getTile(tP).get().isWater())).toList();
                possiblePositions = new ArrayList<>(possiblePositions);
                Collections.shuffle(possiblePositions);
                controller.placeBuilding(possiblePositions.get(0));
            }
            case SETUP_STREET -> {
                List<EdgePosition> possiblePositions = new ArrayList<>(BuildRules.getStartEdgePositions(controller.getMap(), controller.getCurrentPlayerColor()));
                Collections.shuffle(possiblePositions);
                controller.placeStreet(possiblePositions.get(0));
            }
            case MOVE_BURGLAR -> {
                List<TilePosition> possiblePositions = new ArrayList<TilePosition>(controller.getMap().getTiles().stream().filter(pT -> !pT.getObject().isWater()).map(pT -> pT.getPosition()).toList());
                Collections.shuffle(possiblePositions);
                controller.moveBurglar(possiblePositions.get(0));
            }
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
    
    public void tryTrading() {
    	
    	List<MaterialType> materialList = new ArrayList<>();
    	materialList.add(MaterialType.WOOD);
    	materialList.add(MaterialType.CLAY);
    	materialList.add(MaterialType.WHEAT);
    	materialList.add(MaterialType.WOOL);
    	materialList.add(MaterialType.ORE);
    	
    	for(MaterialType type  : materialList) {
	    	if(controller.getCurrentPlayerHand().getAmount(type) >= 6) {
	    		for( MaterialType type2  : materialList) {
	    			if(controller.getCurrentPlayerHand().getAmount(type2) <= 1) {
	    				controller.bankTrade(type2, type);
	    				return;
	    			}
	    		}
	    	}
    	}
    }
}
