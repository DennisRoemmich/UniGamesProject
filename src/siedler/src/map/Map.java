package map;

import core.OptionalAdder;
import player.PlayerColor;
import positions.EdgePosition;
import positions.NodePosition;
import positions.TilePosition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Map {
    private TilePosition burglarPosition = new TilePosition(0,0);
    private List<Tile> tiles = new ArrayList<>();
    private List<Building> buildings = new ArrayList<>();
    private List<Street> streets = new ArrayList<>();

    public Map() {

    }

    public Map(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public Map(TilePosition burglarPosition, List<Tile> tiles) {
        this.burglarPosition = burglarPosition;
        this.tiles = tiles;
    }

    public TilePosition getBurglarPosition() {
        return burglarPosition;
    }

    public void setBurglarPosition(TilePosition burglarPosition) {
        this.burglarPosition = burglarPosition;
    }

    public List<Building> getBuildings(PlayerColor color) {
        return buildings.stream().filter(building -> building.getColor().equals(color)).toList();
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public List<Street> getStreets() {
        return streets;
    }

    public Optional<Tile> getTile(TilePosition position) {
        return tiles.stream().filter(tile -> tile.getPosition().equals(position)).findFirst();
    }

    public Optional<Building> getBuilding(NodePosition position) {
        return buildings.stream().filter(building -> building.getPosition().equals(position)).findFirst();
    }

    public Optional<Street> getStreet(EdgePosition position) {
        return streets.stream().filter(street -> street.getPosition().equals(position)).findFirst();
    }

    public void addTiles(Tile... newTiles) {
        for(Tile newTile : newTiles) {
            addTile(newTile);
        }
    }

    public void addTile(Tile newTile) {
        if(!tiles.stream().map(Tile::getPosition).toList().contains(newTile.getPosition())) {
            tiles.add(newTile);
        }
    }

    public void addBuilding(Building... newTiles) {
        for(Building newBuilding : newTiles) {
            addBuilding(newBuilding);
        }
    }

    public void addBuilding(Building newBuilding) {
        if(!buildings.stream().map(Building::getPosition).toList().contains(newBuilding.getPosition())) {
            buildings.add(newBuilding);
        }
    }

    public void addStreets(Street... newStreets) {
        for(Street newStreet : newStreets) {
            addStreet(newStreet);
        }
    }

    public void addStreet(Street newStreet) {
        if(!streets.stream().map(Street::getPosition).toList().contains(newStreet.getPosition())) {
            streets.add(newStreet);
        }
    }

    /* MapTool-Funktionalität integriert für geschickteren Zugriff */

    public List<Tile> getTiles(NodePosition nodePosition) {
        List<Tile> tiles = new ArrayList<>();
        for(TilePosition tilePosition : MapTools.getTilesPositions(nodePosition)) {
            OptionalAdder.add(getTile(tilePosition), tiles);
        }
        return tiles;
    }
}
