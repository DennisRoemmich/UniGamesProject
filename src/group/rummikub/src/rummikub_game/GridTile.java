package rummikub_game;

public class GridTile {

    private boolean isEmpty;
    private Tile tile;

    public GridTile(){
        isEmpty = true;
    }

    public boolean isEmpty(){
        return isEmpty;
    }

    public Tile getTile(){
        return tile;
    }

    public void setTile(Tile tile){
    }


}
