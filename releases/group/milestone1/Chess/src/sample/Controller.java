package sample;

import core.Chess;
import core.positioning.Square;
import org.json.simple.JSONObject;


public class Controller {

    private Chess game;
    private Presenter presenter;

    boolean isGameRunning = false;

    Player playerA;
    Player playerB;

    boolean colorSwitch = false;

    public void executeMove(JSONObject move) {
        try {
            String originName = move.get("origin").toString();
            String destinationName = move.get("destination").toString();
            Square origin = new Square(originName);
            Square destination = new Square(destinationName);
            game.makeMove(origin, destination);
        } catch (Exception e) {

        }
    }

    public Controller(Presenter presenter) {
        this.presenter = presenter;
        this.presenter.setController(this);
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

    public boolean newGame() {
        if (playerA == null || playerB == null) {
            return false;
        } else {
            game = new Chess();
            isGameRunning = true;
            gameLoop();
            return true;
        }
    }

    public void gameLoop() {
        while(isGameRunning) {
            gameStep();
            presenter.refreshOutput();
        }
    }

    public void gameStep() {
        boolean isTurnOfPlayerA = game.isItWhitesTurn() != colorSwitch;
        if(isTurnOfPlayerA){
            executeMove(playerA.requestMove());
        } else {
            executeMove(playerB.requestMove());
        }
    }
}
