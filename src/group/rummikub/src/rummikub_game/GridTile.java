package rummikub_game;

public class GridTile {

    private boolean isEmpty;
    private Tile tile;
    private int position;

    /**
     * Constructor
     */
    public GridTile(){

        this.isEmpty = true;
    }

    /**
     * @return true if empty
     * @return false if not empty
     */
    public boolean isEmpty() {

        return this.isEmpty;
    }

    /**
     * @return tile of GridTile
     */
    public Tile getTile() {

        return this.tile;
    }

    /**
     * sets Tile of GridTile
     * @param tile of GridTile
     */
    public void setTile(Tile tile) {

        this.tile = tile;
        this.isEmpty = false;
    }

    /**
     * removes Tile from GridTile
     */
    public void removeTile() {

        this.tile = null;
        this.isEmpty = true;
    }

    /**
     * @return position of GridTile
     */
    public int getPosition() {

        return this.position;
    }

    /**
     * sets position of GridTile
     * @param pos of GridTile
     */
    public void setPosition(int pos) {

        this.position = pos;
    }

    public String toString(){
        if(this.tile == null){
            return "[*]";
        }
        return "[" + tile.color.toString() + "," + Integer.toString(tile.value) + "]";
    }

}
