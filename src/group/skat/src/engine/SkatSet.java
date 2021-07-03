package engine;

import java.util.ArrayList;

public class SkatSet {

    private int gameAmount;
    private ArrayList<GameResult> gameResults;
    private ArrayList<SkatSetPlayer> players;

    private SkatGame currentGame;

    /* CONSTRUCTOR */

    public SkatSet(int gameAmount, String[] playerNames) {

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

    public void startNewGame() {

        if ( gameResults.size() < gameAmount || gameAmount == -1 ) {

            currentGame = new SkatGame();
            gameResults.add(currentGame.getGameResult());
        }
    }
}
