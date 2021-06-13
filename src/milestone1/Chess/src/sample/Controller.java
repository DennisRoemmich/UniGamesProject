package sample;

import core.Chess;
import core.ChessResult;
import core.GameOverDetector;
import core.positioning.Square;
import framework.GameController;
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

    private static Presenter mPresenter;
    private static boolean mIsGameRunning = false;
    private static List<Player> players = new ArrayList<Player>();
    private static boolean mColorSwitch = false;
    private static Controller main = new Controller();

    public static boolean addPlayerGlobal(Player playerToAdd) {
        if(players.size() < 2) {
            players.add(playerToAdd);
            return true;
        } else {
            return false;
        }
    }

    public static void removePlayerGlobal(Player playerToRemove) {
        players.remove(playerToRemove);
    }

    private static JSONObject getMoveRequestJSON() {
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
            PrintError.writeErrorLog("");
            return;
        }
        if(Chess.makeMove(origin, destination)) {
            logMove(move);
            tryOutput();
        }
    }

    public static Controller getMain() {
        return main;
    }

    public static boolean startGame() {
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

    public static void gameLoop() {
        while (mIsGameRunning) {
            gameStep();
            if (mPresenter != null) {
                mPresenter.refreshOutput();
            }
        }
    }

    public static void gameStep() {
        boolean isTurnOfPlayerA = Chess.isItWhitesTurn() != mColorSwitch;
        if (isTurnOfPlayerA) {
            main.executeMove(players.get(0).requestMove(getMoveRequestJSON()));
        } else {
            main.executeMove(players.get(1).requestMove(getMoveRequestJSON()));
        }
    }

    public void setPresenter(Presenter newPresenter) {
        presenter = newPresenter;
    }
}
