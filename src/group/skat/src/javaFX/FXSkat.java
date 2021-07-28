package javaFX;

import controller.SkatMove;
import controller.enums.ActionType;
import engine.Card;
import engine.enums.GamePhase;
import javaFX.enums.FXCardPosition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class FXSkat {

    private FXController mFxController;

    private FXCard[] mSkatFXCards;

    private int mSelectedCardIndex;

    /* CONSTRUCTOR */

    public FXSkat(FXController fxController, AnchorPane left, AnchorPane right) {

        this.mFxController = fxController;

        mSelectedCardIndex = -1;

        init(left, right);
        update();
    }

    /* GETTER */

    public FXCard getFXCardAt(int index) {

        return mSkatFXCards[index];
    }

    /* OTHER */

    private void init(AnchorPane left, AnchorPane right) {

        var skat = mFxController.getController().getGame().getSkat();

        mSkatFXCards = new FXCard[2];

        mSkatFXCards[0] = new FXCard(left, FXCardPosition.SKAT, 0, mFxController);
        mSkatFXCards[1] = new FXCard(right, FXCardPosition.SKAT, 1, mFxController);


        for (var i = 0; i < skat.length; i++) {

            mSkatFXCards[i].setOpen(true);
        }
    }

    public void update() {

        var game = mFxController.getController().getGame();
        var phase = game.getGamePhase();
        var skat = new Card[2];

        if (phase == GamePhase.AUCTION) {

            skat = game.getSkat();

        } else {

            skat[0] = game.getDeclarer().getHand().getCardAt(10);
            skat[1] = game.getDeclarer().getHand().getCardAt(11);
        }

        for (var i = 0; i < 2; i++) {

            if (!mSkatFXCards[i].isEqualTo(skat[i])) {

                mSkatFXCards[i].changeCard(skat[i]);
            }

            mSkatFXCards[i].update();

        }
    }

    /* OTHER */

    public int getSelectedCardIndex() {

        return mSelectedCardIndex;
    }

    public void setSelectedCardIndex(int index) {

        mSelectedCardIndex = index;
    }

    /* ACTIONHANDLING */

    public void cardClickedAt(int index) {

        if (mSelectedCardIndex == -1) {

            for (FXCard card : mSkatFXCards) {

                card.setSelected(false);
            }

            mSkatFXCards[index].setSelected(true);
            mSelectedCardIndex = index;

        } else if (mSelectedCardIndex == index) {

            mSkatFXCards[index].setSelected(false);
            mSelectedCardIndex = -1;

        } else {

            var move = new SkatMove(ActionType.ON_SKATHAND, 10 + mSelectedCardIndex, 10 + index);

            if (mFxController.makeMove(move)) {

                mSkatFXCards[mSelectedCardIndex].setSelected(false);
                mSelectedCardIndex = -1;
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

        for (FXCard card : mSkatFXCards) {

            card.setSelected(false);
        }
        mSelectedCardIndex = -1;
    }
}
