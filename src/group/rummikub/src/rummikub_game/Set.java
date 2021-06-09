package rummikub_game;

import java.util.ArrayList;

public class Set {

    private ArrayList<Tile> tiles;
    private SetType type;

    // private int lastValue;
    // private TileColor color;

    public Set(){
        tiles = new ArrayList<Tile>();
    }

    public void addTile(Tile tile){
        tiles.add(tile);
    }

    public int getSize(){
        return tiles.size();
    }

    public boolean isValid(){

        if (tiles.size() < 3 ){
            return false;
        }

        boolean isRun = true;
        boolean isGroup = true;
        boolean valid = true;
        Tile first = tiles.get(0);
        Tile second = tiles.get(1);
        int i = 1;
        int j = i + 1;
        
        if(isSmaller(first, second) && sameColor(first, second) || first.isJoker()) {
            i = 1;
            while (isRun && j < tiles.size()) {
                isRun = isSmaller(tiles.get(i), tiles.get(j)) && sameColor(tiles.get(i), tiles.get(j)) || tiles.get(i).isJoker();
                i++;
            }

        } else if(valueIsEqual(first, second) && !sameColor(first, second) || first.isJoker()) {
            i = 1;

            while (isGroup && j < tiles.size()) {
                isGroup = valueIsEqual(tiles.get(i), tiles.get(j)) || tiles.get(i).isJoker();
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
        for(var i = 0; i < tiles.size() - 1; i++){
            for(var j = 1; j < tiles.size(); j++){
                if(tiles.get(i).getTileColor() == TileColor.JOKER){
                    noRep = true;
                } else if(tiles.get(i).getTileColor() != tiles.get(j).getTileColor()){
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

