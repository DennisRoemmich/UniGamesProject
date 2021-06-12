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

    private final Rummikub rummiGame = new Rummikub(4, 0);

    private ArrayList<Player> players = new ArrayList<>();

    public RummikubController(){

        moveLog = new GameLog();

    }

    public void addPlayer(Player player){
        players.add(player);
        player.setGameClass(rummiGame);
    }

    public void startGameLoop(){

        ActionType currentAction = null;

        do {

            for (var player : players){

                boolean result = makeMove(player.getGameMove());

                if (!result) {
                    System.out.print("\nThis move is not possible.\n\n");
                }

            }

        } while (currentAction != ActionType.QUIT);

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

        }

        /* CREATE AND SAVE JSON OBJECT */

        if ( successful ) {

            moveLog.logMove(move.toJSON());

        }

        return successful;

    }


    @Override
    public void executeMove(JSONObject obj) {

        ActionType type = ActionType.valueOf((String) obj.get("actionType"));

        if (type.usesPoints()) {



        }


    }

    @Override
    public void resetGame() {

    }

    @Override
    public void addAIPlayer() {

    }

    @Override
    public JSONObject metaSettingsToJSON() {
        return null;
    }

    @Override
    public JSONObject gameSettingsToJSON() {
        return null;
    }

}

enum GameState {
    STARTED,
    RUNNING,
    ENDED;
}

