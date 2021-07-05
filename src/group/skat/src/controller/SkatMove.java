package controller;

import controller.enums.ActionType;
import engine.Card;
import engine.Trump;
import framework.GameController;
import org.json.simple.JSONObject;

import java.awt.*;

public class SkatMove extends GameController {

    private ActionType type;

    public Card Card1;
    public Card Card2;

    public Trump trump;

    public Card card;
    public int index;

    /* CONSTRUCTOR */

    public SkatMove() {


    }

    /* GETTER */

    public ActionType getType() {

        return type;
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
