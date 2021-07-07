package engine;

import controller.GameMove;
import controller.SkatMove;
import controller.enums.ActionType;
import console.Print;

import java.util.ArrayList;

public class SkatSet {

    private int gameAmount;
    private ArrayList<GameResult> gameResults;
    private ArrayList<SkatSetPlayer> players;

    private SkatGame currentGame;

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

    public SkatSetPlayer getSkatSetPlayerAt(int index) {

        return players.get(index);
    }

    public SkatPlayer getSkatPlayer(int index) {

        return currentGame.getPlayerAt(index);
    }

    public String getSkatPlayerName(int atIndex){

        return players.get(atIndex).getName();

    }

    /** This funtion returns the name at a given index of the players that are actually playing right now*/
    public String getPlayingPlayerName(int atIndex){

        return playingPlayer(getCurrentGameNo())[atIndex].getName();
    }


    /* OTHER */

    /** This function returns an array of the 3 players that are playing in the current game. It works based on the assumption that the players shift after every game, if there are more then 3*/
    private SkatSetPlayer[] playingPlayer(int forGameNo){

        var mod3 = forGameNo % 3;
        var size = players.size();

        return new SkatSetPlayer[]{players.get(mod3 % size), players.get((mod3 + 1) % size), players.get((mod3 + 2) % size)};

    }

    public int getCurrentGameNo(){
        return gameResults.size()-1;
    }

    public boolean moveIsValid(GameMove move) {

        if ( move.getType() == ActionType.NEW_GAME ) {

            return gameResults.size() < gameAmount || gameAmount == -1;

        }
        return false;
    }

    public void startNewGame() {

        if ( gameResults.size() < gameAmount || gameAmount == -1 ) {

            currentGame = new SkatGame();
            gameResults.add(currentGame.getGameResult());
        }
    }

}
