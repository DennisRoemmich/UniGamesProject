package javaFX;

import console.Print;
import engine.Trick;
import engine.enums.GamePhase;
import javaFX.enums.FXCardPosition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class FXCurrentTrick {

    private FXController fxController;

    private AnchorPane anchorCurrentTrick;
    private FXCard[] trickFXCards;

    private Timeline timeline0;
    private Timeline timeline1;

    private boolean timelineShown;

    /* CONSTRUCTOR */

    public FXCurrentTrick(FXController fxController, AnchorPane one, AnchorPane two, AnchorPane three) {

        this.fxController = fxController;

        anchorCurrentTrick = new AnchorPane();

        timelineShown = false;

        init(one, two, three);
        update();
    }

    /* GETTER */

    public Timeline[] getTimelines() {

        return new Timeline[]{timeline0, timeline1};
    }

    /* OTHER */

    private void init(AnchorPane one, AnchorPane two, AnchorPane three) {

        trickFXCards = new FXCard[3];

        trickFXCards[0] = new FXCard(one, FXCardPosition.TRICK, 0, fxController);
        trickFXCards[1] = new FXCard(two, FXCardPosition.TRICK, 1, fxController);
        trickFXCards[2] = new FXCard(three, FXCardPosition.TRICK, 2, fxController);

    }

    public void update() {

        var game = fxController.getController().getGame();
        var currentTrick = game.getCurrentTrick();

        if (currentTrick.getSize() == 0 && game.getCurrentRoundNo() > 0) {

            currentTrick = game.getLastTrick();
        }

        for (var i = 0; i < 3; i++) {

            if (!trickFXCards[i].isEqualTo(currentTrick.getCardAt(i))) {

                trickFXCards[i].changeCard(currentTrick.getCardAt(i));
                trickFXCards[i].getAnchorCard().setOpacity(1);
            }
        }

        if (currentTrick.getSize() == 3 && !timelineShown) {

            var ended = game.getGamePhase() == GamePhase.ENDED;

            someMagic(ended);
            timelineShown = true;
        }
        if (currentTrick.getSize() == 1) {

            timelineShown = false;
        }
    }

    public void someMagic(boolean ended) {

        var kv00 = new KeyValue(trickFXCards[0].getAnchorCard().opacityProperty(), 1, Interpolator.EASE_OUT);
        var kv01 = new KeyValue(trickFXCards[1].getAnchorCard().opacityProperty(), 1, Interpolator.EASE_OUT);
        var kv02 = new KeyValue(trickFXCards[2].getAnchorCard().opacityProperty(), 1, Interpolator.EASE_OUT);

        var kf00 = new KeyFrame(Duration.seconds(2), kv00);
        var kf01 = new KeyFrame(Duration.seconds(2), kv01);
        var kf02 = new KeyFrame(Duration.seconds(2), kv02);

        timeline0 = new Timeline();
        timeline0.getKeyFrames().addAll(kf00, kf01, kf02);

        timeline0.setOnFinished(e -> fadeOut(ended));

        timeline0.play();
    }

    private void fadeOut(boolean ended) {

        var kv10 = new KeyValue(trickFXCards[0].getAnchorCard().opacityProperty(), 0, Interpolator.EASE_IN);
        var kv11 = new KeyValue(trickFXCards[1].getAnchorCard().opacityProperty(), 0, Interpolator.EASE_IN);
        var kv12 = new KeyValue(trickFXCards[2].getAnchorCard().opacityProperty(), 0, Interpolator.EASE_IN);

        var kf10 = new KeyFrame(Duration.seconds(1.5), kv10);
        var kf11 = new KeyFrame(Duration.seconds(1.5), kv11);
        var kf12 = new KeyFrame(Duration.seconds(1.5), kv12);

        timeline1 = new Timeline();

        timeline1.getKeyFrames().addAll(kf10, kf11, kf12);

        if (ended) {

            timeline1.setOnFinished(e -> FXPresenter.update());
        }

        timeline1.play();
    }

}
