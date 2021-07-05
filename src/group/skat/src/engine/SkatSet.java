package engine;

import controller.SkatMove;
import controller.enums.ActionType;

import java.util.ArrayList;

public class SkatSet {

    private int gameAmount;
    private ArrayList<GameResult> gameResults;
    private ArrayList<SkatSetPlayer> players;

    private SkatGame currentGame;

    /* CONSTRUCTOR */

    public SkatSet(int gameAmount, String[] playerNames) {

        players = new ArrayList<SkatSetPlayer>();
        gameResults = new ArrayList<GameResult>();

        this.gameAmount = gameAmount;

        for ( String name : playerNames ) {

            players.add(new SkatSetPlayer(name));
        }

        startNewGame();
    }

    /* GETTER */

    public SkatGame getSkatGame() {

        return currentGame;
    }

    public SkatSetPlayer getSkatSetPlayerAt(int index) {

        return players.get(index);
    }

    public SkatPlayer getSkatPlayer(int index) {

        return currentGame.getPlayerAt(index);
    }

    /* ELSE */

    public boolean moveIsValid(SkatMove move) {

        if ( move.getType() == ActionType.NEW_GAME ) {

            return gameResults.size() < gameAmount || gameAmount == -1;

        } else {

            return currentGame.moveIsValid(move);
        }
    }

    public void startNewGame() {

        if ( gameResults.size() < gameAmount || gameAmount == -1 ) {

            currentGame = new SkatGame();
            gameResults.add(currentGame.getGameResult());
        }
    }
}
