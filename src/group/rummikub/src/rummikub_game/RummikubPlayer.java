package rummikub_game;

public class RummikubPlayer {

    private Rack tileRack;
    private Rack sketchRack;
    private int score;

    /**
     * Constructor
     */
    public RummikubPlayer(){

        tileRack = new Rack();
        sketchRack = new Rack();
        score = 0;
    }

    /**
     * returns sketchRack
     * @return sketchRack
     */
    public Rack getSketchRack() {

        return this.sketchRack;
    }

    /**
     * returns actual Rack
     * @return tileRack
     */
    public Rack getRack(){

        return this.tileRack;
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

    /**
     * resets the sketchRack to the previous tileRack
     */
    public void resetSketchRack(){

        System.arraycopy(tileRack.getGrid(), 0, sketchRack.getGrid(), 0, sketchRack.getSize());

    }

    /**
     * sets tileRack to new sketchRack
     */
    public void acceptSketchRack(){

        System.arraycopy(sketchRack.getGrid(), 0, tileRack.getGrid(), 0, sketchRack.getSize());

    }
}
