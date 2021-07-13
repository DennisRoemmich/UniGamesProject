package javaFX;

import javaFX.enums.FXCardPosition;
import javafx.scene.layout.AnchorPane;

public class FXCurrentTrick {

    private FXController fxController;

    private AnchorPane anchorCurrentTrick;
    private FXCard[] trickFXCards;

    /* CONSTRUCTOR */

    public FXCurrentTrick(FXController fxController, AnchorPane one, AnchorPane two, AnchorPane three) {

        this.fxController = fxController;

        anchorCurrentTrick = new AnchorPane();

        init(one, two, three);
        update();
    }

    /* OTHER */

    private void init(AnchorPane one, AnchorPane two, AnchorPane three) {

        trickFXCards = new FXCard[3];

        trickFXCards[0] = new FXCard(one, FXCardPosition.TRICK, 0, fxController);
        trickFXCards[1] = new FXCard(two, FXCardPosition.TRICK, 1, fxController);
        trickFXCards[2] = new FXCard(three, FXCardPosition.TRICK, 2, fxController);

        for (var i = 0; i < 3; i++) {



        }
    }

    public void update() {

        var currentTrick = fxController.getController().getGame().getCurrentTrick();

        for (var i = 0; i < 3; i++) {

            if (!trickFXCards[i].isEqualTo(currentTrick.getCardAt(i))) {

                trickFXCards[i].changeCard(currentTrick.getCardAt(i));
            }
        }
    }


}
