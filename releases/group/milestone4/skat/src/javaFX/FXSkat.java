package javaFX;

import console.Print;
import controller.SkatMove;
import controller.enums.ActionType;
import engine.Card;
import engine.enums.CardValue;
import engine.enums.GamePhase;
import javaFX.enums.FXCardPosition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

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

        var game = fxController.getController().getGame();
        var phase = game.getGamePhase();
        Card[] skat = new Card[2];

        if (phase == GamePhase.AUCTION) {

            skat = game.getSkat();

        } else {

            skat[0] = game.getDeclarer().getHand().getCardAt(10);
            skat[1] = game.getDeclarer().getHand().getCardAt(11);
        }

        for (var i = 0; i < 2; i++) {

            if (!skatFXCards[i].isEqualTo(skat[i])) {

                skatFXCards[i].changeCard(skat[i]);
            }

            skatFXCards[i].update();

        /*    if (skatFXCards[i].getCard().getCardValue() == CardValue.JACK) {

                someMagic(skatFXCards[i].getAnchorCard());

            } else {

                skatFXCards[i].getAnchorCard().setOpacity(1);
            }//*/
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

            var move = new SkatMove(ActionType.ON_SKATHAND, 10 + selectedCardIndex, 10 + index);

            if (fxController.makeMove(move)) {

                skatFXCards[selectedCardIndex].setSelected(false);
                selectedCardIndex = -1;
            }
        }

        update();
    }

    public void someMagic(AnchorPane pane) {

        var kv0 = new KeyValue(pane.opacityProperty(), 1, Interpolator.EASE_OUT);

        var kf0 = new KeyFrame(Duration.seconds(2), kv0);

        var timeline0 = new Timeline();
        timeline0.setOnFinished(e -> fadeOut(pane));

        timeline0.getKeyFrames().addAll(kf0);

        timeline0.play();
    }

    private void fadeOut(AnchorPane pane) {

        var kv0 = new KeyValue(pane.opacityProperty(), 0, Interpolator.EASE_IN);

        var kf0 = new KeyFrame(Duration.seconds(2), kv0);

        var timeline1 = new Timeline();

        timeline1.getKeyFrames().addAll(kf0);

        timeline1.play();
    }

    public void deselectAll() {

        for (FXCard card : skatFXCards) {

            card.setSelected(false);
        }
        selectedCardIndex = -1;
    }
}
