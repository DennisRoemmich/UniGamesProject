package diceRolling;

import buildings.Building;
import buildings.BuildingType;
import map.Map;
import map.MapTools;
import tiles.ResourceTile;
import tiles.Tile;
import player.PlayerData;
import positions.NodePosition;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class DiceRolling {

    public static int getNumber() {
    	
    	int dice1 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
    	int dice2 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
       
    	return dice1 + dice2;
    }

    public static void handOutResources(int number, Map map, List<PlayerData> players) {
        List<Tile> tilesWithNumber = map.getTiles().stream().filter(tile -> tile.getHitnumber() == number).toList();
        for(Tile tile : tilesWithNumber) {
            for(NodePosition buildingPosition : MapTools.getNodePositions(tile.getPosition())) {
                Optional<Building> building = map.getBuilding(buildingPosition);
                if(building.isPresent()) {
                    Optional<PlayerData> player = players.stream().filter(p -> p.getColor() == building.get().getColor()).findFirst();
                    if(player.isPresent()) {
                        int amount = building.get().getType() == BuildingType.TOWN ? 2 : 1;
                        ResourceTile resourceTile = (ResourceTile) tile;
                        player.get().getHand().addResources(resourceTile.getResourceType(), amount);
                    }
                }
            }
        }
    }
}
