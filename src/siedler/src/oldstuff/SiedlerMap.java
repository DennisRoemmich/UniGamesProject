package oldstuff;

// Kann ignoriert werden
/*public class SiedlerMap {

    private List<TileRow> tileRows = new ArrayList<>();

    public SiedlerMap(List<TileRow> tileRows) {
        this.tileRows = tileRows;
    }

    public Optional<Tile> getTile(OldTilePosition pos) {
        if (pos.getRow() >= 0 && pos.getRow() < tileRows.size()) {
            return tileRows.get(pos.getRow()).getTile(pos.getColumn());
        } else {
            return Optional.empty();
        }
    }

    public Optional<Tile> getNext(Direction direction, OldTilePosition origin) {
        return getTile(origin.getNext(direction));
    }

    public static SiedlerMap getDesertMap() {
        List<TileRow> newTileRows = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            int length;
            int offset = 0;
            if(i <= 2) {
                length = 3 + i;
                offset = 2 - i;
            } else {
                length = 7 - i;
            }
            TileRow newTileRow = new TileRow(offset);
            for(int j = 0; j < length; j++) {
                newTileRow.addTile(new NeutralTile());
            }
            newTileRows.add(newTileRow);
        }
        return new SiedlerMap(newTileRows);
    }

    public String toString() {
        String stringRepresentation = "";
        for(int i = 0; i < tileRows.size(); i++) {
            for(int j = 0; j < i; j++) {
                stringRepresentation += "  ";
            }
            stringRepresentation += tileRows.get(i).toString() + "\n";
        }
        return stringRepresentation;
    }
}*/
