package map;

import buildings.Building;
import player.PlayerColor;
import positions.EdgePosition;
import positions.NodePosition;
import positions.PositionedObject;
import positions.TilePosition;
import streets.PositionedStreet;
import streets.Street;
import tiles.PositionedTile;
import tiles.ResourceTile;
import tiles.Tile;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Builds the whole map with all of its tiles, streets and buildings.
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public class Map {
    private TilePosition mBurglarPosition = new TilePosition(0, 0);
    private List<PositionedTile> mTiles = new ArrayList<>();
    private List<Building> mBuildings = new ArrayList<>();
    private List<PositionedStreet> mStreets = new ArrayList<>();

    public Map() {

    }

    public Map(List<PositionedTile> tiles) {
        this.mTiles = tiles;
    }

    public Map(TilePosition burglarPosition, List<PositionedTile> tiles) {
        this.mBurglarPosition = burglarPosition;
        this.mTiles = tiles;
    }

    public TilePosition getBurglarPosition() {
        return mBurglarPosition;
    }

    public void setBurglarPosition(TilePosition burglarPosition) {
        this.mBurglarPosition = burglarPosition;
    }
    
    public List<Building> getBuildings() {
        return mBuildings;
    }

    public List<Building> getBuildings(PlayerColor color) {
        return mBuildings.stream().filter(building -> building.getColor() == color).toList();
    }

    public List<PositionedTile> getTiles() {
        return mTiles;
    }

    public List<PositionedTile> getMaterialTiles() {
        return mTiles.stream().filter(tile -> tile.getObject() instanceof ResourceTile).toList();
    }


    public List<PositionedStreet> getStreets() {
        return mStreets;
    }

    public List<PositionedStreet> getStreets(PlayerColor color) {
        return mStreets.stream().filter(street -> street.getObject().getColor() == color).toList();
    }

    public Optional<Tile> getTile(TilePosition position) {
        return mTiles.stream().filter(tile -> tile.getPosition().equals(position))
        		.map(PositionedObject::getObject).findFirst();
    }

    public Optional<Building> getBuilding(NodePosition position) {
        return mBuildings.stream()
                        .filter(building -> building.getPosition().equals(position)).findFirst();
    }

    public Optional<Street> getStreet(EdgePosition position) {
        return mStreets.stream()
                        .filter(s -> s.getPosition().equals(position))
                        .map(PositionedObject::getObject).findFirst();
    }

    public void addTiles(PositionedTile... newTiles) {
        for (PositionedTile newTile : newTiles) {
            addTile(newTile);
        }
    }

    public void addTile(PositionedTile newTile) {
        mTiles.removeIf(pT -> pT.getPosition().equals(newTile.getPosition()));
        mTiles.add(newTile);
    }

    public void addBuilding(Building... newBuildings) {
        for (Building newBuilding : newBuildings) {
            addBuilding(newBuilding);
        }
    }

    public void addBuilding(Building newBuilding) {
    	NodePosition p = newBuilding.getPosition();
    if (!mBuildings.stream().map(Building::getPosition).collect(Collectors.toList())
    		.contains(p) && MapTools.isPositionValid(this, p)) {
            mBuildings.add(newBuilding);
        }       
    }

    public void addStreets(PositionedStreet... newStreets) {
        for (PositionedStreet newStreet : newStreets) {
            if (MapTools.isPositionValid(this, newStreet.getPosition())) {
                addStreet(newStreet);
            }
        }
    }

    public void addStreet(PositionedStreet newStreet) {
        if (!mStreets.stream().map(PositionedObject::getPosition).toList().contains(newStreet.getPosition())) {
            mStreets.add(newStreet);
        }
    }
}
