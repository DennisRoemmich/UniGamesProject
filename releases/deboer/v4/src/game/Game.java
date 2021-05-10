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
        for(int i = 0; i < amount; i ++) {
            undoLastAction();
        }
    }
    
    public final void undoLastAction(){
        List<String> backupMoveHistory = actionHistory;
        actionHistory.remove(actionHistory.size() - 1);
        reset();
        executeActions(actionHistory);
    }

    public final void executeActions(List<String> actions) {
        for(String action: actions) {
            executeAction(action);
        }
    }
}
