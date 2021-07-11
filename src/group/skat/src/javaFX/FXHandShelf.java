package javaFX;

import javaFX.enums.HandShelfPosition;
import javafx.scene.layout.AnchorPane;

public class FXHandShelf {

    private FXController fxController;

    private HandShelfPosition position;

    public AnchorPane anchorHand;
    private FXCard[] handFXCards;

    /* CONSTRUCTOR */

    public FXHandShelf(FXController fxController, HandShelfPosition pos){

        this.fxController = fxController;

        position = pos;

        init();
    }

    /* OTHER */

    public void init(){

        var playersHand = fxController.getPlayer().getHand();
        handFXCards = new FXCard[10];

        for (var i = 0; i < 10; i++) {

            var card = new FXCard(playersHand.getCardAt(i), i, fxController);

            handFXCards[i] = card;

            anchorHand.getChildren().add(card.getAnchorCard());
            AnchorPane.setLeftAnchor(card.getAnchorCard(), i * 70.0);
        }
    }

    public void update(){

        var playersHand = fxController.getPlayer().getHand();

        for (var i = 0; i < handFXCards.length; i++) {

            if (!handFXCards[i].isEqualTo(playersHand.getCardAt(i))) {

                handFXCards[i].changeCard(playersHand.getCardAt(i));
            }
        }
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
