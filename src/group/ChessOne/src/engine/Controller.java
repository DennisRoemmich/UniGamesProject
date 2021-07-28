package engine;

import engine.analysis.ChessResult;
import engine.analysis.GameOverDetector;
import engine.board.ChessMove;
import torpedo.TorpedoChess;
import framework.GameController;
import framework.Player;

import framework.WriteError;
import org.json.simple.JSONObject;

/**
 * Creates the surrounding game logic the chess game operates in.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class Controller extends GameController {

    private Chess mGame;
    private Player mPlayerA;
    private Player mPlayerB;
    private boolean mColorSwitch = false;
    private boolean mStandardChess = true;
    
    public Controller() {
    	//Unused
    }

    public void executeMove(JSONObject moveJSon) {
    	if (moveJSon == null) {
    		return;
    	}
    	if (!moveJSon.containsKey("origin") || !moveJSon.containsKey("destination")) {
            return;
        }
    	ChessMove move;
        try {
            move = ChessMove.valueOf(moveJSon);
        } catch (Exception e) {
            WriteError.writeErrorLog("");
            return;
        }
        if (mGame.isMovePossible(move)) {
            mGame.makeMove(move);
            logMove(moveJSon);
            if (mPresenter != null) {
                mPresenter.refreshOutput();
            }
            gameStep();
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
    		mGame = new TorpedoChess();
    	} else {
    		mGame = new Chess();    		
    	}
    }

    @Override
    public JSONObject metaSettingsToJSON() {
        return new JSONObject();
    }

    @Override
    public JSONObject gameSettingsToJSON() {
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

    public void exitGame() {
        mIsGameRunning = false;
    }

    public void setPlayerA(Player playerA) {
        this.mPlayerA = playerA;
    }

    public void setPlayerB(Player playerB) {
        this.mPlayerB = playerB;
    }

    public Chess getGame() {
        return mGame;
    }

    public boolean startGame() {
    	if (mPlayerA == null || mPlayerB == null) {
            return false;
        } else {
            mIsGameRunning = true;
            if (mPresenter != null) {
                mPresenter.refreshOutput();
            }
            gameStep();
            return true;
        }
    }

    public void gameStep() {
    	boolean isTurnOfPlayerA = mGame.getCurrentColor().isWhite() != mColorSwitch;
        Player currentPlayer = isTurnOfPlayerA ? mPlayerA : mPlayerB;
        executeMove(currentPlayer.requestMove(createRequestJSON("move")));
        updateGameState();
        if (mPresenter != null) {
            mPresenter.refreshOutput();
        }
    }

    private void updateGameState() {
        mIsGameRunning = GameOverDetector.checkForMate(mGame) == ChessResult.NONE;
    }
}
