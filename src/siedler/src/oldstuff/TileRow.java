package oldstuff;

import map.Tile;

import java.util.ArrayList;
import java.util.Optional;

public class TileRow {
    private ArrayList<Tile> tiles = new ArrayList<>();
    private int startColumn = 0;

    public TileRow(ArrayList<Tile> tiles, int startColumn) {
        this.tiles = tiles;
        this.startColumn = startColumn;
    }

    public int getStartColumn() {
        return startColumn;
    }

    public TileRow() {

    }

    public TileRow(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    public TileRow(int startColumn) {
        this.startColumn = startColumn;
    }

    public Optional<Tile> getTile(int column) {
        if(column >= startColumn && column < startColumn + tiles.size()) {
            return Optional.ofNullable(tiles.get(column));
        } else {
            return Optional.empty();
        }
    }

    public void addTile(Tile tile) {
        tiles.add(tile);
    }

    public void setTile(Tile tile, int index) {
        if(index < 0 || index >= tiles.size()) return;
        tiles.set(index, tile);
    }


    public String toString() {
        String stringRepresentation = "";
        for(int i = 0; i < startColumn; i++) {
            stringRepresentation += "    ";
        }
        for(Tile tile : tiles) {
            if(tile == null) {
                stringRepresentation += " ";
            } else {
                stringRepresentation += tile.toString().charAt(0);
            }
            stringRepresentation += "   ";
        }
        return stringRepresentation;
    }
}
