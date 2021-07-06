package engine;

import controller.SkatMove;
import controller.enums.ActionType;
import test.Print;

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

    public SkatGame getSkatGame() {

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

    public boolean moveIsValid(SkatMove move) {

        if ( move.getType() == ActionType.NEW_GAME ) {

            return gameResults.size() < gameAmount || gameAmount == -1;

        } else {

            return currentGame.moveIsValid(move);
        }
    }

    /**
     * TODO : Einfacher Index bei Playcard
     * TODO : SkatMove subclass of GameMove
     * TODO : Discuss : move that in SkatClass? Maybe differ between GameMove and SkatMove? SkatMove as subclass?
     */
    public void makeMove(SkatMove move) {

        switch ( move.getType() ) {

            case NEW_GAME -> startNewGame();
            case SORT -> currentGame.sort();
            case RAISE_OR_ACCEPT -> currentGame.raiseOrAcceptBid();
            case PASS -> currentGame.passBid();
            case SKAT_TO_HAND -> currentGame.moveCardFromSkatToHand(new Card(null, null), -1);
            case HAND_TO_SKAT -> currentGame.moveCardFromHandToSkat(new Card(null, null), -1);
            case DROP_SKAT -> currentGame.dropSkat();
            case SET_TRUMP -> currentGame.setTrump(move.trump);
            case PLAY_CARD -> currentGame.playCard(new Card(null, null));
            default -> Print.debug("ERROR", "in SkatSet -> exeMove");
        }
    }

    public void startNewGame() {

        if ( gameResults.size() < gameAmount || gameAmount == -1 ) {

            currentGame = new SkatGame();
            gameResults.add(currentGame.getGameResult());
        }
    }

}
