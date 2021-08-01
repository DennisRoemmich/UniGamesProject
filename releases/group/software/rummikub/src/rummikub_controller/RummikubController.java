package rummikub_controller;

import framework.GameController;
import framework.GameLog;
import framework.Player;
import org.json.simple.JSONObject;
import rummikub_game.Rummikub;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * the rummikub controller is the central class and is intersection between gui and game engine
 */
public class RummikubController extends GameController {

    private GameState mState = GameState.STARTING;
    private Rummikub mRummiGame;
    private ArrayList<PlayerInfo> mPlayerInfos = new ArrayList<>();

    int mPlayerNo = 4;
    int mStartPlayer = 0;

    private final Random mRand = new Random();
    private int seed = mRand.nextInt();

    /* CONSTRUCTOR */

    /**
     * constructor
     */
    public RummikubController(boolean test){

        this.mPlayers = new ArrayList<Player>();

        var player1 = new PlayerInfo("Mario");
        var player2 = new PlayerInfo("Luigi");
        var player3 = new PlayerInfo("Peach");
        var player4 = new PlayerInfo("HuiBuu");
        mPlayerInfos.add(player1);
        mPlayerInfos.add(player2);
        mPlayerInfos.add(player3);
        mPlayerInfos.add(player4);

        mGameLog = new GameLog("ID");
        mRummiGame = new Rummikub(mPlayerNo, mStartPlayer, seed, test);


    }

    public RummikubController(int playerNumber, int startPlayer) {

        this.mPlayerNo = playerNumber;
        this.mStartPlayer = startPlayer;

        mGameLog = new GameLog("ID");
        mRummiGame = new Rummikub(playerNumber, startPlayer, seed, false);

    }

    /* FUNCTIONS */


    public Rummikub getGame() {

        return mRummiGame;
    }

    public GameState getGameState() {

        return mState;
    }

    public List<PlayerInfo> getPlayerInfos() {

        return mPlayerInfos;
    }

    /**
     * executes a game move
     * @param move move
     * @return true if executed, false if failed
     */
    public boolean makeMove(GameMove move) {

        /* CHECK IF MOVE IS VALID */

        var successful = false;

        switch (move.getType()) {

            case FINISHMOVE:

                successful = mRummiGame.finishMove();
                if (successful && mRummiGame.isFinished()) {

                    gameEnded();
                }
                break;

            case ONRACK:

                successful = mRummiGame.moveTileOnCurrentRack(move.getPointA(), move.getPointB());
                break;

            case ONBOARD:

                successful = mRummiGame.moveTileOnBoard(move.getPointA(), move.getPointB());
                break;

            case RACKTOBOARD:

                successful = mRummiGame.moveTileFromCurrentRackToBoard(move.getPointA(), move.getPointB());
                break;

            case BOARDTORACK:

                successful = mRummiGame.moveTileFromBoardToCurrentRack(move.getPointA(), move.getPointB());
                break;

            case SORTGROUP:

                successful = mRummiGame.sortRackForGroup();
                break;

            case SORTRUN:

                successful = mRummiGame.sortRackForRun();
                break;

            case RESET:

                successful = mRummiGame.resetMove();
                break;


            case UNDOLASTMOVE:

                undoLastMove();    // wont be logged
                break;

            case STARTGAME:   // wont be logged, creates new gameLOg

                seed = mRand.nextInt();
                newGame();
                break;

            default: break;
        }

        /* CREATE AND SAVE JSON OBJECT */

        if ( successful ) {

            mGameLog.logMove(move.toJSON());
        }

        return successful;
    }

    /**
     * handles game end
     */
    private void gameEnded() {

        mState = GameState.FINISHED;

        for (var i = 0; i < mRummiGame.getPlayerAmount(); i++) {

            var score = mRummiGame.getPlayerAt(i).getScore();
            mPlayerInfos.get(i).setLastScore(score);
            mPlayerInfos.get(i).addToTotalScore(score);
        }

    }

    /**
     * @return scores of players at end of the game
     */
    public PlayerInfo[] getPodium() {

        var size = mPlayerInfos.size();
        var podium = new PlayerInfo[size];

        int lastBiggest = Integer.MAX_VALUE;

        for(var i = 0; i < size; i++) {

            var currentBiggest = new PlayerInfo("poop");
            currentBiggest.setLastScore(Integer.MIN_VALUE);

            for ( var player : mPlayerInfos) {

                if ( player.getLastScore() < lastBiggest && player.getLastScore() > currentBiggest.getLastScore() ) {

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


        mState = GameState.RUNNING;


        mGameLog = new GameLog("ID");
        mRummiGame = new Rummikub(mPlayerNo, mStartPlayer, seed, mRummiGame.isRandomDisabled());

        for ( var player : mPlayers ) {
            player.setController(this);
        }

    }

}

