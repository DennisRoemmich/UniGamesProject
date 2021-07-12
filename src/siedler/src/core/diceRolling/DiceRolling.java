package core.diceRolling;

import core.map.Map;
import core.map.MapTools;
import core.map.Tile;
import core.player.SiedlerPlayer;
import core.positions.NodePosition;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DiceRolling {

    public static int getNumber() {
    	
    	int dice1 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
    	int dice2 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
       
    	return dice1 + dice2;
    }

    public static void handOutResources(int number, Map map, List<SiedlerPlayer> players) {
        List<Tile> tilesWithNumber = map.getTiles().stream().filter(tile -> tile.getHitnumber() == number).toList();
        for(Tile tile : tilesWithNumber) {
            for(NodePosition buildingPosition : MapTools.getNodePositions(tile.getPosition())) {
                if(!map.getBuilding(buildingPosition).isEmpty()) {

                }
            }
        }
    }
}
