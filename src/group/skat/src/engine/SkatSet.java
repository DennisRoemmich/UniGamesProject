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

        startNewGame();
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


    /* OTHER */

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
