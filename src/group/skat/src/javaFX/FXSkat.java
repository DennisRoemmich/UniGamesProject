package javaFX;

import controller.SkatMove;
import controller.enums.ActionType;
import javaFX.enums.FXCardPosition;
import javafx.scene.layout.AnchorPane;

public class FXSkat {

    private FXController fxController;

    private FXCard[] skatFXCards;

    private int selectedCardIndex;

    /* CONSTRUCTOR */

    public FXSkat(FXController fxController, AnchorPane left, AnchorPane right) {

        this.fxController = fxController;

        selectedCardIndex = -1;

        init(left, right);
        update();
    }

    /* OTHER */

    private void init(AnchorPane left, AnchorPane right) {

        var skat = fxController.getController().getGame().getSkat();

        skatFXCards = new FXCard[2];

        skatFXCards[0] = new FXCard(left, FXCardPosition.SKAT, fxController);
        skatFXCards[1] = new FXCard(right, FXCardPosition.SKAT, fxController);



        for (var i = 0; i < skat.length; i++) {

            skatFXCards[i].setOpen(false);

        }
    }

    public void update() {

        var skat = fxController.getController().getGame().getSkat();

        for (var i = 0; i < skat.length; i++) {

            if (!skatFXCards[i].isEqualTo(skat[i])) {

                skatFXCards[i].changeCard(skat[i]);
            }
        }
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

        } else if (selectedCardIndex == index || skatFXCards[index].isSelected()) {

            selectedCardIndex = -1;

        } else {

            var move = new SkatMove(ActionType.ON_SKATHAND, selectedCardIndex, index);

            if (!fxController.makeMove(move)) {

                selectedCardIndex = index;
                skatFXCards[index].setSelected(true);

            } else {

                skatFXCards[selectedCardIndex].setSelected(false);
                selectedCardIndex = -1;
            }
        }

        update();
    }
}
