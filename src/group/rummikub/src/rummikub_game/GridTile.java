package rummikub_game;

public class GridTile {

    private boolean isEmpty;
    private Tile tile;
    private int position;

    public GridTile(){

        this.isEmpty = true;
    }

    public boolean isEmpty() {

        return this.isEmpty;
    }

    public Tile getTile() {

        return this.tile;
    }

    public void setTile(Tile tile) {

        this.tile = tile;
        this.isEmpty = false;
    }

    public void removeTile() {

        this.tile = null;
        this.isEmpty = true;
    }

    public int getPosition() {

        return this.position;
    }

    public void setPosition(int pos) {

        this.position = pos;
    }
}
