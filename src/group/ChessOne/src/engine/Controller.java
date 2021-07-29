package engine;

import engine.analysis.ChessResult;
import engine.analysis.GameOverDetector;
import engine.board.ChessMove;
import torpedo.TorpedoChess;
import framework.GameController;
import framework.Player;

import framework.WriteError;
import org.json.simple.JSONObject;

import java.util.Optional;

/**
 * Creates the surrounding game logic the chess game operates in.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class Controller extends GameController {

    private Optional<Chess> mGame = Optional.empty();
    private Player mPlayerA;
    private Player mPlayerB;
    private boolean mColorSwitch = false;
    private boolean mStandardChess = true;
    
    public Controller() {
    	//Unused
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
            if (mPresenter != null) {
                mPresenter.refreshOutput();                
            }
        }
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
            if (mPresenter != null) {
                mPresenter.refreshOutput();
            }
            gameLoop();
            return true;
        }
    }

    public void gameLoop() {
        while (moveQueue.poll() == null) {

        }
        JSONObject move = moveQueue.remove();
        executeMove(move);
        if (mIsGameRunning) {
            gameStep();
        }
    }

    public void gameStep() {
    	if (mGame.isPresent()) {
            boolean isTurnOfPlayerA = mGame.get().getCurrentColor().isWhite() != mColorSwitch;
            Player currentPlayer = isTurnOfPlayerA ? mPlayerA : mPlayerB;
            JSONObject json = currentPlayer.requestMove(createRequestJSon("move"));
            updateGameState();
            if (mPresenter != null) {
                mPresenter.refreshOutput();
            }
        }
    }

    private void updateGameState() {
        if (mGame.isPresent() && GameOverDetector.checkForMate(mGame.get()) == ChessResult.NONE) {
        	quitGame();
        }
    }
}
