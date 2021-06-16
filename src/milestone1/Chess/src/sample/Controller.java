package sample;

import core.Chess;
import core.ChessResult;
import core.GameOverDetector;
import core.positioning.Square;
import framework.GameController;
import framework.GameLog;
import framework.Player;
import framework.Presenter;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates the surrounding game logic the chess game operates in.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class Controller extends GameController {

    private Presenter mPresenter;
    private boolean mIsGameRunning = false;
    private List<Player> players = new ArrayList<Player>();
    private boolean mColorSwitch = false;

    public boolean addPlayer(Player playerToAdd) {
        if(players.size() < 2) {
            players.add(playerToAdd);
            return true;
        } else {
            return false;
        }
    }

    public void removePlayer(Player playerToRemove) {
        players.remove(playerToRemove);
    }

    private JSONObject getMoveRequestJSON() {
        JSONObject object = new JSONObject();
        object.put("type", "move");
        return object;
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
        if(Chess.makeMove(origin, destination)) {
            logMove(move);
            tryOutput();
        }
    }

    @Override
    public void loadGame(GameLog gameLog) {

    }

    public boolean startGame() {
        if (players.size() != 2) {
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
        boolean isTurnOfPlayerA = Chess.isItWhitesTurn() != mColorSwitch;
        if (isTurnOfPlayerA) {
            executeMove(players.get(0).requestMove(getMoveRequestJSON()));
        } else {
            executeMove(players.get(1).requestMove(getMoveRequestJSON()));
        }
    }

    public void setPresenter(Presenter newPresenter) {
        mPresenter = newPresenter;
    }
}
