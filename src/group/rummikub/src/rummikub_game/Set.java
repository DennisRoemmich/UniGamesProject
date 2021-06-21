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

   /* public boolean isValid(){

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
        
        if(isSmaller(first, second) && sameColor(first, second) || first.isJoker() || second.isJoker()) {
            i = 1;
            while (isRun && j < tiles.size()) {
                isRun = isSmaller(tiles.get(i), tiles.get(j)) && sameColor(tiles.get(i), tiles.get(j)) || tiles.get(i).isJoker() || tiles.get(j).isJoker();
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
*/

    public boolean isValid(){
        if (tiles.size() < 3){
            return false;
        }

        boolean isRun = false;
        boolean isGroup = false;
        Tile first = tiles.get(0);
        Tile second = tiles.get(1);

        return isRun(first,second) || isGroup(first,second);
    }

    public boolean isRun(Tile a, Tile b){
        boolean isRun = true;

        if (a.isJoker() && b.isJoker()){ //first 2 tiles are jokers
            if(tiles.get(2).getValue() <= 2){ //3rd tile must be number, if this number smaller than 3 then not valid
                return false;
            } else if(tiles.get(2).getValue() >= 3){ //if 3rd tile is bigger than 2 then it is valid when size is 3
                return true;
            } else if (smallerAndColor(tiles.get(2), tiles.get(3)) && tiles.get(2).getValue() >= 3){ //3rd tile is smaller than 4th tile and bigger than 2, check if is a run
                int i = 3;
                int j = i+1;
                while(isRun && i < tiles.size()-2){
                    isRun = smallerAndColor(tiles.get(i), tiles.get(j));
                    i++;
                }
            }
        } else if(a.isJoker() && !b.isJoker()){ //first tile is joker second is not
            if(b.getValue() < 2) { //value of second tile smaller than 2 not valid
                return false;
            } else{ //any other case
                int i = 1;
                int j = i+1;
                while(isRun && i < tiles.size()-2){
                    if(tiles.get(j).isJoker()){
                        if(j == tiles.size()-1){
                            isRun = true;
                            i++;
                            continue;
                        }else{
                            isRun = smallerAndColor(tiles.get(i), tiles.get(j+1)) && tiles.get(j+1).getValue() == tiles.get(i).getValue()+2;
                            i++;
                            continue;
                        }
                    }

                    isRun = smallerAndColor(tiles.get(i), tiles.get(j));
                    i++;
                }
            }
        } else if(!a.isJoker()){
            int i = 1;
            int j = i+1;
            while(isRun && i < tiles.size()-2){
                if(tiles.get(i).isJoker() && tiles.get(j).isJoker() && j < tiles.size()-2){
                    isRun = smallerAndColor(tiles.get(i-1), tiles.get(j+1)) && tiles.get(j+1).getValue() == tiles.get(i).getValue()+3;
                    i++;
                    continue;
                } else if(tiles.get(i).isJoker() && tiles.get(j).isJoker() && j < tiles.size()-1){
                    isRun = true;
                    i++;
                    continue;
                }
                if(tiles.get(j).isJoker()){
                    if(j == tiles.size()-1){
                        isRun = true;
                        i++;
                        continue;
                    }else{
                        isRun = smallerAndColor(tiles.get(i), tiles.get(j+1)) && tiles.get(j+1).getValue() == tiles.get(i).getValue()+2;
                        i++;
                        continue;
                    }
                }

                isRun = smallerAndColor(tiles.get(i), tiles.get(j));
                i++;
            }
        } else {
            isRun = false;
        }

        return isRun;
    }

    public boolean isGroup(Tile a, Tile b){
        boolean isGroup = true;
        boolean notEqualValues = true;

        if(a.isJoker() && b.isJoker()){
            return true;
        } else{
            int i = 0;
            int j = i + 1;
            while(notEqualValues && j < tiles.size()-1){
                if(tiles.get(i).isJoker()){
                    i++;
                    continue;
                }else if(tiles.get(j).isJoker()){
                    if(j < tiles.size()-2){
                        notEqualValues = !valueIsEqual(tiles.get(i), tiles.get(j+1));
                        i++;
                        continue;
                    } else {
                        notEqualValues = true;
                        i++;
                        continue;
                    }
                }

                notEqualValues = !valueIsEqual(tiles.get(i), tiles.get(j));
                i++;
            }
        }

        return notEqualValues && noSameColors();
    }
    public boolean sameColor(Tile a, Tile b){return a.getTileColor() == b.getTileColor(); }

    public boolean valueIsEqual(Tile a, Tile b){
        return a.getValue() == b.getValue();
    }

    public boolean isBigger(Tile a, Tile b){
        return a.getValue() > b.getValue();
    }

    public boolean isSmaller(Tile a, Tile b){
        return a.getValue() < b.getValue();
    }

    public boolean smallerAndColor(Tile a, Tile b){
        return isSmaller(a, b) && sameColor(a, b);
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

