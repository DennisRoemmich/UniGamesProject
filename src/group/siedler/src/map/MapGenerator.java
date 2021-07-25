package map;

import buildings.Building;
import buildings.BuildingType;
import player.PlayerColor;
import player.PlayerData;
import positions.*;
import materials.MaterialType;
import siedlerController.Controller;
import streets.PositionedStreet;
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

    public static Map generateKonstanzMap() {
        Map map = new Map();
        int[] waterX = {-1, 1, 3, 3, 7, 0, -8, -7, -6, -5, -4, -3, -2, -1, -1, 0, -1, -1, -1, -2, -1, 0, 0, 1, 2, 2, 2, 3, 4, 5, 8, 8, 8, 8, 7, 7, 6, 7, 6, 5, 4, 5, 4, 3, 8, 8, 2, 1, 0, 1, 0, 1, 2};
        int[] waterY = {4, 4, 2, 3, -2, 3, 1, 1, 0, 0, -1, -1, -1, -1, 0, 0, 1, 2, 3, 4, 5, 4, 5, 5, 4, 3, 5, 4, 2, 1, -3, -2, -1, 0, -1, 0, 1, 1, 2, 2, 3, 3, 4, 5, -5, -4, 2, 3, 1, 1, 2, 2, 1};

        for(int i = 0; i < waterX.length; i++) {
            Tile waterTile = new NeutralTile(true);
            TilePosition position = new TilePosition(waterX[i], waterY[i]);
            map.addTile(new PositionedTile(waterTile, position));
        }

        int cutoff = 17 / 2;

        int cutOffTilesInCorner = 5 * (5 + 1);

        int desertTilesAmount = 5;
        int amountOfTiles = 17 * 11 - cutOffTilesInCorner - desertTilesAmount - waterX.length;

        LinkedList<Tile> tiles = new LinkedList<>(getMaterialTiles(amountOfTiles));

        for(int i = 0; i < desertTilesAmount; i++) {
            tiles.add(new NeutralTile(false));
        }

        Collections.shuffle(tiles);

        for(int x = -8; x <= 8; x++) {
            for(int y = -5; y <= 5; y++) {
                if(Math.abs(x + y) <= cutoff) {
                    TilePosition position = new TilePosition(x, y);
                    if(map.getTile(position).isEmpty()) {
                        Tile tile = tiles.pop();
                        PositionedTile positionedTile = new PositionedTile(tile, position);
                        map.addTile(positionedTile);
                    }
                }
            }
        }
        List<TilePosition> desertPositions = new ArrayList<>(map.getTiles().stream().filter(pT -> !(pT.getObject().isWater() || pT.getObject().isHasHitnumber())).map(pT -> pT.getPosition()).toList());
        Collections.shuffle(desertPositions);
        map.setBurglarPosition(desertPositions.get(0));

        return map;
    }

    public static Map generateVariableMap(int width, int height) {
        Map map = new Map();

        correctWidthAndHeight(width, height);

        int maxX = width / 2;
        int maxY = height / 2;

        int cutOffTilesInCorner = maxY * (maxY + 1);
        int amountOfTiles = width * height - cutOffTilesInCorner;
        LinkedList<Tile> tiles = new LinkedList<>(getTiles(amountOfTiles));

        for(int x = -maxX; x <= maxX; x++) {
            for(int y = -maxY; y <= maxY; y++) {
                if(Math.abs(x + y) <= maxX) {
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
    For a variable map, both height and width have to be odd number and width <= height has to be true
     */
    public static void correctWidthAndHeight(int width, int height) {

        if(width % 2 == 0) {
            width--;
        }

        if(height % 2 == 0) {
            height--;
        }

        if(height > width) {
            height = width;
        }

    }

    /*
    Get an evenly distributed set of Tiles, including desert & water.
    The output is always randomized.
     */
    public static List<Tile> getTiles(int amount) {
        List<Tile> tiles = new ArrayList<>();

        int desertTilesAmount = amount / 15;
        int waterTilesAmount = (amount - 20) / 5;

        for(int i = 0; i < desertTilesAmount + waterTilesAmount; i++) {
            tiles.add(new NeutralTile(i >= desertTilesAmount));
        }

        int materialTilesAmount = amount - desertTilesAmount - waterTilesAmount;
        tiles.addAll(getMaterialTiles(materialTilesAmount));

        Collections.shuffle(tiles);

        return tiles;
    }

    public static List<ResourceTile> getMaterialTiles(int amount) {
        List<ResourceTile> tiles = new ArrayList<>();
        LinkedList<Integer> numbers = getHitnumbers(amount);
        LinkedList<MaterialType> types = getMaterialTypes(amount);

        for(int i = 0; i < amount; i++) {
            tiles.add(new ResourceTile(types.pop(), numbers.pop()));
        }

        return tiles;
    }

    public static LinkedList<Integer> getHitnumbers(int amount) {
        LinkedList<Integer> numbers = new LinkedList<>();
        while(numbers.size() <= amount) {
            for(int i = 1; i <= 5; i++)  {
                numbers.add(7 + i);
                numbers.add(7 - i);
            }
        }
        Collections.shuffle(numbers);
        return numbers;
    }

    public static LinkedList<MaterialType> getMaterialTypes(int amount) {
        LinkedList<MaterialType> types = new LinkedList<>();

        while(types.size() <= amount) {
            types.add(MaterialType.WOOD);
            types.add(MaterialType.WHEAT);
            types.add(MaterialType.WOOL);
            types.add(MaterialType.CLAY);
            types.add(MaterialType.ORE);
        }
        Collections.shuffle(types);
        return types;
    }

}
