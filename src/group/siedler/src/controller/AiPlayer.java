package controller;

import buildings.Building;
import buildings.BuildingType;
import helper.QuickJSon;
import map.BuildRules;
import map.MapTools;
import materials.MaterialType;

import org.json.simple.JSONObject;
import player.PlayerColor;
import positions.EdgePosition;
import positions.NodePosition;
import positions.TilePosition;
import framework.Player;
import streets.Street;
import streets.StreetType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AiPlayer implements Player {

    private Controller mController;

    public AiPlayer(Controller controller) {
        this.mController = controller;
    }

    @Override
    public JSONObject requestMove(JSONObject inputType) {
        if (!inputType.containsKey("type")) {
            return QuickJSon.create("reply", "invalid input");
        }
        switch (mController.getState()) {
            case ROLL_DICES -> mController.handleRoll();
            case OPTIONAL_MOVES -> {
                for (int i = 0; i < 3; i++) {
                    tryCreatingBuilding(BuildingType.VILLAGE);
                    tryCreatingBuilding(BuildingType.TOWN);
                    if(mController.getState() != GameState.NOT_RUNNING)
                    	break;
                    tryCreatingStreet(StreetType.ROAD);
                    tryCreatingStreet(StreetType.SHIP);
                    tryTrading();
                }
                mController.endMove();
            }
            case SETUP_VILLAGE -> {
                List<NodePosition> possiblePositions = BuildRules.getStartNodePositions(mController.getMap());
                possiblePositions = possiblePositions.stream().filter(nP -> !Arrays.stream(MapTools.getTilesPositions(nP)).anyMatch(tP -> mController.getMap().getTile(tP).isEmpty())).toList();
                possiblePositions = possiblePositions.stream().filter(nP -> !Arrays.stream(MapTools.getTilesPositions(nP)).anyMatch(tP -> mController.getMap().getTile(tP).get().isWater())).toList();
                possiblePositions = new ArrayList<>(possiblePositions);
                Collections.shuffle(possiblePositions);
                mController.placeBuilding(possiblePositions.get(0));
            }
            case SETUP_STREET -> {
                List<EdgePosition> possiblePositions = new ArrayList<>(BuildRules.getStartEdgePositions(mController.getMap(), mController.getCurrentPlayerColor()));
                Collections.shuffle(possiblePositions);
                mController.placeStreet(possiblePositions.get(0));
            }
            case MOVE_BURGLAR -> {
                List<TilePosition> possiblePositions = new ArrayList<TilePosition>(mController.getMap().getTiles().stream().filter(pT -> !pT.getObject().isWater()).map(pT -> pT.getPosition()).toList());
                Collections.shuffle(possiblePositions);
                mController.moveBurglar(possiblePositions.get(0));
            }
        }
        return QuickJSon.create("reply", "valid");
    }

    public void tryCreatingBuilding(BuildingType type) {
        if(mController.getCurrentPlayerHand().isSuperset(Building.getCost(type))) {
            PlayerColor color = mController.getCurrentPlayerColor();
            List<NodePosition> possiblePositions = BuildRules.getValidNodePositions(mController.getMap(), color, type);
            if (possiblePositions.size() > 0) {
                int index = ThreadLocalRandom.current().nextInt(0, possiblePositions.size());
                mController.placeBuilding(possiblePositions.get(index));
            }
        }
    }

    public void tryCreatingStreet(StreetType type) {
        if(!BuildRules.getValidNodePositions(mController.getMap(), mController.getCurrentPlayerColor(), BuildingType.VILLAGE).isEmpty()) {
            return;
        }
        if(mController.getCurrentPlayerHand().isSuperset(Street.getCost(type))) {
            PlayerColor color = mController.getCurrentPlayerColor();
            List<EdgePosition> possiblePositions = BuildRules.getValidEdgePositions(mController.getMap(), color, type);
            if (possiblePositions.size() > 0) {
                int index = ThreadLocalRandom.current().nextInt(0, possiblePositions.size());
                mController.placeStreet(possiblePositions.get(index), type);
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
	    	if(mController.getCurrentPlayerHand().getAmount(type) >= 6) {
	    		for( MaterialType type2  : materialList) {
	    			if(mController.getCurrentPlayerHand().getAmount(type2) <= 1) {
	    				mController.bankTrade(type2, type);
	    				return;
	    			}
	    		}
	    	}
    	}
    }
    	
        public void tryTakingCard() {
        		mController.takeCard();
        }
    
}
