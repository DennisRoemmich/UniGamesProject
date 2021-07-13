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

    /* GETTER */

    public FXCard getFXCardAt(int index) {

        return skatFXCards[index];
    }

    /* OTHER */

    private void init(AnchorPane left, AnchorPane right) {

        var skat = fxController.getController().getGame().getSkat();

        skatFXCards = new FXCard[2];

        skatFXCards[0] = new FXCard(left, FXCardPosition.SKAT, 0, fxController);
        skatFXCards[1] = new FXCard(right, FXCardPosition.SKAT, 1, fxController);



        for (var i = 0; i < skat.length; i++) {

            skatFXCards[i].setOpen(true);
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

            for (FXCard card : skatFXCards) {

                card.setSelected(false);
            }

            skatFXCards[index].setSelected(true);
            selectedCardIndex = index;

        } else if (selectedCardIndex == index) {

            skatFXCards[index].setSelected(false);
            selectedCardIndex = -1;

        } else {

            var move = new SkatMove(ActionType.ON_SKATHAND, selectedCardIndex, index);

            if (fxController.makeMove(move)) {

                skatFXCards[selectedCardIndex].setSelected(false);
                selectedCardIndex = -1;
            }
        }

        update();
    }
}
