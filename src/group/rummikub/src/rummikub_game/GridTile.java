package rummikub_game;

public class GridTile {

    private boolean isEmpty;
    private Tile tile;
    private int position;

    /**
     * Constructor
     */
    public GridTile(){

        isEmpty = true;
        tile = null;
    }

    /**
     * @return true if empty
     * @return false if not empty
     */
    public boolean isEmpty() {

        return isEmpty;
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
        isEmpty = false;
    }

    /**
     * removes Tile from GridTile
     */
    public void removeTile() {

        tile = null;
        isEmpty = true;
    }

    /**
     * @return position of GridTile
     */
    public int getPosition() {

        return position;
    }

    /**
     * sets position of GridTile
     * @param pos of GridTile
     */
    public void setPosition(int pos) {

        position = pos;
    }

    public String toString() {

        if (tile == null) {

            return "[*]";
        }

        return "[" + tile.color.toString() + "," + Integer.toString(tile.value) + "]";
    }
}
