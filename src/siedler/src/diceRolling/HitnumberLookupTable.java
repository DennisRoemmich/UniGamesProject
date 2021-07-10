package diceRolling;

import map.Building;
import map.Map;
import map.MapTools;
import map.Tile;
import positions.TilePosition;

import java.util.ArrayList;
import java.util.List;

/*
Diese Klasse hilft bei der Suche nach Gebäuden,
die an ein Feld mit einer bestimmten Zahl grenzen.

Auf Basis einer Map kann eine Lookup table erstellt werden,
die dann direkten Zugriff ermöglicht.

 */
public class HitnumberLookupTable {
    List<List<Building>> lookupTable;

    public HitnumberLookupTable() {
        initTable();
    }

    private void initTable() {
        lookupTable = new ArrayList<>();
        for(int i = 2; i <= 12; i++) {
            lookupTable.add(new ArrayList<>());
        }
    }

    public void update(Map map){
        for(Building building : map.getBuildings()) {
            for(TilePosition tilePosition : MapTools.getTilesPositions(building.getPosition())) {
                // TODO : Check if this class is needed
            }
        }
    }

}
