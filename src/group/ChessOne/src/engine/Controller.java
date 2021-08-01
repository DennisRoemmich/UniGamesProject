package engine;

import engine.analysis.ChessResult;
import engine.analysis.GameOverDetector;
import engine.board.ChessMove;
import framework.*;
import torpedo.TorpedoChess;

import org.json.simple.JSONObject;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Creates the surrounding game logic the chess game operates in.
 *
 * @author Jan de Boer, Dennis Roemmich
 */
public class Controller extends GameController implements Runnable, GameOwner {

    private Optional<Chess> mGame = Optional.empty();
    private boolean mColorSwitch = false;
    private boolean mStandardChess = true;

    protected BlockingQueue<JSONObject> mMoveQueue = new LinkedBlockingQueue<>();

    private boolean mIsLoopRunning = true;

    public Controller() {

    }

    public Controller(Player player, Presenter presenter) {
        this.mPlayerA = player;
        this.mPresenter = presenter;
    }

    @Override
    public void run() {
        createGameIfNecessary();
        startGame();
        while (mIsLoopRunning) {
            var player = getCurrentPlayer();
            if (player.isPresent()) {
                player.get().requestMove(createRequestJSon("move"));
                while (mMoveQueue.peek() == null) {
                    // Waiting for move
                }
                JSONObject input = mMoveQueue.poll();
                if (input.containsKey("undo")) {
                    int amount;
                    try {
                        amount = Integer.valueOf(input.get("undo").toString());
                    } catch (Exception e) {
                        amount = 1;
                    }
                    undoLastMoves(amount);
                } else if (input.containsKey("quit")) {
                    mIsGameRunning = false;
                } else {
                    executeMove(input);
                }
                updateGameState();
                refreshOutput();
            }
        }
        PrintToConsole.println("Controller finished.");
    }

    private void createGameIfNecessary() {
        if (mGame.isEmpty()) {
            newGame();
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

    public boolean isItMyTurn(Player player) {
        var currentPlayer = getCurrentPlayer();
        if (currentPlayer.isPresent()) {
            return player == currentPlayer.get();
        } else {
            return false;
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

    public void addMoveToQueue(JSONObject move) {
        mMoveQueue.add(move);
    }

    public void setGameMode(boolean isStandard) {
        mStandardChess = isStandard;
    }

    @Override
    public JSONObject metaSettingsToJSon() {
        return new JSONObject();
    }

    @SuppressWarnings("unchecked")
				@Override
    public JSONObject gameSettingsToJSon() {
        JSONObject gameSettings = new JSONObject();
        gameSettings.put("mode", mStandardChess ? "Classical" : "Torpedo");
        return gameSettingsToJSon();
    }

    @Override
    public void restoreMetaSettings(JSONObject metaSettings) {
        //Not used yet
    }

    @Override
    public void restoreGameSettings(JSONObject gameSettings) {
        if (gameSettings.containsKey("mode")) {
            var mode = gameSettings.get("mode");
            mStandardChess = mode.equals("Classical");
        } else {
            mStandardChess = true;
        }
    }

    /* Replay & undo */

    public final void replayLog(GameLog log) {
        newGame();
        restoreMetaSettings(log.getMetaSettings());
        restoreGameSettings(log.getGameSettings());
        for (JSONObject move : log.getMoveLog()) {
            mMoveQueue.add(move);
        }
        startGame();
    }

    public final void undoLastMove() {
        undoLastMoves(1);
    }

    public final void undoLastMoves(int amount) {
        mGameLog.removeLastMoves(amount);
        replayLog(mGameLog);
    }
}
