package javaFX;

import controller.SkatMove;
import controller.enums.ActionType;
import javaFX.enums.FXHandShelfPosition;
import javafx.scene.layout.AnchorPane;

public class FXHandShelf {

    private FXController fxController;

    private FXHandShelfPosition position;

    private AnchorPane anchorHand;
    private FXCard[] handFXCards;

    private int selectedCardIndex;

    /* CONSTRUCTOR */

    public FXHandShelf(FXController fxController, FXHandShelfPosition pos){

        this.fxController = fxController;

        position = pos;
        anchorHand = new AnchorPane();

        selectedCardIndex = -1;

        init();
        update();
    }

    /* OTHER */

    public void init(){

        var playersHand = fxController.getPlayer().getHand();
        handFXCards = new FXCard[10];

        for (var i = 0; i < 10; i++) {

            var card = new FXCard(playersHand.getCardAt(i), i, fxController, position);

            handFXCards[i] = card;

            anchorHand.getChildren().add(card.getAnchorCard());
            AnchorPane.setLeftAnchor(card.getAnchorCard(), i * 70.0);
        }
    }

    public void update(){

        var playersHand = fxController.getPlayer().getHand();

        var shiftRight = false;
        for (var i = 0; i < handFXCards.length; i++) {

            if (!handFXCards[i].isEqualTo(playersHand.getCardAt(i))) {

                handFXCards[i].changeCard(playersHand.getCardAt(i));
            }
            if (shiftRight) {

                AnchorPane.setLeftAnchor(handFXCards[i].getAnchorCard(), i * 70.0 + (119.0 - 70.0));
            }
            if (handFXCards[i].isSelected()) {

                shiftRight = true;
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

    /* ACTIONHANDLING */

    public void cardClickedAt(int index) {

        if (selectedCardIndex == -1) {

            selectedCardIndex = index;

        } else if (selectedCardIndex == index || handFXCards[index].isSelected()) {

            selectedCardIndex = -1;

        } else {

            var move = new SkatMove(ActionType.ON_HAND, selectedCardIndex, index);

            if (!fxController.makeMove(move)) {

                selectedCardIndex = index;
                handFXCards[index].setSelected(true);
            }
        }

        update();
    }



}
