package rummikub_game;

public class GridTile {

    private Tile mTile;

    /**
     * Constructor
     */
    public GridTile(){

        mTile = null;
    }

    /**
     * @return true if empty, false if not empty
     */
    public boolean isEmpty() {

        return mTile == null;
    }

    /**
     * @return tile of GridTile
     */
    public Tile getTile() {

        return mTile;
    }

    /**
     * sets Tile of GridTile
     * @param tile of GridTile
     */
    public void setTile(Tile tile) {

        this.mTile = tile;
    }

    /**
     * removes Tile from GridTile
     */
    public void removeTile() {

        mTile = null;
    }

    public String toString() {

        return toString(false);

    }

    public String toString(boolean wide) {

        if (mTile == null) {

            if(wide){

                return "[EMPTY]";

            } else {

                return "[*]";

            }

        }

        return mTile.toString(wide);

    }


}
