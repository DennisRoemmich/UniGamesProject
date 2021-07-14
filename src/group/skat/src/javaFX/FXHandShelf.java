package javaFX;

import console.Print;
import controller.SkatMove;
import controller.enums.ActionType;
import engine.Card;
import engine.Hand;
import javaFX.enums.FXHandShelfPosition;
import javafx.scene.layout.AnchorPane;

public class FXHandShelf {

    private FXController fxController;

    private FXHandShelfPosition position;

    private FXCard[] handFXCards;

    private int selectedCardIndex;

    /* CONSTRUCTOR */

    public FXHandShelf(AnchorPane pane, FXController fxController, FXHandShelfPosition pos) {

        this.fxController = fxController;

        position = pos;

        selectedCardIndex = -1;

        init(pane);
        update();
    }

    /* GETTER */

    public FXCard getFXCardAt(int index) {

        return handFXCards[index];
    }

    public FXCard[] getHandFXCards() {

        return handFXCards;
    }

    /* OTHER */

    public void init(AnchorPane pane) {

        handFXCards = new FXCard[10];

        for (var i = 0; i < 10; i++) {

            var card = new FXCard(i, fxController, position);

            handFXCards[i] = card;

            pane.getChildren().add(card.getAnchorCard());

            if (position == FXHandShelfPosition.MID_PLAYER) {

                AnchorPane.setLeftAnchor(card.getAnchorCard(), i * 70.0);

            } else {

                // TODO: das stimmt noch nich ganz, muss noch geändert werden - andere anker vlt?
                AnchorPane.setLeftAnchor(card.getAnchorCard(), i * 45.0);
            }
        }
    }

    public void update(){

        var curIndex = fxController.getPlayerGameIndex();
        Hand playersHand;

        if (position == FXHandShelfPosition.LEFT_PLAYER) {

            playersHand = fxController.getController().getGame().getPlayerAt((curIndex + 1) % 3).getHand();

        } else if (position == FXHandShelfPosition.RIGHT_PLAYER) {

            playersHand = fxController.getController().getGame().getPlayerAt((curIndex + 2) % 3).getHand();

        } else {

            playersHand = fxController.getController().getGame().getPlayerAt(curIndex).getHand();
        }

        var shiftRight = false;
        for (var i = 0; i < handFXCards.length; i++) {

            if (!handFXCards[i].isEqualTo(playersHand.getCardAt(i))) {

                handFXCards[i].changeCard(playersHand.getCardAt(i));
            }

            if (position == FXHandShelfPosition.MID_PLAYER) {

                if (handFXCards[i].getCard() == null) {

                    AnchorPane.setLeftAnchor(handFXCards[i].getAnchorCard(), 9 * 70.0 + 2 * (119.0 - 74.0));

                } else

                if (shiftRight) {

                    AnchorPane.setLeftAnchor(handFXCards[i].getAnchorCard(), i * 70.0 + (119.0 - 74.0));

                } else {

                    AnchorPane.setLeftAnchor(handFXCards[i].getAnchorCard(), i * 70.0);
                }

                if (handFXCards[i].isSelected()) {

                    shiftRight = true;
                }
            }

            handFXCards[i].update();
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

            deselectAll();

            handFXCards[index].setSelected(true);
            selectedCardIndex = index;

        } else if (selectedCardIndex == index) {

            handFXCards[index].setSelected(false);
            selectedCardIndex = -1;

        } else {

            var move = new SkatMove(ActionType.ON_HAND, selectedCardIndex, index);

            if (fxController.makeMove(move)) {

                deselectAll();
                selectedCardIndex = -1;
            }
        }

        FXPresenter.update();
    }

    public void deselectAll() {

        for (FXCard card : handFXCards) {

            card.setSelected(false);
        }
    }



}
