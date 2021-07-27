package gui;

import java.util.concurrent.ThreadLocalRandom;

import javafx.animation.AnimationTimer;

/**
 * Plays the roll animation.
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public class RollAnimation extends AnimationTimer {

    public static final long FRAMES_PER_SEC = 10L;
    public static final long INTERVAL = 1000000000L / FRAMES_PER_SEC;
    public static final int MAX_ROLLS = 20;

    private long mLast = 0;
    private int mCount = 0;

    private FxController mFxController;

    private boolean mIsRunning = false;
    private boolean mAnimationStopFlag = false;

    public RollAnimation(FxController fxController) {
        this.mFxController = fxController;
    }

    @Override
    public void handle(long l) {
        mIsRunning = true;
        if (l - mLast > INTERVAL) {
            int r = ThreadLocalRandom.current().nextInt(1, 7);
            int j = ThreadLocalRandom.current().nextInt(1, 7);
            mFxController.setDiceViews(r, j);
            mLast = l;
            mCount++;
            if (mCount > MAX_ROLLS || mAnimationStopFlag) {
                stop();
                mFxController.finishRoll();
                mCount = 0;
                mIsRunning = false;
                mAnimationStopFlag = false;
            }
        }
    }

    public boolean isRunning() {
        return mIsRunning;
    }

    public void stopAnimation() {
        if (mIsRunning) {
            mAnimationStopFlag = true;
        }
    }

}
