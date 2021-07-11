package javaFX;

import javaFX.enums.HandShelfPosition;

public class FXHandShelf {

    private FXController fxController;

    private HandShelfPosition position;

    public FXHandShelf(FXController fxController, HandShelfPosition pos){

        this.fxController = fxController;

        position = pos;
    }

    public void init(){

        /**/

    }

    public void update(){

    }

    public void expand(int atIndex){

    }

    /* DRAG, DROP, CLICK*/

    public boolean startDrag(int atIndex){

        return false;
    }

    public boolean endDrag(int atIndex){

        return false;
    }

    public void dragOver(int atIndex){

        // Gui interne Vorschau des drags
    }

    /** For Skat an Play*/
    public void endDrag(){

    }


    public boolean click(int atIndex){

        return false;
    }


}
