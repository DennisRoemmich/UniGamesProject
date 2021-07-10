package controller;

import console.Print;
import controller.enums.ActionType;
import engine.SkatGame;
import engine.SkatSet;
import framework.GameController;
import org.json.simple.JSONObject;

public class SkatController extends GameController {

    private int gameAmount;
    private SkatSet skatSet;
    private String[] playerNames;

    /* CONSTRUCTOR */

    public SkatController(int gameAmount, String[] names) {


        this.gameAmount = gameAmount;
        this.playerNames = names;
        skatSet = new SkatSet(gameAmount, names);

    }

    /* GETTER */

    public SkatGame getGame(){

        return skatSet.getCurrentSkatGame();
    }

    /* OTHER */

    public boolean makeMove(GameMove move) {

        if (!move.getType().isSkatMove()) {

            if (move.getType() == ActionType.NEW_SET && moveIsValid(move)) {

                skatSet = new SkatSet(gameAmount, playerNames);
                return true;
            }

            if (move.getType() == ActionType.NEW_GAME && moveIsValid(move)) {

                skatSet.startNewGame();
                return true;
            }
            return false;

        } else {

            if (skatSet.getCurrentSkatGame().makeSkatMove((SkatMove) move)) {

                if (skatSet.getCurrentGameResult().isAborted()) {

                    skatSet.abortGame();

                } else if (skatSet.getCurrentGameResult().isFinished()) {

                    Print.debug("MAIK", "GAME IS FINISHED");
                    skatSet.gameIsFinished();

                } else {

                    return true;
                }
            }
            return false;
        }
    }

    public boolean moveIsValid(GameMove move) {

        return switch (move.getType()) {

            case NEW_SET -> false; // ??
            case NEW_GAME -> skatSet.moveIsValid(move);
            default -> skatSet.getCurrentSkatGame().moveIsValid((SkatMove) move);
        };
    }

    public SkatSet getSkatSet() {
        return skatSet;
    }

    /* OVERRIDE */

    @Override
    protected JSONObject executeMove(JSONObject jsnMove) {

        makeMove(new SkatMove(jsnMove));

        return null;
    }

    @Override
    public JSONObject metaSettingsToJSON() {
        return null;
    }

    @Override
    public JSONObject gameSettingsToJSON() {
        return null;
    }

    @Override
    public void restoreMetaSettings(JSONObject metaSettings) {

    }

    @Override
    public void restoreGameSettings(JSONObject gameSettings) {

    }

    @Override
    public void newGame() {

    }
}
