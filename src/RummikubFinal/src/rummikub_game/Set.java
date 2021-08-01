package rummikub_game;

import java.util.ArrayList;
import java.util.List;

/**
 * class for sets (on the board)
 */
public class Set {

    private final ArrayList<Tile> tiles;

    /**
     * constructor
     */
    public Set() {

        tiles = new ArrayList<>();
    }

    /**
     * adds a tile to the set
     * @param tile tile
     */
    public void addTile(Tile tile) {

        tiles.add(tile);
    }

    /**
     * @return size of set
     */
    public int getSize() {

        return tiles.size();
    }

    /**
     * @return true if valid, false if not
     */
    public boolean isValid() {

        if (tiles.size() < 3) {

            return false;
        }

        return isRun() || isGroup();
    }

    /**
     * ckecks if a run set is valid
     * @return true if valid, false if not
     */
    public boolean isRun() {

        ArrayList<Tile> check = new ArrayList<>();

        for ( var i = 0; i < tiles.size(); i++) {

            if (!jokerCheck(i, check)) {

                return false;
            }
        }

        for(var c = 0; c < check.size()-1; c++) {
            if (!smallerAndColor(check.get(c), check.get(c+1)) || check.get(c).getValue() > 13 || check.get(c).getValue() < 1 || check.get(c+1).getValue() > 13) {
                return false;
            }
        }

        return sameColors(check);
    }

    /**
     * helper method for validation check
     */
    private boolean jokerCheck(int i, ArrayList<Tile> check) {

        int j = i+1;

        if (tiles.get(i).isJoker()) { //If i is a joker save in check list as supposed value to be.
            if (i == 0) {//if the Joker is at the beginning of the run
                if (!tiles.get(j).isJoker() && tiles.get(j).getValue() > 1) { //if the tile after the joker is not a joker and it's bigger than 1, since 0 does not exist then add to check list as a value less than the next one
                    check.add(new Tile(tiles.get(j).getTileColor(), tiles.get(j).getValue()-1));
                } else if (tiles.get(j).isJoker() && tiles.get(j+1).getValue() > 2) { //if the next tile is a joker then get the next tile from it, and rest 2 to the value of that tile and add it to the check list.
                    check.add(new Tile(tiles.get(j+1).getTileColor(), tiles.get(j+1).getValue()-2));
                } else{
                    return false;
                }
            } else {
                check.add(new Tile(check.get(i-1).getTileColor(), check.get(i-1).getValue()+1));
                //if the joker is somewhere in the middle of the run, then grab the value from the tile before it from the check list, since it could be that it is a joker
            }
        } else {
            check.add(tiles.get(i));
        }
        return true;
    }

    /**
     * checks if a group set is valid
     * @return true if valid, false if not
     */
    public boolean isGroup() {
        ArrayList<Tile> check = new ArrayList<>();
        var jokers = 0;
        for (Tile tile : tiles) {

            if (!tile.isJoker()) {
                check.add(tile);
            } else {
                jokers++;
            }
        }

        if ((jokers + check.size()) > 4) {
            return false;
        }

        for(var j = 0; j < check.size()-1; j++) {
            if (check.get(j).getValue() != check.get(j+1).getValue()) {
                return false;
            }
        }

        return noSameColors(check);
    }

    /**
     * @param a tile
     * @param b tile
     * @return true if tiles have same color, false if not
     */
    public boolean sameColor(Tile a, Tile b) {return a.getTileColor() == b.getTileColor(); }

    public boolean isSmaller(Tile a, Tile b) {

        return a.getValue() == b.getValue()-1;
    }

    public boolean smallerAndColor(Tile a, Tile b) {

        return isSmaller(a, b) && sameColor(a, b);
    }

    public boolean noSameColors(List<Tile> list) {

        for(var i = 0; i < list.size()-1; i++) {
            for(int j = i+1; j < list.size();j++) {
                if (list.get(i).getTileColor() == list.get(j).getTileColor()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean sameColors(List<Tile> list) {

        for(var i = 0; i < list.size()-1; i++) {
            if (list.get(i).getTileColor() != list.get(i+1).getTileColor()) {
                return false;
            }
        }

        return true;
    }
}

