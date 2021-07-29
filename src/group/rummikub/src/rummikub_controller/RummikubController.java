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

        var player1 = new PlayerInfo("Mario");
        var player2 = new PlayerInfo("Luigi");
        var player3 = new PlayerInfo("Peach");
        var player4 = new PlayerInfo("HuiBuu");
        playerInfos.add(player1);
        playerInfos.add(player2);
        playerInfos.add(player3);
        playerInfos.add(player4);

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

        switch (move.getType()){

            case FINISHMOVE -> {

                successful = rummiGame.finishMove();
                if (successful && rummiGame.isFinished()) {

                    gameEnded();
                }
            }

            case ONRACK -> successful = rummiGame.moveTileOnCurrentRack(move.getPointA(), move.getPointB());

            case ONBOARD -> successful = rummiGame.moveTileOnBoard(move.getPointA(), move.getPointB());

            case RACKTOBOARD -> successful = rummiGame.moveTileFromCurrentRackToBoard(move.getPointA(), move.getPointB());

            case BOARDTORACK -> successful = rummiGame.moveTileFromBoardToCurrentRack(move.getPointA(), move.getPointB());

            case SORTGROUP -> successful = rummiGame.sortRackForGroup();

            case SORTRUN -> successful = rummiGame.sortRackForRun();

            case RESET -> successful = rummiGame.resetMove();


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

        int lastBiggest = Integer.MAX_VALUE;

        for(var i = 0; i < size; i++){

            var currentBiggest = new PlayerInfo("poop");
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

    public void addPlayer(Player player) {

        mPlayers.add(player);
        player.setController(this);
    }


    /* Override */

    @Override
    public JSONObject executeMove(JSONObject obj) {
        //default
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
        //default method
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

