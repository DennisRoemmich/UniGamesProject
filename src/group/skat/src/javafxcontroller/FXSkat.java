package javafxcontroller;

import controller.SkatMove;
import controller.enums.ActionType;
import engine.Card;
import engine.enums.GamePhase;
import javafxcontroller.enums.FXCardPosition;
import javafx.scene.layout.AnchorPane;

/**
 * class for skat in GUI
 */
public class FXSkat {

    private FXController mFxController;

    private FXCard[] mSkatFXCards;

    private int mSelectedCardIndex;

    /* CONSTRUCTOR */

    /**
     * constructor
     * @param fxController fxcontroller
     * @param left anchorpane of left card
     * @param right anchorpane of right card
     */
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

    /**
     * initialisation
     * @param left anchorpane of left card
     * @param right anchorpane of right card
     */
    private void init(AnchorPane left, AnchorPane right) {

        var skat = mFxController.getController().getGame().getSkat();

        mSkatFXCards = new FXCard[2];

        mSkatFXCards[0] = new FXCard(left, FXCardPosition.SKAT, 0, mFxController);
        mSkatFXCards[1] = new FXCard(right, FXCardPosition.SKAT, 1, mFxController);


        for (var i = 0; i < skat.length; i++) {

            mSkatFXCards[i].setOpen(true);
        }
    }

    /**
     * updates the skat
     */
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

    /**
     * handles skat-card click event
     * @param index index of clicked card
     */
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

    /**
     * deselects both cards
     */
    public void deselectAll() {

        for (FXCard card : mSkatFXCards) {

            card.setSelected(false);
        }
        mSelectedCardIndex = -1;
    }
}
