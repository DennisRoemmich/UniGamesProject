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
        boolean isGroup = true;
        boolean valid = true;
        Tile first = tiles[0];
        Tile second = tiles[1];
        int i = 1;
        int j = i + 1;
        
        if(isSmaller(first, second) && sameColor(first, second)){
            i = 1;
            while(isRun && j < tiles.length){
                isRun = isSmaller(tiles[i], tiles[j]) && sameColor(tiles[i], tiles[j]);
                i++;
            }
        } else if(valueIsEqual(first, second) && !sameColor(first, second)) {
            i = 1;

            while (isGroup && j < tiles.length) {
                isGroup = valueIsEqual(tiles[i], tiles[j]);
            }
            isGroup = isGroup && noSameColors();

        } else {

            valid = false;
        }

        return (isRun || isGroup) && valid;
    }

    public boolean sameColor(Tile a, Tile b){
        return a.getTileColor() == b.getTileColor();
    }

    public boolean valueIsEqual(Tile a, Tile b){
        return a.getValue() == b.getValue();
    }

    public boolean isBigger(Tile a, Tile b){
        return a.getValue() > b.getValue();
    }

    public boolean isSmaller(Tile a, Tile b){
        return a.getValue() < b.getValue();
    }

    public boolean noSameColors(){
        boolean noRep = true;
        for(int i = 0; i < tiles.length - 1; i++){
            for(int j = 1; j < tiles.length; j++){
                if(tiles[i].getTileColor() == TileColor.JOKER){
                    noRep = true;
                } else if(tiles[i].getTileColor() != tiles[j].getTileColor()){
                    noRep = true;
                } else {
                    noRep = false;
                    break;
                }
            }
        }
        return noRep;
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

