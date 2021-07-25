package rummikub_game;

public class GridTile {

    private Tile tile;

    /**
     * Constructor
     */
    public GridTile(){

        tile = null;
    }

    /**
     * @return true if empty, false if not empty
     */
    public boolean isEmpty() {

        return tile == null;
    }

    /**
     * @return tile of GridTile
     */
    public Tile getTile() {

        return tile;
    }

    /**
     * sets Tile of GridTile
     * @param tile of GridTile
     */
    public void setTile(Tile tile) {

        this.tile = tile;
    }

    /**
     * removes Tile from GridTile
     */
    public void removeTile() {

        tile = null;
    }

    public String toString() {

        return toString(false);

    }

    public String toString(boolean wide) {

        if (tile == null) {

            if(wide){

                return "[EMPTY]";

            } else {

                return "[*]";

            }

        }

        return tile.toString(wide);

    }


}
