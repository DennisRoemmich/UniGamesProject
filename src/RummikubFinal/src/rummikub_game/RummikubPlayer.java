package rummikub_game;

public class RummikubPlayer {

    private Rack mTileRack;
    private Rack mSketchRack;
    private int mScore;
    private boolean mCommingOut;

    /**
     * Constructor
     */
    public RummikubPlayer() {

        mTileRack = new Rack();
        mSketchRack = new Rack();
        mScore = 0;
        mCommingOut = false;
    }

    /**
     * returns sketchRack
     * @return sketchRack
     */
    public Rack getSketchRack() {

        return mSketchRack;
    }

    /**
     * returns actual Rack
     * @return tileRack
     */
    public Rack getRack() {

        return mTileRack;
    }

    /**
     * @return players score of the current game (only set in the end of the game)
     */
    public int getScore() {

        return mScore;
    }

    /**
     * sets players score of the current game (only set in the end of the game)
     */
    public void setScore(int score) {

        this.mScore = score;
    }

    public void setCommingOut(boolean b) {

        mCommingOut = b;
    }

    public boolean getCommingOut() {

        return mCommingOut;
    }

    /**
     * resets the sketchRack to the previous tileRack
     */
    public void resetSketchRack() {

        for (var i = 0; i < mTileRack.getRackSize(); i++) {

            var sourceGridTile = mTileRack.getGrid()[i / Rack.GRID_WIDTH][i % Rack.GRID_WIDTH];

            var returnTile = new GridTile();

            if ( sourceGridTile.getTile() != null ) {

                returnTile.setTile(new Tile(sourceGridTile.getTile().mColor, sourceGridTile.getTile().mValue));

            }

            mSketchRack.getGrid()[i / Rack.GRID_WIDTH][i % Rack.GRID_WIDTH] = returnTile;
        }
    }

    /**
     * sets tileRack to new sketchRack
     */
    public void acceptSketchRack() {

        for (var i = 0; i < mTileRack.getRackSize(); i++) {


            var sourceGridTile = mSketchRack.getGrid()[i / Rack.GRID_WIDTH][i % Rack.GRID_WIDTH];

            var returnTile = new GridTile();

            if ( sourceGridTile.getTile() != null ) {

                returnTile.setTile(new Tile(sourceGridTile.getTile().mColor, sourceGridTile.getTile().mValue));

            }

            mTileRack.getGrid()[i / Rack.GRID_WIDTH][i % Rack.GRID_WIDTH] = returnTile;
        }


    }
}
