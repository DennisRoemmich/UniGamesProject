package engine;

import console.Print;
import controller.GameMove;
import controller.enums.ActionType;

import java.util.ArrayList;

public class SkatSet {

    private int gameAmount;
    private ArrayList<GameResult> gameResults;
    private ArrayList<SkatSetPlayer> players;

    private SkatGame currentGame;

    private boolean isFinished;

    /* CONSTRUCTOR */

    public SkatSet(int gameAmount, String[] playerNames) {

        this.gameAmount = gameAmount;
        gameResults = new ArrayList<GameResult>();

        players = new ArrayList<SkatSetPlayer>();
        for ( String name : playerNames ) {

            players.add(new SkatSetPlayer(name));
        }
    }

    /* GETTER */

    public SkatGame getCurrentSkatGame() {

        return currentGame;
    }

    public GameResult getCurrentGameResult() {

        return currentGame.getGameResult();
    }

    public boolean isFinished() {

        return isFinished;
    }

    public int getSkatSetPlayerAmount() {

        return players.size();
    }

    public int getGameAmount() {

        return gameAmount;
    }

    public SkatSetPlayer getSkatSetPlayerAt(int index) {

        return players.get(index);
    }

    public SkatPlayer getSkatPlayerAt(int index) {

        return currentGame.getPlayerAt(index);
    }

    public String getSkatPlayerName(int atIndex){

        return players.get(atIndex).getName();
    }

    /** This function returns the name at a given index of the players that are actually playing right now*/
    public String getPlayingPlayerName(int atIndex){

        return playingPlayer(currentGameNo())[atIndex].getName();
    }


    /* OTHER */

    public SkatSetPlayer[] currentSkatSetPlayer(){

        var gameNo = currentGameNo();

        var size = players.size();
        var modSize = gameNo % size;

        return new SkatSetPlayer[]{players.get(modSize), players.get((modSize + 1) % size), players.get((modSize + 2) % size)};
    }

    /** This function returns an array of the 3 players that are playing in the current game. It works based on the assumption that the players shift after every game, if there are more then 3*/
    private SkatSetPlayer[] playingPlayer(int gameNo){

        var size = players.size();
        var modSize = gameNo % size;

        return new SkatSetPlayer[]{players.get(modSize), players.get((modSize + 1) % size), players.get((modSize + 2) % size)};
    }

    public int currentGameNo() {

        return gameResults.size() - 1;
    }

    public boolean moveIsValid(GameMove move) {

        if (move.getType() == ActionType.NEW_GAME) {

            return gameResults.size() < gameAmount || gameAmount == -1;

        }
        return false;
    }

    // TODO: comment: new Game started
    public void startNewGame() {

        if (gameResults.size() < gameAmount || gameAmount == -1) {

            currentGame = new SkatGame();
            gameResults.add(currentGame.getGameResult());
        }

        Print.debug("MAIK", "\n  NEW GAME STARTED");
    }

    // TODO: comment: game aborted
    public void abortGame() {

        Print.debug("MAIK", "Game aborted - new Game started");

        gameResults.remove(gameResults.size() - 1);

    //    startNewGame();
    }

    // TODO: comment: finalScore
    public void gameIsFinished() {

        Print.debug("MAIK", getPlayingPlayerName(getCurrentGameResult().getDeclarerIndex()) + " gets " + getCurrentGameResult().getGameValue());

        updatePoints();

        if (gameResults.size() == gameAmount) {

            isFinished = true;
        }
    }

    private void updatePoints() {

        var played = playingPlayer(currentGameNo());
        for (var i = 0; i < played.length; i++) {

            played[i].addToScore(getSkatPlayerAt(i).getFinalScore());
        }

    }

    public void printSkatSetStats() {

        for (SkatSetPlayer player : players) {

            Print.debug("MAIK", player.getName() + ": " + player.getTotalScore());
        }
    }
}
