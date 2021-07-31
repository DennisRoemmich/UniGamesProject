package src.engine;

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
 *
 * @author Jan de Boer, Dennis Roemmich
 */
public class Controller extends GameController implements Runnable {

    private Optional<Chess> mGame = Optional.empty();
    private Player mPlayerA;
    private Player mPlayerB;
    private boolean mColorSwitch = false;
    private boolean mStandardChess = true;
    private boolean mIsLoopRunning = true;

    protected LinkedBlockingQueue<JSONObject> moveQueue = new LinkedBlockingQueue<JSONObject>();

    public Controller() {

    }

    @Override
    public void run() {
        newGame();
        startGame();
        while (mIsLoopRunning) {
            if (getCurrentPlayer().isPresent()) {
                getCurrentPlayer().get().requestMove(createRequestJSon("move"));
                while (moveQueue.peek() == null) {
                }
                JSONObject input = moveQueue.poll();
                executeMove(input);
                refreshOutput();
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
        }
    }

    public void refreshOutput() {
        mPresenter.refreshOutput();
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

    private boolean startGame() {
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

    private void updateGameState() {
        if (mGame.isPresent() && GameOverDetector.checkForMate(mGame.get()) == ChessResult.NONE) {
            quitGame();
        }
    }

    public Optional<Player> getCurrentPlayer() {
        if (mGame.isPresent()) {
            if (mGame.get().getCurrentColor().isWhite() != mColorSwitch) {
                return Optional.of(mPlayerA);
            } else {
                return Optional.of(mPlayerB);
            }
        } else {
            return Optional.empty();
        }
    }

    public LinkedBlockingQueue<JSONObject> getMoveQueue() {
        return moveQueue;
    }

    public boolean isItMyTurn(Player player) {
        var currentPlayer = getCurrentPlayer();
        if (currentPlayer.isPresent()) {
            return player == currentPlayer.get();
        } else {
            return false;
        }
    }
}
