package rummikub_controller;

import framework.GameController;
import framework.GameLog;
import framework.Player;
import org.json.simple.JSONObject;
import rummikub_game.Rummikub;

import java.awt.*;
import java.util.ArrayList;

public class RummikubController extends GameController {

    private GameState state = GameState.STARTED;
    private Rummikub rummiGame;

    /* CONSTRUCTOR */

    public RummikubController(){

        this.mPlayers = new ArrayList<Player>();

        var standardPlayerNo = 4;
        var standardStartPlayer = 0;

        mGameLog = new GameLog("ID");
        rummiGame = new Rummikub(standardPlayerNo, standardStartPlayer);

    }

    public RummikubController(int playerNumber, int startPlayer){

        mGameLog = new GameLog("ID");
        rummiGame = new Rummikub(playerNumber, startPlayer);

    }

    /* FUNCTIONS */

    public void startGame(){

        // Start Game View with button "Start"?


            var currentPlayerIdx = rummiGame.getCurrentPlayerIndex();
            mPlayers.get(currentPlayerIdx).requestMove(new JSONObject());



    }


    public void gameLoop(){

    }

    public Rummikub getGame(){
        return this.rummiGame;
    }

    public boolean makeMove(GameMove move){

        /* CHECK IF MOVE IS VALID */

        var successful = false;

        switch (move.type){

            case FINISHMOVE -> successful = rummiGame.finishMove();

            case ONRACK -> successful = rummiGame.getCurrentPlayer().getSketchRack().moveTile(move.pointA, move.pointB);

            case ONBOARD -> successful = rummiGame.getSketchBoard().moveTile(move.pointA, move.pointB);

            case RACKTOBOARD -> successful = rummiGame.moveTileFromCurrentRackToBoard(move.pointA, move.pointB);

            case SORTGROUP -> {

                if ( state == GameState.RUNNING ) {

                    rummiGame.getCurrentPlayer().getSketchRack().sortForGroup();
                    successful = true;

                }

            }

            case SORTRUN -> rummiGame.getCurrentPlayer().getSketchRack().sortForRun();

            case RESET -> {

                rummiGame.resetMove();

            }

            case BOARDTORACK -> System.out.print("TO IMPLEMENT");// rummiGame.moveTileFromCurrentBoardToRack();

            case UNDOLASTMOVE -> undoLastMove();

        }

        /* CREATE AND SAVE JSON OBJECT */

        if ( successful ) {

            mGameLog.logMove(move.toJSON());

        }

        return successful;

    }



    // @Override
    public void addPlayer(Player player){
        mPlayers.add(player);
        player.setController(this);
    }


    private void printDebugState(){

        System.out.print(rummiGame.getCurrentPlayer().getSketchRack().toString());

    }

    /* Override */

    @Override
    public JSONObject executeMove(JSONObject obj) {

        var move = new GameMove(obj);

        makeMove(move);

        printDebugState();

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

enum GameState {
    STARTED,
    RUNNING,
    ENDED;
}

