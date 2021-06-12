package sample;

import core.Chess;
import core.ChessResult;
import core.GameOverDetector;
import core.positioning.Square;
import framework.GameController;
import framework.Player;
import framework.Presenter;
import org.json.simple.JSONObject;

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
    private final JSONObject mMoveRequestJSon;
    
    public Controller() {
    	 JSONObject object = new JSONObject();
    	 object.put("type", "move");
    	 mMoveRequestJSon = object;
    }
    
    public Controller(Presenter presenter) {
    	this.mPresenter = presenter;
        JSONObject object = new JSONObject();
        object.put("type", "move");
        mMoveRequestJSon = object;
    }

    public void executeMove(JSONObject move) {
        String originName = move.get("origin").toString();
        String destinationName = move.get("destination").toString();
        Square origin;
        Square destination;
        try {
            origin = new Square(originName);
            destination = new Square(destinationName);
        } catch (Exception e) {
            WriteError.writeErrorLog("");
            return;
        }
        if(mGame.makeMove(origin, destination)) {
            logMove(move);
        }
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

    public void createGame() {
        mGame = new Chess();
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
            executeMove(mPlayerA.requestMove(mMoveRequestJSon));
        } else {
            executeMove(mPlayerB.requestMove(mMoveRequestJSon));
        }
        updateGameState();
    }

    private void updateGameState() {
        mIsGameRunning = GameOverDetector.checkForMate(mGame.isItWhitesTurn(), mGame.getBoard()) == ChessResult.NONE;
    }
}
