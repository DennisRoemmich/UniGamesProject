package rummikub_game;

public class RummikubPlayer {

    private Rack tileRack;
    private Rack sketchRack;
    private int score;
    private boolean commingOut;

    /**
     * Constructor
     */
    public RummikubPlayer(){

        tileRack = new Rack();
        sketchRack = new Rack();
        score = 0;
        commingOut = false;
    }

    /**
     * returns sketchRack
     * @return sketchRack
     */
    public Rack getSketchRack() {

        return sketchRack;
    }

    /**
     * returns actual Rack
     * @return tileRack
     */
    public Rack getRack(){

        return tileRack;
    }

    /**
     * @return players score of the current game (only set in the end of the game)
     */
    public int getScore(){

        return score;
    }

    /**
     * sets players score of the current game (only set in the end of the game)
     */
    public void setScore(int score){

        this.score = score;
    }

    public void setCommingOut(boolean b) {

        commingOut = b;
    }

    public boolean getCommingOut() {

        return commingOut;
    }

    /**
     * resets the sketchRack to the previous tileRack
     */
    public void resetSketchRack(){

        for (var i = 0; i < tileRack.getRackSize(); i++) {

            var sourceGridTile = tileRack.getGrid()[i / Rack.GRID_WIDTH][i % Rack.GRID_WIDTH];

            var returnTile = new GridTile();

            if( sourceGridTile.getTile() != null ) {

                returnTile.setTile(new Tile(sourceGridTile.getTile().color, sourceGridTile.getTile().value));

            }

            sketchRack.getGrid()[i / Rack.GRID_WIDTH][i % Rack.GRID_WIDTH] = returnTile;
        }
    }

    /**
     * sets tileRack to new sketchRack
     */
    public void acceptSketchRack(){

        for (var i = 0; i < tileRack.getRackSize(); i++) {


            var sourceGridTile = sketchRack.getGrid()[i / Rack.GRID_WIDTH][i % Rack.GRID_WIDTH];

            var returnTile = new GridTile();

            if( sourceGridTile.getTile() != null ) {

                returnTile.setTile(new Tile(sourceGridTile.getTile().color, sourceGridTile.getTile().value));

            }

            tileRack.getGrid()[i / Rack.GRID_WIDTH][i % Rack.GRID_WIDTH] = returnTile;
        }


    }
}
