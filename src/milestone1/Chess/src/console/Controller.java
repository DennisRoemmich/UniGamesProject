package console;

import TorpedoChess.TorpedoChess;
import core.*;
import framework.GameController;
import framework.Player;
import framework.Presenter;

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
    private AiPlayer mPlayerAi;
    private boolean mColorSwitch = false;
    
    public Controller() {
    }
    
    public Controller(Presenter presenter) {
        // Vielleicht ist es übersichtlicher auf diesen Konstuktor zu verzichten und set zu benutzen
        // Oder ein Konstuktor mit Presenter und Player-Liste als Parametern
        mPresenter = presenter;
    }


    protected JSONObject executeMove(JSONObject moveJSON) {
    	if (moveJSON == null) {
    		return createReply(false, "NullInput");
    	}
    	if (!moveJSON.containsKey("origin") || !moveJSON.containsKey("destination")) {
            return createReply(false, "InvalidInput");
        }
    	ChessMove move;
        try {
            move = ChessMove.valueOf(moveJSON);
        } catch (Exception e) {
            WriteError.writeErrorLog("");
            return createReply(false, "unknown");
        }
        mGame.makeMove(move);
        return createReply(true, "success");
    }

    @Override
    public void newGame() {
        mGame = new TorpedoChess();
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

    }

    @Override
    public void restoreGameSettings(JSONObject gameSettings) {

    }


    public void setPlayerA(Player playerA) {
        this.mPlayerA = playerA;
    }

    public void setPlayerB(Player playerB) {
        this.mPlayerB = playerB;
    }
    
    public AiPlayer getAiPlayer() {
        mPlayerAi = new AiPlayer();
    	return this.mPlayerAi;
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
            gameLoop();
            return true;
        }
    }

    public void gameLoop() {
        while (mIsGameRunning) {
            gameStep();
            if (mPresenter != null) {
                mPresenter.refreshOutput();
            }
        }
    }

    public void gameStep() {
    	boolean isTurnOfPlayerA = mGame.isItWhitesTurn() != mColorSwitch;
        if (isTurnOfPlayerA) {
            handleMove(mPlayerA.requestMove(createRequestJSON("move")));
        } else {
            handleMove(mPlayerB.requestMove(createRequestJSON("move")));
        }
        updateGameState();
    }

    private void updateGameState() {
        if(mIsGameRunning) {
            mIsGameRunning = GameOverDetector.checkForMate(mGame.isItWhitesTurn(), mGame.getBoard()) == ChessResult.NONE;
        }
    }
}
