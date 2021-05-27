package rummikub_game;

public class Set {

    private Tile[] tiles;
    private SetType type;

    // private int lastValue;
    // private TileColor color;

    public Set(Tile[] tiles){
        this.tiles = tiles;
    }

    public boolean isValid(){
        return false;
    }

    public SetType getType(){
        return this.type;
    }

    private enum SetType {
        GROUP,
        RUN,
        INVALID
    }

}

