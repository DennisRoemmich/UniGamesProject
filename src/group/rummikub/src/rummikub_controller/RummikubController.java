package rummikub_controller;

import framework.GameController;
import framework.GameLog;
import framework.Player;
import org.json.simple.JSONObject;
import rummikub_game.Rummikub;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RummikubController extends GameController {

    private GameState state = GameState.STARTING;
    private Rummikub rummiGame;
    private ArrayList<PlayerInfo> playerInfos = new ArrayList<>();

    int playerNo = 4;
    int startPlayer = 0;

    private final Random rand = new Random();
    private int seed = rand.nextInt();

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


    public Rummikub getGame(){

        return rummiGame;
    }

    public GameState getGameState() {

        return state;
    }

    public List<PlayerInfo> getPlayerInfos() {

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

                successful = rummiGame.sortRackForGroup();
            }

            case SORTRUN -> {

                successful = rummiGame.sortRackForRun();
            }

            case RESET -> {

                successful = rummiGame.resetMove();
            }

            case UNDOLASTMOVE -> undoLastMove();    // wont be logged

            case STARTGAME -> {  // wont be logged, creates new gameLOg

                seed = rand.nextInt();
                newGame();
            }

        }

        /* CREATE AND SAVE JSON OBJECT */

        if ( successful ) {

            mGameLog.logMove(move.toJSON());
        }

        return successful;
    }

    private void gameEnded() {

        state = GameState.FINISHED;

        for (var i = 0; i < rummiGame.getPlayerAmount(); i++) {

            var score = rummiGame.getPlayerAt(i).getScore();
            playerInfos.get(i).setLastScore(score);
            playerInfos.get(i).addToTotalScore(score);
        }

    }

    public PlayerInfo[] getPodium() {

        var size = playerInfos.size();
        var podium = new PlayerInfo[size];

        PlayerInfo pole;
        PlayerInfo vize;
        PlayerInfo third;
        PlayerInfo looser;


        ArrayList<PlayerInfo> pI = new ArrayList<>();
        int lastBiggest = Integer.MAX_VALUE;

        for(var i = 0; i < size; i++){

            PlayerInfo currentBiggest = new PlayerInfo("poop");
            currentBiggest.setLastScore(Integer.MIN_VALUE);

            for ( var player : playerInfos ){

                if ( player.getLastScore() < lastBiggest && player.getLastScore() > currentBiggest.getLastScore() ){

                    currentBiggest = player;

                }

            }

            lastBiggest = currentBiggest.getLastScore();
            podium[i] = currentBiggest;


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

        var successful = makeMove(move);

    /*    var suc = new JSONObject();

        if (successful) {

            obj.put("successful", true);

        } else {

            obj.put("successful", false);
        }*/

    //    return suc;
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
        //default method
    }

    @Override
    public void restoreGameSettings(JSONObject gameSettings) {

    }

    @Override
    public void newGame() {


        state = GameState.RUNNING;


        mGameLog = new GameLog("ID");
        rummiGame = new Rummikub(playerNo, startPlayer, seed);

        for ( var player : mPlayers ){
            player.setController(this);
        }

    }

}

