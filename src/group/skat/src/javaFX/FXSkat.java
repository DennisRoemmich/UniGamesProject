package javaFX;

import javaFX.enums.FXCardPosition;
import javafx.scene.layout.AnchorPane;

public class FXSkat {

    private FXController fxController;

    public AnchorPane anchorSkat;
    private FXCard[] skatFXCards;

    private int selectedCardIndex;

    /* CONSTRUCTOR */

    public FXSkat(FXController fxController, AnchorPane left, AnchorPane right) {

        this.fxController = fxController;

        anchorSkat = new AnchorPane();

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

    /* ACTIONHANDLING */

    public void cardClickedAt(int index) {


    }
}
