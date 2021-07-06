package controller;

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

        return skatSet.getSkatGame();

    }

    /* OTHER */

    public void forwardMove(GameMove move) {

        if ( !move.getType().isSkatMove() ){

            if(move.getType() == ActionType.NEW_SET) {
                skatSet = new SkatSet(gameAmount, playerNames);
            }

            if(move.getType() == ActionType.NEW_GAME) {
                skatSet.startNewGame();
            }


        } else {

            skatSet.makeMove((SkatMove) move);

        }

    }

    public boolean moveIsValid(SkatMove move) {

        return switch ( move.getType() ) {

            case NEW_SET -> false; // ??
        //    case SORT -> true;
            case ON_HAND -> true;
            default -> skatSet.moveIsValid(move);
        };
    }



    /* OVERRIDE */

    @Override
    protected JSONObject executeMove(JSONObject jsnMove) {

        forwardMove(new SkatMove(jsnMove));

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
