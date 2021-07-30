package engine;

import console.Print;
import controller.GameMove;
import controller.enums.ActionType;

import javax.swing.*;
import java.util.ArrayList;

public class SkatSet {

    private int mGameAmount;
    private ArrayList<GameResult> mGameResults;
    private ArrayList<SkatSetPlayer> mPlayers;

    private SkatGame mCurrentGame;

    private boolean mIsFinished;

    /* CONSTRUCTOR */

    public SkatSet(int gameAmount, String[] playerNames) {

        this.mGameAmount = gameAmount;
        mGameResults = new ArrayList<>();

        mPlayers = new ArrayList<>();
        for ( String name : playerNames ) {

            mPlayers.add(new SkatSetPlayer(name));
        }
    }

    /* GETTER */

    public SkatGame getCurrentSkatGame() {

        return mCurrentGame;
    }

    public GameResult getCurrentGameResult() {

        return mCurrentGame.getGameResult();
    }

    public boolean isFinished() {

        return mIsFinished;
    }

    public int getSkatSetPlayerAmount() {

        return mPlayers.size();
    }

    public int getGameAmount() {

        return mGameAmount;
    }

    public SkatSetPlayer getSkatSetPlayerAt(int index) {

        return mPlayers.get(index);
    }

    public SkatPlayer getSkatPlayerAt(int index) {

        return mCurrentGame.getPlayerAt(index);
    }

    public String getSkatPlayerName(int atIndex) {

        return mPlayers.get(atIndex).getName();
    }

    /** This function returns the name at a given index of the players that are actually playing right now*/
    public String getPlayingPlayerName(int atIndex) {

        return playingPlayer(currentGameNo())[atIndex].getName();
    }


    /* OTHER */


    /**
     * returns 0 if player is not part of the current game; 1 if player is part and 2 if player is playing;
     * @param atIndex
     */
    public int skatSetPlayerStatus(int atIndex) {

        var playingPlayers = playingPlayer(currentGameNo());

        for (var i = 0; i < playingPlayers.length; i++) {

            if (playingPlayers[i] == mPlayers.get(atIndex) ) {

                if (getCurrentSkatGame().getGameResult().isAborted()) {

                    return -1;
                }

                if (i == getCurrentSkatGame().getCurrentPlayer().getGameIndex()) {

                    return 2;

                }
                return 1;
            }

        }

        return 0;

    }

    public SkatSetPlayer[] currentPlayingSkatSetPlayer() {

        var gameNo = currentGameNo();

        var size = mPlayers.size();
        var modSize = gameNo % size;

        return new SkatSetPlayer[]{mPlayers.get(modSize), mPlayers.get((modSize + 1) % size), mPlayers.get((modSize + 2) % size)};
    }

    /** This function returns an array of the 3 players that are playing in the current game. It works based on the assumption that the players shift after every game, if there are more then 3*/
    private SkatSetPlayer[] playingPlayer(int gameNo) {

        var size = mPlayers.size();
        var modSize = gameNo % size;

        return new SkatSetPlayer[]{mPlayers.get(modSize), mPlayers.get((modSize + 1) % size), mPlayers.get((modSize + 2) % size)};
    }

    public int currentGameNo() {

        return mGameResults.size() - 1;
    }

    public boolean moveIsValid(GameMove move) {

        if (move.getType() == ActionType.NEW_GAME) {

            return mGameResults.size() < mGameAmount || mGameAmount == -1;

        }
        return false;
    }

    public void startNewGame() {

        if (!mGameResults.isEmpty() && mGameResults.get(mGameResults.size() - 1).isAborted()) {

            mGameResults.remove(mGameResults.size() - 1);
        }

        if (mGameResults.size() < mGameAmount || mGameAmount == -1) {

            mCurrentGame = new SkatGame();
            mGameResults.add(mCurrentGame.getGameResult());

            Print.debug("INFO", "\n  NEW GAME OBJECT!");
        }


    }

    public void abortGame() {

        Print.debug("MAIK", "Game aborted - new Game started");

    //    mGameResults.remove(mGameResults.size() - 1);

    }


    public void gameIsFinished() {

        Print.debug("MAIK", getPlayingPlayerName(getCurrentGameResult().getDeclarerIndex()) + " gets " + getCurrentGameResult().getGameValue());

        updatePoints();

        if (mGameResults.size() == mGameAmount) {

            mIsFinished = true;
        }
    }

    private void updatePoints() {

        var played = playingPlayer(currentGameNo());
        for (var i = 0; i < played.length; i++) {

            played[i].addToScore(getSkatPlayerAt(i).getFinalScore());
        }

    }

    public void printSkatSetStats() {

        for (SkatSetPlayer player : mPlayers) {

            Print.debug("MAIK", player.getName() + ": " + player.getTotalScore());
        }
    }
}
