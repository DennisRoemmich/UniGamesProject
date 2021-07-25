package gui;

import javafx.animation.AnimationTimer;

public class RollAnimation extends AnimationTimer {

    private long FRAMES_PER_SEC = 10L;
    private long INTERVAL = 1000000000L / FRAMES_PER_SEC;
    private int MAX_ROLLS = 20;

    private long last = 0;
    private int count = 0;

    private FXController fxController;

    private boolean isRunning = false;
    private boolean animationStopFlag = false;

    public RollAnimation(FXController fxController) {
        this.fxController = fxController;
    }

    @Override
    public void handle(long l) {
        isRunning = true;
        if (l - last > INTERVAL) {
            int r = 2 + (int) (Math.random() * 5);
            int j = 2 + (int) (Math.random() * 5);
            fxController.setDiceViews(r,j);
            last = l;
            count++;
            if (count > MAX_ROLLS || animationStopFlag) {
                stop();
                fxController.finishRoll();
                count = 0;
                isRunning = false;
                animationStopFlag = false;
            }
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void stopAnimation() {
        if(isRunning) {
            animationStopFlag = true;
        }
    }

}
