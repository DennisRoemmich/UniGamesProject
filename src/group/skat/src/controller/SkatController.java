package controller;

import console.Print;
import controller.enums.ActionType;
import engine.SkatGame;
import engine.SkatSet;
import engine.enums.GamePhase;
import framework.GameController;
import framework.Player;
import framework.Presenter;
import javafx.FXPresenter;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * The SkatController is the central engine of the game logic
 */
public class SkatController extends GameController {

    private int mGameAmount;
    private SkatSet mSkatSet;
    private String[] mPlayerNames;
    private int mLastCurrentPlayer = -1;
    int guiPlayerIndex;

    /* CONSTRUCTOR */

    public SkatController(int gameAmount, ArrayList<String> nameList) {

        String[] names = nameList.toArray(new String[0]);

        this.mPlayers = new ArrayList<>();

        this.mGameAmount = gameAmount;
        this.mPlayerNames = names;
        mSkatSet = new SkatSet(gameAmount, names);

    }

    /* GETTER */

    public SkatGame getGame() {

        return mSkatSet.getCurrentSkatGame();
    }

    public String[] getPlayerNames() {

        return mPlayerNames;
    }



    /* OTHER */

    /**
     * executes a given move
     * @param move move
     * @return true if done, false if failed
     */
    public boolean makeMove(GameMove move) {

        if (!moveIsValid(move)){
            Print.debug("INFO", "A false move was entered :" + move.toJSON().toString());
            Print.debug("INFO","Break");
        }



        if (!move.getType().isSkatMove() && moveIsValid(move)) {

            // NEW_SET or NEW_GAME
            if (makeMoveHelp(move)) {

                return true;
            }

        } else {

            if (mSkatSet.getCurrentSkatGame().makeSkatMove((SkatMove) move)) {

                if (mSkatSet.getCurrentGameResult().isAborted()) {

                    mSkatSet.abortGame();

                } else if (mSkatSet.getCurrentGameResult().isFinished()) {

                    Print.debug("MAIK", "GAME IS FINISHED");
                    mSkatSet.gameIsFinished();

                    guiPlayersTurn();

                    if (mSkatSet.isFinished()) {

                        Print.debug("MAIK", "\n  SET IS FINISHED");

                        mSkatSet.printSkatSetStats();
                    }
                }
                messageNextPlayer();
                return true;
            }
        }
        messageNextPlayer();
        return false;
    }

    private boolean makeMoveHelp(GameMove move) {

        if (move.getType() == ActionType.NEW_SET) {

            mSkatSet = new SkatSet(mGameAmount, mPlayerNames);
            messageNextPlayer();
            return true;
        }

        if (move.getType() == ActionType.NEW_GAME) {

            mSkatSet.startNewGame();
            messageNextPlayer();
            return true;
        }

        return false;
    }

    private void guiPlayersTurn(){

        var obj = new JSONObject();
        var yourMove = "YOURMOVE";

        for (var i = 0; i < mPlayers.size(); i++ ) {

            var player = mPlayers.get(i);

            if (i == guiPlayerIndex){
                obj.put(yourMove, "TRUE");
            } else {
                obj.put(yourMove, "FALSE");
            }
                player.requestMove(obj);

        }

    }

    private void messageNextPlayer() {

        var obj = new JSONObject();
        var activatePlayerAt = -1;
        var yourMove = "YOURMOVE";

        for (var i = 0; i < mPlayers.size(); i++ ) {

            var player = mPlayers.get(i);

            if ( mSkatSet.skatSetPlayerStatus(i) == 2 ) {
                activatePlayerAt = i;
            } else {
                obj.put(yourMove, "FALSE");
                player.requestMove(obj);
            }
        }

        if (getGame().getGamePhase() == GamePhase.ENDED || getGame().getGamePhase() == GamePhase.ABORTED) {

            obj.put(yourMove, "FALSE");
            mPlayers.get((guiPlayerIndex + 1) % 3).requestMove(obj);
            mPlayers.get((guiPlayerIndex + 2) % 3).requestMove(obj);
            obj.put(yourMove, "TRUE");
            mPlayers.get(guiPlayerIndex).requestMove(obj);

            return;
        }

        if ( activatePlayerAt != -1 ) {
            obj.put(yourMove, "TRUE");
            mPlayers.get(activatePlayerAt).requestMove(obj);
        } else {
            Print.debug("ERROR", "Something went wrong, no player is playing.");
        }

    }

    /**
     * checks whether an given move is possible
     * @param move move
     * @return true if possible, false if not
     */
    public boolean moveIsValid(GameMove move) {

        return switch (move.getType()) {

            case NEW_SET -> false; // ??
            case NEW_GAME -> mSkatSet.moveIsValid(move);
            default -> mSkatSet.getCurrentSkatGame().moveIsValid((SkatMove) move);
        };
    }

    public SkatSet getSkatSet() {

        return mSkatSet;
    }

    public int getCurPlayerNo(){
        return mPlayers.size();
    }

    public void addPlayer(Player player) {

        var firstMove = true;

        if (getGame() == null || getGame().getGamePhase() == GamePhase.NOT_STARTED) {

            mPlayers.add(player);

            if (firstMove) { // first gui player was added?

                guiPlayerIndex = mPlayers.size() - 1;
                var obj = new JSONObject();
                obj.put("YOURMOVE", "TRUE");
                var ret = player.requestMove(obj);
                firstMove = ret == null;

            }

        } else {

            Print.debug("WARNING", "Cannot add player in this Gamephase!");

        }
    }


    /* OVERRIDE */

    @Override
    protected JSONObject executeMove(JSONObject jsnMove) {



        Print.debug("INFO", "Move not valid");

        return null;
    }

    @Override
    public JSONObject metaSettingsToJSON() {

        return null;

        /*
         * this function is not needed to be overwritten in this game
         */
    }

    @Override
    public JSONObject gameSettingsToJSON() {

        return null;

        /*
         * this function is not needed to be overwritten in this game
         */
    }

    @Override
    public void restoreMetaSettings(JSONObject metaSettings) {

        /*
         * this function is not needed to be overwritten in this game
         */
    }

    @Override
    public void restoreGameSettings(JSONObject gameSettings) {

        /*
         * this function is not needed to be overwritten in this game
         */
    }

    @Override
    public void newGame() {

        /*
         * this function is not needed to be overwritten in this game
         */
    }
}
