package map;

import positions.TilePosition;
import rohstoffe.ResourceType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MapGenerator {

    /*
     Generierung einer zufälligen Karte mit 19 Feldern.
     Die Karte ist ohne Wasser und in der Mitte befindet sich eine Wüste
     */
    public static Map generateBasicMap() {
        Map map = new Map();
        TilePosition desertPosition = new TilePosition(0,0);
        Tile desertTile = new NeutralTile(desertPosition, false);
        map.addTile(desertTile);

        LinkedList<ResourceType> types = getBasicTypes();
        LinkedList<Integer> hitnumbers = getBasicHitnumbers();

        int x, y;
        for(x = -2; x <= 2; x++) {
            for(y = -2; y <= 2; y++) {
                // Die Wüste in der Mitte wird übersprungen
                if(x == 0 && y == 0) {
                    continue;
                }
                // Um den Sinn dahinter zu verstehen muss die Karte visualisiert betrachtet werden
                if(Math.abs(x + y) >= 3) {
                    continue;
                }
                TilePosition position = new TilePosition(x,y);
                ResourceType type = types.pop();
                int hitnumber = hitnumbers.pop();
                ResourceTile tile = new ResourceTile(position, type, hitnumber);
                map.addTile(tile);
            }
        }

        return map;
    }

    /*
    Generierung der Rohstofftypen (in zufälliger Reihenfolge) eines Spielfelds in Standardgröße.
     */
    private static LinkedList<ResourceType> getBasicTypes() {
        LinkedList<ResourceType> resourceTypes = new LinkedList<>();
        for(ResourceType type : ResourceType.values()) {
            int amount = type == ResourceType.CLAY || type == ResourceType.ORE ? 3 : 4;
            for(int i = 0; i < amount; i++) {
                resourceTypes.add(type);
            }
        }
        Collections.shuffle(resourceTypes);
        return resourceTypes;
    }

    /*
    Generierung der Rohstofftypen (in zufälliger Reihenfolge) eines Spielfelds in Standardgröße.
     */
    private static LinkedList<Integer> getBasicHitnumbers() {
        LinkedList<Integer> hitnumbers = new LinkedList<>();
        hitnumbers.add(2);
        hitnumbers.add(12);
        for(int offset = 1; offset <= 4; offset++) {
            hitnumbers.add(7 - offset);
            hitnumbers.add(7 - offset);
            hitnumbers.add(7 + offset);
            hitnumbers.add(7 + offset);
        }
        Collections.shuffle(hitnumbers);
        return hitnumbers;
    }

}
