package sample;

import core.Chess;
import core.ChessMove;
import core.ChessResult;
import core.GameOverDetector;
import core.positioning.Square;
import framework.GameController;
import framework.GameLog;
import framework.Player;
import framework.Presenter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Creates the surrounding game logic the chess game operates in.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class Controller extends GameController {

    private Chess mGame;
    private Presenter mPresenter;
    private boolean mIsGameRunning = false;
    private Player mPlayerA;
    private Player mPlayerB;
    private boolean mColorSwitch = false;

    private final JSONObject mMoveRequestJSON;
    
    public Controller() {
    	 JSONObject object = new JSONObject();
    	 object.put("type", "move");
    	 mMoveRequestJSON = object;
    }
    
    public Controller(Presenter presenter) {
    	this.mPresenter = presenter;
        JSONObject object = new JSONObject();
        object.put("type", "move");
        mMoveRequestJSON = object;
    }

    @Override
    public void newGame() {
        mGame = new Chess();
        mIsGameRunning = true;
    }

    public void executeMove(final JSONObject move) {
        try {
            String originName = move.get("origin").toString();
            String destinationName = move.get("destination").toString();
            Square origin = new Square(originName);
            Square destination = new Square(destinationName);
            if(mGame.makeMove(origin, destination)) {
                logMove(move);
            }
        } catch (Exception e) {
            PrintError.writeErrorLog("");
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
            executeMove(mPlayerA.requestMove(mMoveRequestJSON));
        } else {
            executeMove(mPlayerB.requestMove(mMoveRequestJSON));
        }
        updateGameState();
    }

    private void updateGameState() {
        mIsGameRunning = GameOverDetector.checkForMate(mGame.isItWhitesTurn(), mGame.getBoard()) == ChessResult.NONE;
    }
}
