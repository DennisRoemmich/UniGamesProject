package javaFX;

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

    }

    public void update() {

        var currentTrick = fxController.getController().getGame().getCurrentTrick();

        for (var i = 0; i < 3; i++) {

            if (!trickFXCards[i].isEqualTo(currentTrick.getCardAt(i))) {

                trickFXCards[i].changeCard(currentTrick.getCardAt(i));
            }
        }

    /*    if (currentTrick.getSize() == 3) {

            someMagic();
        }//*/

    /*    for (FXCard card : trickFXCards) {

            card.removeCard();
        }//*/
    }

    public void someMagic() {

        var kv0 = new KeyValue(trickFXCards[0].getAnchorCard().opacityProperty(), 1, Interpolator.EASE_OUT);
        var kv1 = new KeyValue(trickFXCards[1].getAnchorCard().opacityProperty(), 1, Interpolator.EASE_OUT);
        var kv2 = new KeyValue(trickFXCards[2].getAnchorCard().opacityProperty(), 1, Interpolator.EASE_OUT);

        var kf0 = new KeyFrame(Duration.seconds(2), kv0);
        var kf1 = new KeyFrame(Duration.seconds(2), kv1);
        var kf2 = new KeyFrame(Duration.seconds(2), kv2);

        var timeline0 = new Timeline();
        timeline0.setOnFinished(e -> fadeOut());

        timeline0.getKeyFrames().addAll(kf0, kf1, kf2);

        timeline0.play();
    }

    private void fadeOut() {

        var kv0 = new KeyValue(trickFXCards[0].getAnchorCard().opacityProperty(), 0, Interpolator.EASE_IN);
        var kv1 = new KeyValue(trickFXCards[1].getAnchorCard().opacityProperty(), 0, Interpolator.EASE_IN);
        var kv2 = new KeyValue(trickFXCards[2].getAnchorCard().opacityProperty(), 0, Interpolator.EASE_IN);

        var kf0 = new KeyFrame(Duration.seconds(2), kv0);
        var kf1 = new KeyFrame(Duration.seconds(2), kv1);
        var kf2 = new KeyFrame(Duration.seconds(2), kv2);

        var timeline1 = new Timeline();

        timeline1.getKeyFrames().addAll(kf0, kf1, kf2);

        timeline1.play();
    }

}
