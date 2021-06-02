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
        boolean isRun = true;
        boolean valid = false;
        
        if(isBigger(tiles[0], tiles[1]) && sameColor(tiles[0], tiles[1])){
            int i = 1;
            int j = 2;
            while(isRun && j < tiles.length){
                isRun = isBigger(tiles[i], tiles[j]) && sameColor(tiles[i], tiles[j]);
            }
        }

        return false;
    }

    public boolean sameColor(Tile a, Tile b){
        return a.getTileColor() == b.getTileColor();
    }

    public boolean isBigger(Tile a, Tile b){
        return a.getValue() > b.getValue();
    }
    
    public boolean isSmaller(Tile a, Tile b){
        return a.getValue() < b.getValue();
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

