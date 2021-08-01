package siedler.controller;

import buildings.Building;
import buildings.BuildingType;
import cards.CardSet;
import cards.CardType;
import helper.QuickJSon;
import map.BuildRules;
import map.MapTools;
import materials.MaterialType;

import org.json.simple.JSONObject;
import player.PlayerColor;
import positions.EdgePosition;
import positions.NodePosition;
import positions.TilePosition;
import siedler.framework.Player;
import siedler.framework.PrintToConsole;
import streets.Street;
import streets.StreetType;
import tiles.PositionedTile;

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
            return QuickJSon.createReply("invalid input");
        }
        JSONObject reply = QuickJSon.createReply("valid");
        switch (mController.getState()) {
            case NOT_RUNNING -> setToInvalid(reply);
            case ROLL_DICES -> mController.handleRoll();
            case SETUP_VILLAGE ->  handleSetupVillage();
            case SETUP_STREET -> handleSetupStreet();
            
            //TESTING: Adjust this method with a boolean input for testing the streets
            case OPTIONAL_MOVES -> handleOptionalMoves(); 
            
            case MOVE_BURGLAR -> handleMoveBurglar();
        }
        return reply;
    }

    @SuppressWarnings("unchecked")
	private void setToInvalid(JSONObject reply) {
        reply.put("reply", "invalid");
    }

    private void handleSetupVillage() {
        List<NodePosition> possiblePositions = BuildRules.getStartNodePositions(mController.getMap());
        
        List<NodePosition> filteredPostitions = new ArrayList<>();
        
        for (NodePosition nP : possiblePositions) {
        	List<TilePosition> tilePostitions = Arrays.stream(MapTools.getTilesPositions(nP))
        												.toList();
        	boolean isEmpty =  tilePostitions.stream()
        										.anyMatch(tP -> mController.getMap().getTile(tP).isEmpty());
        	
        	if (isEmpty) {
        		continue;
        	}

        	boolean isWater = tilePostitions.stream()
        			.filter(tP -> mController.getMap().getTile(tP).isPresent())
        			.anyMatch(tP -> mController.getMap().getTile(tP).get().isWater());
        	if (!isWater) {
        		filteredPostitions.add(nP);
        	}
        }
        
        Collections.shuffle(filteredPostitions);
        try {
        	mController.placeBuilding(filteredPostitions.get(0));
        } catch (Exception e) {
        	PrintToConsole.println("ERROR: Please do not choose that many Ai Players! "
        			+ "There is not enough space to place inward villages onto!!!");
        }
    }

    private void handleSetupStreet() {
        List<EdgePosition> possiblePositions = new ArrayList<>
        	(BuildRules.getStartEdgePositions(mController.getMap(), mController.getCurrentPlayerColor()));
        Collections.shuffle(possiblePositions);
        mController.placeStreet(possiblePositions.get(0));
    }

    private void handleOptionalMoves() {
		tryUsingCards();
		
        for (int i = 0; i < 3; i++) {
            var color = mController.getCurrentPlayerColor();
            var possibleVillageSpots =
                    BuildRules.getValidNodePositions(mController.getMap(), color, BuildingType.VILLAGE);
            if (possibleVillageSpots.isEmpty()) {
                tryCreatingStreet(StreetType.ROAD);
            } else {
                tryCreatingBuilding(BuildingType.VILLAGE);
            }
        }
        tryCreatingBuilding(BuildingType.TOWN);
        tryTakingCard();
        tryTrading();
        
        if (GameState.NOT_RUNNING.equals(mController.getState())) {
        	return;
        }
        mController.endMove();
    }
    
    //*TESTING*: This method is for testing how well roads can be placed. 
    @SuppressWarnings("unused")
	private void handleOptionalMoves(boolean isTest) {
	    
    	//*TESTING*: Change the amount the AI places streets per turn 
    	int amountOfStreetsPlaced = 60;
    	
	    for (int i = 0; i < amountOfStreetsPlaced; i++) {
	            tryCreatingStreet(StreetType.ROAD);
	    }
	    if (GameState.NOT_RUNNING.equals(mController.getState())) {
	    	return;
	    }
	    mController.endMove();
    }

    private void handleMoveBurglar() {
        List<PositionedTile> possiblePositions = mController.getMap().getTiles();
        Collections.shuffle(possiblePositions);
        List<TilePosition> bestPositions = new ArrayList<>();
        int maxBlock = 0;
        for (PositionedTile positionedTile : possiblePositions) {
            if (!positionedTile.getObject().isHasHitnumber()) {
                continue;
            }
            int block = getBlockedResources(positionedTile);
            if (block >= maxBlock) {
                if (block > maxBlock) {
                    maxBlock = block;
                    bestPositions.clear();
                }
                bestPositions.add(positionedTile.getPosition());
            }
        }
        Collections.shuffle(bestPositions);
        mController.moveBurglar(bestPositions.get(0));
    }

    private int getBlockedResources(PositionedTile positionedTile) {
        int block = 0;
        var buildingPositions = MapTools.getNodePositions(positionedTile.getPosition());
        for (NodePosition buildingPosition : buildingPositions) {
            var building = mController.getMap().getBuilding(buildingPosition);
            if (building.isPresent()) {
                block += building.get().getType() == BuildingType.VILLAGE ? 1 : 2;
                if (building.get().getColor() == mController.getCurrentPlayerColor()) {
                    block -= 10; //The AI shouldn't block its own tiles
                }
            }
        }
        return block;
    }

    public void tryCreatingBuilding(BuildingType type) {
        if (mController.getCurrentPlayerHand().isSuperset(Building.getCost(type))) {
            PlayerColor color = mController.getCurrentPlayerColor();
            List<NodePosition> possiblePositions = BuildRules.getValidNodePositions(mController.getMap(), color, type);
            if (!possiblePositions.isEmpty()) {
                Collections.shuffle(possiblePositions);
                mController.placeBuilding(possiblePositions.get(0));
            }
        }
    }

    public void tryCreatingStreet(StreetType type) {
       
    	//*TESTING*: Comment this if statement out to test the streets!
    	if (!BuildRules.getValidNodePositions(mController.getMap(), mController.getCurrentPlayerColor(), 
        		BuildingType.VILLAGE).isEmpty()) {
            return;
        }
        if (mController.getCurrentPlayerHand().isSuperset(Street.getCost(type))) {
            PlayerColor color = mController.getCurrentPlayerColor();
            List<EdgePosition> possiblePositions = BuildRules.getValidEdgePositions(mController.getMap(), color, type);
            if (!possiblePositions.isEmpty()) {
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
    	
    	for (MaterialType type  : materialList) {
	    	if (mController.getCurrentPlayerHand().getAmount(type) >= 6) {
	    		for ( MaterialType type2  : materialList) {
	    			if (mController.getCurrentPlayerHand().getAmount(type2) <= 1) {
	    				mController.bankTrade(type2, type);
	    				return;
	    			}
	    		}
	    	}
    	}
    }
    	
    public void tryTakingCard() {
        if (mController.getCurrentPlayerHand().isSuperset(CardSet.getCost())) {
            mController.takeCard();
        }
    }

    @SuppressWarnings("unused")
	private void tryUsingCards() {
        for (CardType cardType : CardType.values()) {
            if (cardType == CardType.VICTORY) {
                continue;
            }
            mController.playCard(cardType);
        }
    }
}
