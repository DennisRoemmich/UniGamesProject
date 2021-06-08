package sample;

import core.Chess;
import core.ChessResult;
import core.GameOverDetector;
import core.positioning.Square;
import org.json.simple.JSONObject;


public class Controller {

    private Chess game;
    private Presenter presenter;

    boolean isGameRunning = false;

    private Player playerA;
    private Player playerB;

    boolean colorSwitch = false;

    public void executeMove(JSONObject move) {
        try {
            String originName = move.get("origin").toString();
            String destinationName = move.get("destination").toString();
            Square origin = new Square(originName);
            Square destination = new Square(destinationName);
            game.makeMove(origin, destination);
        } catch (Exception e) {
        	PrintError.writeErrorLog("");
        }
    }

    public Controller() {

    }
    public Controller(Presenter presenter) {
        this.presenter = presenter;
    }

    public void setPlayerA(Player playerA) {
        this.playerA = playerA;
    }

    public void setPlayerB(Player playerB) {
        this.playerB = playerB;
    }

    public Chess getGame() {
        return game;
    }

    public void createGame() {
        game = new Chess();
    }

    public boolean startGame() {
        if (playerA == null || playerB == null) {
            return false;
        } else {
            isGameRunning = true;
            if(presenter != null) {
                presenter.refreshOutput();
            }
            gameLoop();
            return true;
        }
    }

    public void gameLoop() {
        while(isGameRunning) {
            gameStep();
            if(presenter != null) {
                presenter.refreshOutput();
            }
        }
    }

    public void gameStep() {
        boolean isTurnOfPlayerA = game.isItWhitesTurn() != colorSwitch;
        if(isTurnOfPlayerA){
            executeMove(playerA.requestMove());
        } else {
            executeMove(playerB.requestMove());
        }
        updateGameState();
    }

    private void updateGameState() {
        isGameRunning = GameOverDetector.checkForMate(game.isItWhitesTurn(), game.getBoard()) == ChessResult.NONE;
    }
}
