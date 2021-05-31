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
     * @return players score of the current game (only set in the end of the game)
     */
    public void setScore(int score){

        this.score = score;

    }

    /**
     * resets the sketchRack to the previous tileRack
     */
    public void resetSketchRack(){

        this.sketchRack = this.tileRack;
    }

    /**
     * sets tileRack to new sketchRack
     */
    public void acceptSketchRack(){

        this.tileRack = this.sketchRack;
    }
}
