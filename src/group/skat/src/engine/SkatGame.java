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

    private int currentRoundNo;
    private int currentLeaderIndex;

    private GameResult result;

    private Random rand = new Random();

    /* CONSTRUCTOR */

    public SkatGame() {

        gamePhase = GamePhase.AUCTION;
        players = new SkatPlayer[3];

        cardStack = new ArrayList<>();

        skat = new Card[2];

        auction = new Auction(players);

        currentRoundNo = 0;

        result = new GameResult(players, declarer, trump);


        for ( var i = 0; i < players.length; i++ ) {

            players[i] = new SkatPlayer();
        }

        createCardStack();
        dealCards();
    }

    /* GETTER */

    public SkatPlayer getPlayerAt(int index) {

        return players[index];
    }

    public SkatPlayer getCurrentPlayer() {

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

    private void setPlayerTricks() {

        var declarerTricks = new Tricks(skat);
        var opponentsTricks = new Tricks();

        for ( SkatPlayer player : players ) {

            if ( player.isDeclarer() ) {

                player.setTricks(declarerTricks);

            } else {

                player.setTricks(opponentsTricks);
            }
        }
    }

    private void setTrump(Trump trump) {

        gameMode = trump.getGameMode();
        this.trump = trump;

        for ( SkatPlayer player : players ) {

            player.getHand().setTrump(trump);
        }
    }

    // TODO: was passiert hier genau?
    public void makeMove(SkatMove move) {

        switch ( move.getType() ) {

            case RAISE_OR_ACCEPT -> {

                if ( moveIsValid(move) ) {

                    auction.raiseOrAcceptBid(getCurrentPlayer());
                }
            }

            case PASS -> {

                if ( moveIsValid(move) ) {

                    auction.passBid(getCurrentPlayer());
                }
            }

            case SKAT_TO_HAND -> {

                if ( moveIsValid(move) ) {

                    moveCardFromSkatToHand(move.card);
                }
            }

            case HAND_TO_SKAT -> {

                if ( moveIsValid(move) ) {

                    moveCardFromHandToSkat(move.card);
                }
            }

            case DROP_SKAT -> {

                if ( moveIsValid(move) ) {

                    setPlayerTricks();
                }
            }

            case SET_TRUMP -> {

                if ( moveIsValid(move) ) {

                    setTrump(move.trump);
                }
            }

            case PLAY_CARD -> {

                if ( cardPlayIsValid(move.card) ) {

                    playCard(move.card);
                }
            }
        }
    }

    public boolean moveIsValid(SkatMove move) {

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

    // TODO: gameOver
    private void gameOver() {


    }
}
