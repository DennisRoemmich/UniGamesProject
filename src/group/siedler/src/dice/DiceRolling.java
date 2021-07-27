package dice;

import buildings.Building;
import buildings.BuildingType;
import framework.PrintToConsole;
import map.Map;
import map.MapTools;
import tiles.PositionedTile;
import tiles.ResourceTile;
import player.PlayerData;
import positions.NodePosition;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public final class DiceRolling {
	
    private static int dice1 = 1;
    private static int dice2 = 1;
	
	private DiceRolling() {
		//Not used
	}
	
    public static void reRoll() {

    	dice1 = ThreadLocalRandom.current().nextInt(1, 7);
    	dice2 = ThreadLocalRandom.current().nextInt(1, 7);

        PrintToConsole.println("Dices rolled: " + dice1 + " and " + dice2 + " => " + getSum());

    }

    public static void handOutResources(int number, Map map, List<PlayerData> players) {
       
        for (PositionedTile p : map.getTiles()) {
        	if (!p.getObject().isHasHitnumber() || p.getPosition().equals(map.getBurglarPosition())) {
        		continue;
        	}
            ResourceTile tile = (ResourceTile) p.getObject();
            if (tile.getHitnumber() == number) {
            	handOutResources(map, players, p);
            }            
        }
    }

	private static void handOutResources(Map map, List<PlayerData> players, PositionedTile positionedTile) {
		for (NodePosition buildingPosition : MapTools.getNodePositions(positionedTile.getPosition())) {
		    Optional<Building> building = map.getBuilding(buildingPosition);
		    if (building.isPresent()) {
		    	for (PlayerData data : players) {
		    		if (data.getColor().equals(building.get().getColor())) {
			            int amount = building.get().getType() == BuildingType.TOWN ? 2 : 1;
			            ResourceTile resourceTile = (ResourceTile) positionedTile.getObject();
			            data.getHand().addResources(resourceTile.getResourceType(), amount);
			        }
		    	}      		        
		    }
		}
	}

    public static int getDice1() {
        return dice1;
    }

    public static int getDice2() {
        return dice2;
    }

    public static int getSum() {
        return dice1 + dice2;
    }
}
