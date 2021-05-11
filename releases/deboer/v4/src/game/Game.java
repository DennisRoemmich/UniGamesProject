package game;

import java.util.ArrayList;
import java.util.List;

public abstract class Game {

    private List<String> actionHistory = new ArrayList<String>();

    
    public abstract void executeAction(String action);
    public abstract void reset();

    public Game() {

    }

    public Game(List<String> moves) {
        executeActions(moves);
    }

    public final void logAction(String action) {
        actionHistory.add(action);
    }

    public final void undoLastActions(int amount) {
        List<String> backupMoveHistory = actionHistory;
        for(int i = 0; i < amount; i ++) {
            backupMoveHistory.remove(backupMoveHistory.size() - 1);
        }
        reset();
        executeActions(backupMoveHistory);
    }
    
    public final void undoLastAction(){
        undoLastActions(1);
    }

    public final void executeActions(List<String> actions) {
        for(String action: actions) {
            executeAction(action);
        }
    }
}
