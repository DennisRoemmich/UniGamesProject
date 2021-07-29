package javafx;

import console.Print;
import engine.enums.GamePhase;
import javafx.enums.FXCardPosition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * class for current trick in middle of gUI
 */
public class FXCurrentTrick {

    private FXController mFxController;

    private FXCard[] mTrickFXCards;

    private Timeline mTimeline0;
    private Timeline mTimeline1;

    private boolean mTimelineShown;

    /* CONSTRUCTOR */

    /**
     * constructor for current trick
     * @param fxController fxcontroller
     * @param one anchorpane of first card
     * @param two anchorpane of second card
     * @param three anchorpane of third card
     */
    public FXCurrentTrick(FXController fxController, AnchorPane one, AnchorPane two, AnchorPane three) {

        this.mFxController = fxController;


        mTimelineShown = false;

        init(one, two, three);
        update();
    }

    /* GETTER */

    /**
     * needed to end animation immediately
     * @return timelines
     */
    public Timeline[] getTimelines() {

        return new Timeline[]{mTimeline0, mTimeline1};
    }

    /* OTHER */

    /**
     * initialisation
     * @param one first anchorpane
     * @param two second anchorpane
     * @param three third anchorpane
     */
    private void init(AnchorPane one, AnchorPane two, AnchorPane three) {

        mTrickFXCards = new FXCard[3];

        mTrickFXCards[0] = new FXCard(one, FXCardPosition.TRICK, 0, mFxController);
        mTrickFXCards[1] = new FXCard(two, FXCardPosition.TRICK, 1, mFxController);
        mTrickFXCards[2] = new FXCard(three, FXCardPosition.TRICK, 2, mFxController);

    }

    /**
     * updates the whole current trick
     */
    public void update() {

        var game = mFxController.getController().getGame();
        var currentTrick = game.getCurrentTrick();

        if (currentTrick.getSize() == 0 && game.getCurrentRoundNo() > 0) {

            currentTrick = game.getLastTrick();
        }

        for (var i = 0; i < 3; i++) {

            if (!mTrickFXCards[i].isEqualTo(currentTrick.getCardAt(i))) {

                mTrickFXCards[i].changeCard(currentTrick.getCardAt(i));
                mTrickFXCards[i].getAnchorCard().setOpacity(1);
            }
        }

        if (currentTrick.getSize() == 3 && !mTimelineShown) {

            var ended = game.getGamePhase() == GamePhase.ENDED;

            someMagic(ended);
            mTimelineShown = true;
        }
        if (currentTrick.getSize() == 1) {

            mTimelineShown = false;
        }
    }

    /**
     * starts the animation (current trick fade out)
     * @param ended boolean, true if game is finished, false if not
     */
    public void someMagic(boolean ended) {

        var kv00 = new KeyValue(mTrickFXCards[0].getAnchorCard().opacityProperty(), 1, Interpolator.EASE_OUT);
        var kv01 = new KeyValue(mTrickFXCards[1].getAnchorCard().opacityProperty(), 1, Interpolator.EASE_OUT);
        var kv02 = new KeyValue(mTrickFXCards[2].getAnchorCard().opacityProperty(), 1, Interpolator.EASE_OUT);

        var kf00 = new KeyFrame(Duration.seconds(0.2), kv00);
        var kf01 = new KeyFrame(Duration.seconds(0.2), kv01);
        var kf02 = new KeyFrame(Duration.seconds(0.2), kv02);

        mTimeline0 = new Timeline();
        mTimeline0.getKeyFrames().addAll(kf00, kf01, kf02);

        mTimeline0.setOnFinished(e -> fadeOut(ended));

        mTimeline0.play();

    }

    /**
     * second part of fade-out
     * @param ended boolean, true if game is finsihed, false if not -> gameresult shows up or not
     */
    private void fadeOut(boolean ended) {

        var kv10 = new KeyValue(mTrickFXCards[0].getAnchorCard().opacityProperty(), 0, Interpolator.EASE_IN);
        var kv11 = new KeyValue(mTrickFXCards[1].getAnchorCard().opacityProperty(), 0, Interpolator.EASE_IN);
        var kv12 = new KeyValue(mTrickFXCards[2].getAnchorCard().opacityProperty(), 0, Interpolator.EASE_IN);

        var kf10 = new KeyFrame(Duration.seconds(0.5), kv10);
        var kf11 = new KeyFrame(Duration.seconds(0.5), kv11);
        var kf12 = new KeyFrame(Duration.seconds(0.5), kv12);

        mTimeline1 = new Timeline();

        mTimeline1.getKeyFrames().addAll(kf10, kf11, kf12);

        if (ended) {

            mTimeline1.setOnFinished(e -> FXPresenter.update());
        }

        mTimeline1.play();
    }

}
