package controller;

import engine.SkatGame;
import engine.SkatSet;
import framework.GameController;
import org.json.simple.JSONObject;

public class SkatController extends GameController {

    private int gameAmount;
    private SkatSet skatSet;

    public SkatController(int gameAmount, String[] names) {


        this.gameAmount = gameAmount;
        skatSet = new SkatSet(gameAmount, names);

    }

    public SkatGame getGame(){

        return skatSet.getSkatGame();

    }

    public void makeMove(SkatMove move) {

        switch ( move.getType() ) {

            case NEW_SET -> skatSet = new SkatSet(gameAmount, new String[]{"Räuber Hotzenplotz", "Schneewitchen", "Rotkäppchen"});
        //    case ON_HAND ->
            case NEW_GAME, SORT, RAISE_OR_ACCEPT, PASS, SKAT_TO_HAND, HAND_TO_SKAT, DROP_SKAT, SET_TRUMP, PLAY_CARD -> {

                if ( moveIsValid(move) ) {

                    skatSet.exeMove(move);
                }
            }
            default -> System.out.println("MASSIVE ERROR! - SkatController");
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

    @Override
    protected JSONObject executeMove(JSONObject move) {
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
