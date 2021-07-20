package map;

import buildings.Building;
import buildings.BuildingType;
import player.PlayerColor;
import player.PlayerData;
import positions.*;
import materials.MaterialType;
import streets.Street;
import streets.StreetType;
import tiles.NeutralTile;
import tiles.PositionedTile;
import tiles.ResourceTile;
import tiles.Tile;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class MapGenerator {

    /*
     Generierung einer zufälligen Karte mit 19 Feldern.
     Die Karte ist ohne Wasser und in der Mitte befindet sich eine Wüste
     */
    public static Map generateBasicMap() {
        Map map = new Map();
        TilePosition desertPosition = new TilePosition(0,0);
        Tile desertTile = new NeutralTile(false);
        PositionedTile positionedTile = new PositionedTile(desertTile, desertPosition);
        map.addTile(positionedTile);

        LinkedList<MaterialType> types = getBasicTypes();
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
                MaterialType type = types.pop();
                int hitnumber = hitnumbers.pop();
                ResourceTile tile = new ResourceTile(type, hitnumber);
                positionedTile = new PositionedTile(tile, position);
                map.addTile(positionedTile);
            }
        }

        return map;
    }

    public static Map generateMap(List<PlayerColor> colors) {
        return generateMap(colors, 5, 5);
    }
    public static Map generateMap(List<PlayerColor> colors, int width, int height) {
        Map map = generateVariableMap(width, height);

        for(PlayerColor color : colors) {
            for(int i = 0; i < 2; i++) {
                NodePosition nodePosition;

                do {
                    nodePosition = MapTools.getRandomNodePosition(map);
                } while (!BuildRules.isNodeValidForNewBuilding(map, nodePosition));


                List<EdgePosition> edgePositions = new ArrayList<>(Arrays.stream(MapTools.getEdgePositions(nodePosition)).toList());
                edgePositions = edgePositions.stream().filter(ep -> MapTools.isPositionValid(map, ep)).toList();
                edgePositions = edgePositions.stream().filter(ep -> map.getStreet(ep).isEmpty()).toList();
                Optional<EdgePosition> edgeOfStreet = edgePositions.stream().findFirst();
                if(edgeOfStreet.isPresent()) {
                    StreetType type = BuildRules.getPossibleStreetType(map, edgeOfStreet.get()).get(0);
                    Street newStreet = new Street(edgeOfStreet.get(), type, color);
                    map.addStreet(newStreet);

                    Building newBuilding = new Building(nodePosition, color);
                    map.addBuilding(newBuilding);
                }
            }
        }

        return map;
    }

    /*
    Generierung der Rohstofftypen (in zufälliger Reihenfolge) eines Spielfelds in Standardgröße.
     */
    private static LinkedList<MaterialType> getBasicTypes() {
        LinkedList<MaterialType> resourceTypes = new LinkedList<>();
        for(MaterialType type : MaterialType.values()) {
            int amount = type == MaterialType.CLAY || type == MaterialType.ORE ? 3 : 4;
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

    public static Map generateVariableMap(int width, int height) {
        Map map = new Map();

        if(width % 2 == 0) {
            width--;
        }

        if(height % 2 == 0) {
            height--;
        }

        if(height > width) {
            height = width;
        }


        int cutoff = height / 2;

        int cutOffTilesInCorner = width/2 * (width/2 + 1);
        int amountOfTiles = width * height - cutOffTilesInCorner;
        LinkedList<Tile> tiles = new LinkedList<>(getTiles(amountOfTiles));

        for(int x = -(width / 2); x <= (width / 2); x++) {
            for(int y = -(height / 2); y <= (height / 2); y++) {
                if(Math.abs(x + y) <= cutoff) {
                    TilePosition position = new TilePosition(x, y);
                    Tile tile = tiles.pop();
                    PositionedTile positionedTile = new PositionedTile(tile, position);
                    map.addTile(positionedTile);
                }
            }
        }


        return map;
    }

    /*
    Get an evenly distributed set of Tiles, including desert & water.
    The output is always randomized.
     */
    public static List<Tile> getTiles(int amount) {
        List<Tile> tiles = new ArrayList<>();

        int desertTilesAmount = amount / 15;
        int waterTilesAmount = (amount - 20) / 5;
        int materialTilesAmount = amount - desertTilesAmount - waterTilesAmount;

        LinkedList<Integer> numbers = new LinkedList<>();
        LinkedList<MaterialType> types = new LinkedList<>();

        while(numbers.size() <= materialTilesAmount) {
            for(int i = 1; i <= 5 && numbers.size() <= materialTilesAmount; i++)  {
                numbers.add(7 + i);
                numbers.add(7 - i);
            }
        }

        while(types.size() <= materialTilesAmount) {
            types.add(MaterialType.WOOD);
            types.add(MaterialType.WHEAT);
            types.add(MaterialType.WOOL);
            types.add(MaterialType.CLAY);
            types.add(MaterialType.ORE);
        }

        Collections.shuffle(types);
        Collections.shuffle(numbers);

        for(int i = 0; i < materialTilesAmount; i++) {
            tiles.add(new ResourceTile(types.pop(), numbers.pop()));
        }

        for(int i = 0; i < desertTilesAmount; i++) {
            tiles.add(new NeutralTile(false));
        }

        for(int i = 0; i < waterTilesAmount; i++) {
            tiles.add(new NeutralTile(true));
        }

        Collections.shuffle(tiles);

        return tiles;
    }

}
