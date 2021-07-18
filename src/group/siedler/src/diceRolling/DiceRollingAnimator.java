package diceRolling;

import javafx.animation.AnimationTimer;

// TODO : Extract the Roller class from FXController

/*

public class DiceRollingAnimator extends AnimationTimer {

    private long FRAMES_PER_SEC = 10L;
    private long INTERVAL = 1000000000L / FRAMES_PER_SEC;
    private int MAX_ROLLS = 20;

    private long last = 0;
    private int count = 0;

    private boolean isRunning = false;
    private boolean animationStopFlag = false;

    @Override
    public void handle(long l) {
        isRunning = true;
        if (l - last > INTERVAL) {
            int r = 2 + (int) (Math.random() * 5);
            setDiceImage(r, dice1);
            int j = 2 + (int) (Math.random() * 5);
            setDiceImage(j, dice2);
            last = l;
            count++;
            if (count > MAX_ROLLS || animationStopFlag) {
                stop();
                finishRoll();
                count = 0;
                isRunning = false;
            }
        }
    }

    public boolean isRunning() {
        return isRunning;
    }
}*/
