package engine;

import engine.analysis.ChessResult;
import engine.analysis.GameOverDetector;
import engine.board.ChessMove;
import javafx.application.Platform;
import torpedo.TorpedoChess;
import framework.GameController;
import framework.Player;

import framework.WriteError;
import org.json.simple.JSONObject;

import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Creates the surrounding game logic the chess game operates in.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class Controller extends GameController implements Runnable {

    private Optional<Chess> mGame = Optional.empty();
    private Player mPlayerA;
    private Player mPlayerB;
    private boolean mColorSwitch = false;
    private boolean mStandardChess = true;
    private boolean mIsLoopRunning = true;

    protected LinkedBlockingQueue<JSONObject> moveQueue = new LinkedBlockingQueue<JSONObject>();
    
    public Controller(LinkedBlockingQueue<JSONObject> moveQueue) {
        this.moveQueue = moveQueue;
    }

    @Override
    public void run() {
        newGame();
        startGame();
        while(mIsLoopRunning) {
            try {
                JSONObject input = moveQueue.take();
                executeMove(input);
                refreshOutput();
            } catch (InterruptedException e) {
                mIsLoopRunning = false;
            }
        }
    }

    protected void executeMove(JSONObject moveJSon) {
    	if (moveJSon == null) {
    		return;
    	}
    	if (!moveJSon.containsKey("origin") || !moveJSon.containsKey("destination")) {
            return;
        }
    	if (mGame.isEmpty()) {
    	    return;
        }
    	ChessMove move;
        try {
            move = ChessMove.valueOf(moveJSon);
        } catch (Exception e) {
            WriteError.writeErrorLog("");
            
            return;
        }
        if (mGame.get().isMovePossible(move)) {
            mGame.get().makeMove(move);
            logMove(moveJSon);
            refreshOutput();
        }
    }

    public void refreshOutput() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mPresenter.refreshOutput();
            }
        });
    }
    
    public void setStandardChess() {
    	this.mStandardChess = !(this.mStandardChess);
    }

    public ChessMove getLastMove() {

        var moveLog = mGameLog.getMoveLog();
        return ChessMove.valueOf(moveLog.get(moveLog.size() - 1));

    }

    @Override
    public void newGame() {
        if (!mStandardChess) {
            mGame = Optional.of(new TorpedoChess());
        } else {
            mGame = Optional.of(new Chess());
        }
    }

    @Override
    public JSONObject metaSettingsToJSon() {
        return new JSONObject();
    }

    @Override
    public JSONObject gameSettingsToJSon() {
        return new JSONObject();
    }

    @Override
    public void restoreMetaSettings(JSONObject metaSettings) {
    		//Not used yet
    }

    @Override
    public void restoreGameSettings(JSONObject gameSettings) {
    		//Not used yet
    }

    public void setPlayerA(Player playerA) {
        this.mPlayerA = playerA;
    }

    public void setPlayerB(Player playerB) {
        this.mPlayerB = playerB;
    }

    public Optional<Chess> getGame() {
        return mGame;
    }

    public boolean startGame() {
        if (mGame.isEmpty()) {
            mGame = Optional.of(new Chess());
        }
    	if (mPlayerA == null || mPlayerB == null) {
            return false;
        } else {
            mIsGameRunning = true;
            refreshOutput();
            return true;
        }
    }

    public void gameStep() {
    	if (mGame.isPresent()) {
            boolean isTurnOfPlayerA = mGame.get().getCurrentColor().isWhite() != mColorSwitch;
            Player currentPlayer = isTurnOfPlayerA ? mPlayerA : mPlayerB;
            JSONObject json = currentPlayer.requestMove(createRequestJSon("move"));
            updateGameState();
            refreshOutput();
        }
    }

    private void updateGameState() {
        if (mGame.isPresent() && GameOverDetector.checkForMate(mGame.get()) == ChessResult.NONE) {
        	quitGame();
        }
    }
}
