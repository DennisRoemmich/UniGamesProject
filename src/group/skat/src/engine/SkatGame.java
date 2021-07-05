package engine;

import controller.SkatMove;
import engine.enums.CardColor;
import engine.enums.CardValue;
import engine.enums.GameMode;
import engine.enums.GamePhase;

import java.util.ArrayList;
import java.util.Random;

public class SkatGame {

    private GamePhase gamePhase;
    private SkatPlayer[] players;

    private ArrayList<Card> cardStack;

    private Card[] skat;
    private Trick currentTrick;

    private Auction auction;

    private SkatPlayer declarer;
    private GameMode gameMode;
    private Trump trump;

    private int currentPlayerIndex;
    private int currentRoundNo;
    private int currentLeaderIndex;

    private GameResult result;

    private Random rand = new Random();

    /* CONSTRUCTOR */

    public SkatGame() {

        gamePhase = GamePhase.AUCTION;

        players = new SkatPlayer[3];

        for ( var i = 0; i < players.length; i++ ) {

            players[i] = new SkatPlayer(trump);
        }

        cardStack = new ArrayList<>();

        skat = new Card[2];

        auction = new Auction(players);

        trump = new Trump();

        currentRoundNo = -1;

        result = new GameResult(players, declarer, trump);

        createCardStack();
        dealCards();
    }

    /* GETTER */

    public SkatPlayer getPlayerAt(int index) {

        return players[index];
    }

    public SkatPlayer getCurrentPlayer() {

        if ( currentTrick == null ){
            return players[0];
        }

        var i = currentLeaderIndex + currentTrick.getSize();

        return players[i % players.length];
    }

    public int getCurrentRoundNo() {

        return currentRoundNo;
    }

    public GamePhase getGamePhase() {

        return gamePhase;
    }

    public GameResult getGameResult() {

        return result;
    }

    /* ELSE */

    private void createCardStack() {

        for ( CardColor color : CardColor.values() ) {

            for (CardValue value : CardValue.values() ) {

                cardStack.add(new Card(color, value));
            }
        }
    }

    private void dealCards() {

        for ( var i = 0; i < skat.length; i++ ) {

            skat[i] = getRandomCardFromStack();
        }

        for ( SkatPlayer player : players) {

            for (var j = 0; j < 10; j++) {

                player.getHand().addCard(getRandomCardFromStack());
            }
        }
    }

    private Card getRandomCardFromStack() {

        var randomIndex = rand.nextInt(cardStack.size());

        var randomCard = cardStack.get(randomIndex);
        cardStack.remove(randomIndex);

        return randomCard;
    }

    public void finishAuction() {

        auction.getAuctionWinner().setDeclarer(true);
    }

    public boolean moveIsValid(SkatMove move) {

        return switch ( move.getType() ) {

            case RAISE_OR_ACCEPT, PASS -> gamePhase == GamePhase.AUCTION;

            case SKAT_TO_HAND, HAND_TO_SKAT -> gamePhase == GamePhase.DECLARING;

            case DROP_SKAT -> gamePhase == GamePhase.DECLARING && skatIsValid();

            case SET_TRUMP -> declarer.getTricks().skatIsDropped() && currentRoundNo == -1;

            case PLAY_CARD -> cardPlayIsValid(move.card);

            default -> false;
        };
    }

    private boolean skatIsValid() {

        for (Card card : skat) {

            if (card == null) {

                return false;
            }
        }
        return true;
    }

    private boolean cardPlayIsValid(Card card) {

        var trickColor = currentTrick.getColor();
        var currentPlayersHand = getCurrentPlayer().getHand();

        if ( trickColor == null ) {

            return true;
        }

        if ( trump.isTrump(currentTrick.getCardAt(0)) ) {

            if ( trump.isTrump(card) ) {

                return true;

            } else {

                return !currentPlayersHand.canFollowTrump();
            }
        } else {

            return !currentPlayersHand.canFollowSuit(trickColor);
        }
    }

    private void setPlayerTricks() {


    }

    private void moveCardFromSkatToHand(Card card) {

        getCurrentPlayer().getHand().addCard(card);

        for ( var i = 0; i < skat.length; i++ ) {

            if ( skat[i] == card ) {

                skat[i] = null;
                return;
            }
        }
    }

    private void moveCardFromHandToSkat(Card card) {

        var currentPlayersHand = getCurrentPlayer().getHand();

        for ( var i = 0; i < skat.length; i++ ) {

            if ( skat[i] == null ) {

                skat[i] = card;
                return;
            }
        }

        for ( var i = 0; i < currentPlayersHand.getSize(); i++ ) {

            if ( currentPlayersHand.getCardAt(i) == card ) {

                currentPlayersHand.removeCard(i);
                return;
            }
        }
    }

    private void setTrump(Trump trump) {

        gameMode = trump.getGameMode();
        this.trump.setGameMode(trump.getGameMode());
        this.trump.setColor(trump.getColor());

        currentRoundNo++;
    }

    private void playCard(Card card) {

        var currentTrickSize = currentTrick.getSize();

        switch ( currentTrickSize ) {

            case 3 -> currentTrick = new Trick(trump, card);
            case 1 -> currentTrick.addCard(card);
            case 2 -> {

                var winner = currentTrick.getWinnerIndex();
                var winnerIndex = (currentLeaderIndex + winner) % players.length;

                players[winnerIndex].getTricks().addTrick(currentTrick);

                currentLeaderIndex = winnerIndex;
                currentRoundNo++;
            }
        }
    }

    // TODO: gameOver
    private void gameOver() {


    }
}
