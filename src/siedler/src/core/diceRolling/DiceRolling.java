package core.diceRolling;

import core.map.Map;
import core.map.MapTools;
import core.map.Tile;
import core.player.SiedlerPlayer;
import core.positions.NodePosition;

import java.util.List;

public class DiceRolling {

    public static int getNumber() {
        // TODO : Zahl von 2-12 Generieren, auf Wahrscheinlichkeitsverteilung achten
        return 2;
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
