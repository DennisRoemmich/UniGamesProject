package sample;

import core.Chess;
import core.ChessResult;
import core.GameOverDetector;
import core.positioning.Square;
import org.json.simple.JSONObject;

/**
 * Creates the surrounding game logic the chess game operates in.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class Controller {

    private Chess mGame;
    private Presenter mPresenter;
    private boolean mIsGameRunning = false;
    private Player mPlayerA;
    private Player mPlayerB;
    private boolean mColorSwitch = false;
    
    public Controller() {
    	//For testing purposes
    }
    
    public Controller(Presenter presenter) {
    	this.mPresenter = presenter;
    }

    public void executeMove(JSONObject move) {
        try {
            String originName = move.get("origin").toString();
            String destinationName = move.get("destination").toString();
            Square origin = new Square(originName);
            Square destination = new Square(destinationName);
            mGame.makeMove(origin, destination);
        } catch (Exception e) {
        	PrintError.writeErrorLog("");
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
            executeMove(mPlayerA.requestMove());
        } else {
            executeMove(mPlayerB.requestMove());
        }
        updateGameState();
    }

    private void updateGameState() {
        mIsGameRunning = GameOverDetector.checkForMate(mGame.isItWhitesTurn(), mGame.getBoard()) == ChessResult.NONE;
    }
}
