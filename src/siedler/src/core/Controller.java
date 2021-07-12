package core;

import framework.GameController;
import org.json.simple.JSONObject;

public class Controller extends GameController {



    public int getNumberOfPlayers() {
        return mPlayers.size();
    }

    @Override
    protected JSONObject executeMove(JSONObject move) {
        return null;
    }

    @Override
    public JSONObject metaSettingsToJSON() {
        JSONObject metaSettings = new JSONObject();
        return metaSettings;
    }

    @Override
    public JSONObject gameSettingsToJSON() {
        JSONObject gameSettings = new JSONObject();
        return gameSettings;
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
