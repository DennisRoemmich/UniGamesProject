package src.npc;

import engine.Chess;
import engine.board.ChessMove;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;

public class AiRatingTask {
    private BlockingQueue<AiRatingResult> mReplyQueue;
    private Chess mGame;
    private int mDepth;
    private long mTimeout;
    private Optional<ChessMove> moveToRate; // Shall be empty, if the situation has to be calculated

    public AiRatingTask(BlockingQueue<AiRatingResult> replyQueue, Chess game, int depth, long timeout) {
        this.mReplyQueue = replyQueue;
        this.mGame = game;
        this.mDepth = depth;
        this.mTimeout = timeout;
        this.moveToRate = Optional.empty();
    }

    public AiRatingTask(BlockingQueue<AiRatingResult> replyQueue, Chess game, int depth, long timeout, ChessMove moveToRate) {
        this.mReplyQueue = replyQueue;
        this.mGame = game;
        this.mDepth = depth;
        this.mTimeout = timeout;
        this.moveToRate = Optional.of(moveToRate);
    }

    public BlockingQueue<AiRatingResult> getReplyQueue() {
        return mReplyQueue;
    }

    public Chess getGame() {
        return mGame;
    }

    public int getDepth() {
        return mDepth;
    }

    public long getTimeout() {
        return mTimeout;
    }

    public Optional<ChessMove> getMoveToRate() {
        return moveToRate;
    }
}
