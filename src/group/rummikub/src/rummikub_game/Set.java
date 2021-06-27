package rummikub_game;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Set {

    private ArrayList<Tile> tiles;
    private SetType type;

    // private int lastValue;
    // private TileColor color;

    public Set() {

        tiles = new ArrayList<>();
    }

    public void addTile(Tile tile) {

        tiles.add(tile);
    }

    public int getSize() {

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

    public boolean isValid() {

        if (tiles.size() < 3) {

            return false;
        }

        return isRun() || isGroup();
    }

    public boolean isRun(){
        ArrayList<Tile> check = new ArrayList<>();
        for(int i = 0; i < tiles.size()-1; i++){
            int j = i+1;

            if(tiles.get(i).isJoker()){ //If i is a joker save in check list as supposed value to be.
                if(i == 0){//if the Joker is at the beginning of the run
                    if(!tiles.get(j).isJoker() && tiles.get(j).getValue() > 1){ //if the tile after the joker is not a joker and it's bigger than 1, since 0 does not exist then add to check list as a value less than the next one
                        check.add(new Tile(tiles.get(j).getTileColor(), tiles.get(j).getValue()-1));
                    } else if(tiles.get(j).isJoker() && tiles.get(j+1).getValue() > 2){ //if the next tile is a joker then get the next tile from it, and rest 2 to the value of that tile and add it to the check list.
                        check.add(new Tile(tiles.get(j+1).getTileColor(), tiles.get(j+1).getValue()-2));
                    } else{
                        return false;
                    }
                } else {
                    check.add(new Tile(check.get(i-1).getTileColor(), check.get(i-1).getValue()+1)); //if the joker is somewhere in the middle of the run, then grab the value from the tile before it from the check list, since it could be that it is a joker
                }
            } else {
                check.add(tiles.get(i));
            }
        }

        for(int c = 0; c < check.size()-1; c++){
            if(!smallerAndColor(check.get(c), check.get(c+1)) || check.get(c).getValue() > 13 || check.get(c).getValue() < 1 || check.get(c+1).getValue() > 13){
                return false;
            }
        }

        return sameColors(check);
    }

    public boolean isGroup(){
        ArrayList<Tile> check = new ArrayList<>();
        int jokers = 0;
        for(int i = 0; i < tiles.size(); i++){

            if(!tiles.get(i).isJoker()){
                check.add(tiles.get(i));
            } else {
                jokers++;
            }
        }

        if((jokers + check.size()) > 4){
            return false;
        }

        for(int j = 0; j < check.size()-1; j++){
            if(check.get(j).getValue() != check.get(j+1).getValue()){
                return false;
            }
        }

        return noSameColors(check);
    }
/*
    public boolean isRun(Tile a, Tile b) {

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
*/ /*
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
*/
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

    public boolean noSameColors(ArrayList<Tile> list){
        for(int i = 0; i < list.size()-1; i++){
            for(int j = i+1; j < list.size();j++){
                if(list.get(i).getTileColor() == list.get(j).getTileColor()){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean sameColors(ArrayList<Tile> list){
        for(int i = 0; i < list.size()-1; i++){
            if(list.get(i).getTileColor() != list.get(i+1).getTileColor()){
                return false;
            }
        }

        return true;
    }

    public SetType getType() {

        return this.type;
    }

    private enum SetType {
        GROUP,
        RUN,
        INVALID
    }

}

