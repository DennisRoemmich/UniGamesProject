package rummikub_game;

public class RummikubPlayer {

    private Rack tileRack;
    private Rack sketchRack;

    /**
     * Constructor
     */
    public RummikubPlayer(){

        this.tileRack = new Rack();
        this.sketchRack = new Rack();
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
