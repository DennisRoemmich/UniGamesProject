package javaFX;

import console.Print;
import controller.SkatMove;
import controller.enums.ActionType;
import javaFX.enums.FXHandShelfPosition;
import javafx.scene.layout.AnchorPane;

public class FXHandShelf {

    private FXController fxController;

    private FXHandShelfPosition position;

    private FXCard[] handFXCards;

    private int selectedCardIndex;

    /* CONSTRUCTOR */

    public FXHandShelf(AnchorPane pane, FXController fxController, FXHandShelfPosition pos){

        this.fxController = fxController;

        position = pos;

        selectedCardIndex = -1;

        init(pane);
        update();
    }

    /* OTHER */

    public void init(AnchorPane pane){

        handFXCards = new FXCard[10];

        for (var i = 0; i < 10; i++) {

            var card = new FXCard(i, fxController, position);

            handFXCards[i] = card;

            pane.getChildren().add(card.getAnchorCard());
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
            Print.debug("maik", "open: " + handFXCards[i].isOpen());
            Print.debug("maik", "empty: " + handFXCards[i].isEmpty());
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

    /* OTHER */

    public int getSelectedCardIndex() {

        return selectedCardIndex;
    }

    public void setSelectedCardIndex(int index) {

        selectedCardIndex = index;
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

            } else {

                handFXCards[selectedCardIndex].setSelected(false);
                selectedCardIndex = -1;
            }
        }

        update();
    }



}
