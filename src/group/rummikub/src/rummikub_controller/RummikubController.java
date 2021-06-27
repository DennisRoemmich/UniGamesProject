package rummikub_controller;

import framework.GameController;
import framework.GameLog;
import framework.Player;
import org.json.simple.JSONObject;
import rummikub_game.Rummikub;
import rummikub_game.RummikubPlayer;

import java.util.ArrayList;
import java.util.Random;

public class RummikubController extends GameController {

    private GameState state = GameState.STARTED;
    private Rummikub rummiGame;
    private ArrayList<PlayerInfo> playerInfos = new ArrayList<>();

    int playerNo = 4;
    int startPlayer = 0;

    private final int seed = new Random().nextInt();

    /* CONSTRUCTOR */

    public RummikubController(){

        this.mPlayers = new ArrayList<Player>();

        PlayerInfo player1 = new PlayerInfo("Mario");
        PlayerInfo player2 = new PlayerInfo("Luigi");
        PlayerInfo player3 = new PlayerInfo("Peach");
        PlayerInfo player4 = new PlayerInfo("HuiBuu");
        playerInfos.add(player1);
        playerInfos.add(player2);
        playerInfos.add(player3);
        playerInfos.add(player4);

        var standardPlayerNo = 4;
        var standardStartPlayer = 0;

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

    public GameState getGameState() {

        return state;
    }

    public ArrayList<PlayerInfo> getPlayerInfos() {

        return playerInfos;
    }

    public boolean makeMove(GameMove move){

        /* CHECK IF MOVE IS VALID */

        var successful = false;

        switch (move.type){

            case FINISHMOVE -> {

                successful = rummiGame.finishMove();
                if (successful && rummiGame.isFinished()) {

                    gameEnded();
                }
            }

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

    private void gameEnded() {

        for (var i = 0; i < rummiGame.getPlayerAmount(); i++) {

            var score = rummiGame.getPlayerAt(i).getScore();
            playerInfos.get(i).setLastScore(score);
            playerInfos.get(i).addToTotalScore(score);
        }
    }

    public PlayerInfo[] getPodium() {

        var size = playerInfos.size();
        PlayerInfo[] podium = new PlayerInfo[size];

        PlayerInfo pole;
        PlayerInfo vize;
        PlayerInfo third;
        PlayerInfo looser;

        if (size == 2) {

            if (playerInfos.get(0).getLastScore() > playerInfos.get(1).getLastScore()) {

                pole = playerInfos.get(0);
                vize = playerInfos.get(1);

            } else {

                vize = playerInfos.get(0);
                pole = playerInfos.get(1);
            }

        } else if (size == 3) {



        } else if (size == 4) {


        }

        return podium;
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

