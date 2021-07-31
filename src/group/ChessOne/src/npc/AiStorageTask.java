package npc;

import java.util.concurrent.BlockingQueue;

public class AiStorageTask {
    private AiRatingTask aiRatingTask;
    private BlockingQueue<Double> replyQueue;

    public AiStorageTask(AiRatingTask aiRatingTask, BlockingQueue<Double> replyQueue) {
        this.aiRatingTask = aiRatingTask;
        this.replyQueue = replyQueue;
    }

    public AiRatingTask getAiRatingTask() {
        return aiRatingTask;
    }

    public BlockingQueue<Double> getReplyQueue() {
        return replyQueue;
    }
}
