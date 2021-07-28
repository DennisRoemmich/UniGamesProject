package javaFX;

import controller.SkatMove;
import controller.enums.ActionType;
import engine.Hand;
import javaFX.enums.FXHandShelfPosition;
import javafx.scene.layout.AnchorPane;

/**
 * class for the hand in GUI
 */
public class FXHandShelf {

    private FXController mFxController;

    private FXHandShelfPosition mPosition;

    private FXCard[] mHandFXCards;

    private int mSelectedCardIndex;

    /* CONSTRUCTOR */

    /**
     * constructor
     * @param pane pane of handshelf
     * @param fxController fxcontroller
     * @param pos position of handshelf
     */
    public FXHandShelf(AnchorPane pane, FXController fxController, FXHandShelfPosition pos) {

        this.mFxController = fxController;

        mPosition = pos;

        mSelectedCardIndex = -1;

        init(pane);
        update();
    }

    /* GETTER */

    public FXCard getFXCardAt(int index) {

        return mHandFXCards[index];
    }

    public FXCard[] getHandFXCards() {

        return mHandFXCards;
    }

    /* OTHER */

    /**
     * initialisation
     * @param pane handshelf anchorpane to handle selection of cards
     */
    public void init(AnchorPane pane) {

        mHandFXCards = new FXCard[10];

        for (var i = 0; i < 10; i++) {

            var card = new FXCard(i, mFxController, mPosition);

            mHandFXCards[i] = card;

            pane.getChildren().add(card.getAnchorCard());

            if (mPosition == FXHandShelfPosition.MID_PLAYER) {

                AnchorPane.setLeftAnchor(card.getAnchorCard(), i * 70.0);

            } else {
                
                AnchorPane.setLeftAnchor(card.getAnchorCard(), i * 45.0);
            }
        }
    }

    /**
     * updates the whole handshelf
     */
    public void update() {

        var curIndex = mFxController.getPlayerGameIndex();
        Hand playersHand;
        
        
        playersHand = initPlayersHand(curIndex);

        var shiftRight = false;
        for (var i = 0; i < mHandFXCards.length; i++) {

            if (!mHandFXCards[i].isEqualTo(playersHand.getCardAt(i))) {

                mHandFXCards[i].changeCard(playersHand.getCardAt(i));
            }

            if (mPosition == FXHandShelfPosition.MID_PLAYER) {

                if (mHandFXCards[i].getCard() == null) {

                    AnchorPane.setLeftAnchor(mHandFXCards[i].getAnchorCard(), 9 * 70.0 + 2 * (119.0 - 74.0));

                } else

                if (shiftRight) {

                    AnchorPane.setLeftAnchor(mHandFXCards[i].getAnchorCard(), i * 70.0 + (119.0 - 74.0));

                } else {

                    AnchorPane.setLeftAnchor(mHandFXCards[i].getAnchorCard(), i * 70.0);
                }

                if (mHandFXCards[i].isSelected()) {

                    shiftRight = true;
                }
            }

            mHandFXCards[i].update();
        }
    }

    /**
     * help method for update
     * @param curIndex index of current player
     * @return corresponding hand
     */
    public Hand initPlayersHand(int curIndex) {

        if (mPosition == FXHandShelfPosition.LEFT_PLAYER) {

            return mFxController.getController().getGame().getPlayerAt((curIndex + 1) % 3).getHand();

        } else if (mPosition == FXHandShelfPosition.RIGHT_PLAYER) {

            return mFxController.getController().getGame().getPlayerAt((curIndex + 2) % 3).getHand();

        } else {

            return mFxController.getController().getGame().getPlayerAt(curIndex).getHand();
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
     * handles card click event
     * @param index index of clicked card
     */
    public void cardClickedAt(int index) {

        if (mSelectedCardIndex == -1) {

            deselectAll();

            mHandFXCards[index].setSelected(true);
            mSelectedCardIndex = index;

        } else if (mSelectedCardIndex == index) {

            mHandFXCards[index].setSelected(false);
            mSelectedCardIndex = -1;

        } else {

            var move = new SkatMove(ActionType.ON_HAND, mSelectedCardIndex, index);

            if (mFxController.makeMove(move)) {

                deselectAll();
                mSelectedCardIndex = -1;
            }
        }

        FXPresenter.update();
    }

    /**
     * deselects all cards
     */
    public void deselectAll() {

        for (FXCard card : mHandFXCards) {

            card.setSelected(false);
        }
        mSelectedCardIndex = -1;
    }



}
