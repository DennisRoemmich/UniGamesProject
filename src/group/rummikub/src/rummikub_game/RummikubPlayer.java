package rummikub_game;

public class RummikubPlayer {

    private Rack tileRack;
    private Rack sketchRack;


    public RummikubPlayer(){

    }

    public Rack getSketchRack() {
        return sketchRack;
    }

    public Rack getRack(){
        return tileRack;
    }

    public void resetSketchRack(){
        sketchRack = tileRack;
    }

    public void acceptSketchRack(){
        tileRack = sketchRack;
    }


}
