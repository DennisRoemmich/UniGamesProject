package npc;

import java.util.concurrent.BlockingQueue;

/**
 * Initializes a task for storing the move rating.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class AiStorageTask {
    private AiRatingTask mAiRatingTask;
    private BlockingQueue<Double> mReplyQueue;

    public AiStorageTask(AiRatingTask aiRatingTask, BlockingQueue<Double> replyQueue) {
        this.mAiRatingTask = aiRatingTask;
        this.mReplyQueue = replyQueue;
    }

    public AiRatingTask getAiRatingTask() {
        return mAiRatingTask;
    }

    public BlockingQueue<Double> getReplyQueue() {
        return mReplyQueue;
    }
}
