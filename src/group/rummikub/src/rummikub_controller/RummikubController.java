package rummikub_controller;

import framework.GameController;
import framework.GameLog;
import framework.Player;
import org.json.simple.JSONObject;
import rummikub_game.Rummikub;

import java.util.ArrayList;
import java.util.Random;

public class RummikubController extends GameController {

    private GameState state = GameState.STARTED;
    private Rummikub rummiGame;

    int playerNo = 4;
    int startPlayer = 0;

    private final int seed = new Random().nextInt();

    /* CONSTRUCTOR */

    public RummikubController(){

        this.mPlayers = new ArrayList<Player>();

        mGameLog = new GameLog("ID");
        rummiGame = new Rummikub(playerNo, startPlayer, seed);


    }

    public RummikubController(int playerNumber, int startPlayer){

        this.playerNo = playerNumber;
        this.startPlayer = startPlayer;

        mGameLog = new GameLog("ID");
        rummiGame = new Rummikub(playerNumber, startPlayer, seed);

    }

    /* FUNCTIONS */

    public void startGame(){

        // Start Game View with button "Start"?


            var currentPlayerIdx = rummiGame.getCurrentPlayerIndex();
            mPlayers.get(currentPlayerIdx).requestMove(new JSONObject());



    }


    public Rummikub getGame(){

        return rummiGame;
    }

    public boolean makeMove(GameMove move){

        /* CHECK IF MOVE IS VALID */

        var successful = false;

        switch (move.type){

            case FINISHMOVE -> successful = rummiGame.finishMove();

            case ONRACK -> successful = rummiGame.moveTileOnCurrentRack(move.pointA, move.pointB);

            case ONBOARD -> successful = rummiGame.moveTileOnBoard(move.pointA, move.pointB);

            case RACKTOBOARD -> successful = rummiGame.moveTileFromCurrentRackToBoard(move.pointA, move.pointB);

            case BOARDTORACK -> successful = rummiGame.moveTileFromBoardToCurrentRack(move.pointA, move.pointB);

            case SORTGROUP -> {

                if ( state == GameState.RUNNING || true ) {

                    successful = rummiGame.sortRackForGroup();
                }
            }

            case SORTRUN -> {

                if ( state == GameState.RUNNING || true) {

                    successful = rummiGame.sortRackForRun();
                }
            }

            case RESET -> {

                rummiGame.resetMove();
            }

            case UNDOLASTMOVE -> undoLastMove();
        }

        /* CREATE AND SAVE JSON OBJECT */

        if ( successful ) {

            mGameLog.logMove(move.toJSON());
            System.out.println("Move was logged!");
        }

        return successful;
    }



    // @Override
    public void addPlayer(Player player) {

        mPlayers.add(player);
        player.setController(this);
    }


    /* Override */

    @Override
    public JSONObject executeMove(JSONObject obj) {

        var move = new GameMove(obj);

        makeMove(move);

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

        mGameLog = new GameLog("ID");
        rummiGame = new Rummikub(playerNo, startPlayer, seed);

        for ( var player : mPlayers ){
            player.setController(this);
        }

    }

}

enum GameState {
    STARTED,
    RUNNING,
    ENDED;
}

