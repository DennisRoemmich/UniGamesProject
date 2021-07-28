package controller;

import console.Print;
import controller.enums.ActionType;
import engine.SkatGame;
import engine.SkatSet;
import engine.enums.GamePhase;
import framework.GameController;
import framework.Player;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class SkatController extends GameController {

    private int gameAmount;
    private SkatSet skatSet;
    private String[] playerNames;
    private int lastCurrentPlayer = -1;

    /* CONSTRUCTOR */

    public SkatController(int gameAmount, ArrayList<String> nameList) {

        String[] names = nameList.toArray(new String[0]);

        this.gameAmount = gameAmount;
        this.playerNames = names;
        skatSet = new SkatSet(gameAmount, names);


    }

    /* GETTER */

    public SkatGame getGame(){

        return skatSet.getCurrentSkatGame();
    }

    public String[] getPlayerNames() {
        return playerNames;
    }



    /* OTHER */

    public boolean makeMove(GameMove move) {

        if (!move.getType().isSkatMove() && moveIsValid(move)) {

            if (move.getType() == ActionType.NEW_SET) {

                skatSet = new SkatSet(gameAmount, playerNames);
                checkPlayerSwitched();
                return true;
            }

            if (move.getType() == ActionType.NEW_GAME) {

                skatSet.startNewGame();
                checkPlayerSwitched();
                return true;
            }

        } else {

            if (skatSet.getCurrentSkatGame().makeSkatMove((SkatMove) move)) {

                if (skatSet.getCurrentGameResult().isAborted()) {

                    skatSet.abortGame();

                } else if (skatSet.getCurrentGameResult().isFinished()) {

                    Print.debug("MAIK", "GAME IS FINISHED");
                    skatSet.gameIsFinished();

                    if (skatSet.isFinished()) {

                        Print.debug("MAIK", "\n  SET IS FINISHED");

                        skatSet.printSkatSetStats();
                    }
                }
                checkPlayerSwitched();
                return true;
            }
        }
        checkPlayerSwitched();
        return false;
    }


    private void checkPlayerSwitched() {

        var index = getGame().getCurrentPlayer().getGameIndex();

        if ( lastCurrentPlayer != index ){

            lastCurrentPlayer = index;
            messageNextPlayer();

        }



    }

    private void messageNextPlayer(){

        JSONObject obj = new JSONObject();
        var activatePlayerAt = -1;

        for (var i = 0; i < mPlayers.size(); i++ ){

            var player = mPlayers.get(i);

            if ( skatSet.skatSetPlayerStatus(i) == 2 ){
                activatePlayerAt = i;
            } else {
                obj.put("YOURMOVE", "FALSE");
                player.requestMove(obj);
            }

        }

        if ( activatePlayerAt != -1 ){
            obj.put("YOURMOVE", "TRUE");
            mPlayers.get(activatePlayerAt).requestMove(obj);
        } else {
            Print.debug("ERROR", "Something went wrong, no player is playing.");
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


    public void addPlayer(Player player){

        if ( getGame().getGamePhase() == GamePhase.NOT_STARTED ){

            mPlayers.add(player);

            if (mPlayers.size() == 1) { // first player was added

                JSONObject obj = new JSONObject();
                obj.put("YOURMOVE", "TRUE");
                player.requestMove(obj);

            }


        } else {

            Print.debug("WARNING", "Cannot add player in this Gamephase!");

        }



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
