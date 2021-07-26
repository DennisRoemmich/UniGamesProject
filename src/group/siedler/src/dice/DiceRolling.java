package dice;

import buildings.Building;
import buildings.BuildingType;
import map.Map;
import map.MapTools;
import siedlerFramework.PrintToConsole;
import tiles.PositionedTile;
import tiles.ResourceTile;
import player.PlayerData;
import positions.NodePosition;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class DiceRolling {
	
    public static int dice1 = 1;
    public static int dice2 = 1;
	
	private DiceRolling() {
		//Not used
	}



    public static void reRoll() {

    	dice1 = ThreadLocalRandom.current().nextInt(1, 7);
    	dice2 = ThreadLocalRandom.current().nextInt(1, 7);

        PrintToConsole.println("Dices rolled: " + dice1 + " and " + dice2 + " => " + getSum());

    }

    public static void handOutResources(int number, Map map, List<PlayerData> players) {
        var tilesWithNumber = map.getMaterialTiles().stream().filter(t -> (((ResourceTile)t.getObject()).getHitnumber() == number)).toList();
        for (PositionedTile positionedTile : tilesWithNumber) {
            if (positionedTile.getPosition().equals(map.getBurglarPosition())) {
                continue;
            }
            for (NodePosition buildingPosition : MapTools.getNodePositions(positionedTile.getPosition())) {
                Optional<Building> building = map.getBuilding(buildingPosition);
                if (building.isPresent()) {
                    Optional<PlayerData> player = players.stream().filter(p -> p.getColor().equals(building.get().getColor())).findFirst();
                    if (player.isPresent()) {
                        int amount = building.get().getType() == BuildingType.TOWN ? 2 : 1;
                        ResourceTile resourceTile = (ResourceTile) positionedTile.getObject();
                        player.get().getHand().addResources(resourceTile.getResourceType(), amount);
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
