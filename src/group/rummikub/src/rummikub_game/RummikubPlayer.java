package rummikub_game;

public class RummikubPlayer {

    private Rack tileRack;
    private Rack sketchRack;

    /**
     * Constructor
     */
    public RummikubPlayer(){

        tileRack = new Rack();
        sketchRack = new Rack();
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
     * resets the sketchRack to the previous tileRack
     */
    public void resetSketchRack(){

        sketchRack = tileRack;
    }

    /**
     * sets tileRack to new sketchRack
     */
    public void acceptSketchRack(){

        tileRack = sketchRack;
    }
}
