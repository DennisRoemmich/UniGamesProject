package engine;

import controller.SkatMove;
import engine.enums.CardColor;
import engine.enums.CardValue;
import engine.enums.GameMode;
import engine.enums.GamePhase;
import test.Print;

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
        currentRoundNo = -1;
        currentLeaderIndex = 0; // right?

        players = new SkatPlayer[3];
        for (var i = 0; i < players.length; i++) {

            players[i] = new SkatPlayer(trump);
        }

        cardStack = new ArrayList<>();
        skat = new Card[2];
        auction = new Auction(players);
        trump = new Trump();
        result = new GameResult(players, declarer, trump);

        createCardStack();
        dealCards();

    }

    /* GETTER */

    public SkatPlayer getPlayerAt(int index) {

        return players[index];
    }

    public SkatPlayer getCurrentPlayer() {

        if (currentTrick == null){
            return players[0];
        }

        var i = currentLeaderIndex + currentTrick.getSize();

        return switch (gamePhase) {

            case AUCTION -> auction.getCurrentAuctioneer();
            case DECLARING -> declarer;
            case PLAYING -> players[i % players.length];
            default -> null;
        };
    }

    /**
     * needed to print a trick properly
     */
    public int getCurrentLeaderIndex() {

        return currentLeaderIndex;
    }

    public int getCurrentRoundNo() {

        return currentRoundNo;
    }

    public GamePhase getGamePhase() {

        return gamePhase;
    }

    public SkatPlayer getDeclarer() {

        return declarer;
    }

    public GameResult getGameResult() {

        return result;
    }

    /* ELSE */

    private void createCardStack() {

        for (CardColor color : CardColor.values()) {

            for (CardValue value : CardValue.values()) {

                cardStack.add(new Card(color, value));
            }
        }
    }

    private void dealCards() {

        for (var i = 0; i < skat.length; i++) {

            skat[i] = getRandomCardFromStack();
        }

        for (SkatPlayer player : players) {

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

    public void sort() {

    }

    public boolean moveIsValid(SkatMove move) {

        return switch (move.getType()) {

            case SORT, ON_HAND -> true;

            case RAISE_OR_ACCEPT, PASS -> gamePhase == GamePhase.AUCTION;

            case SKAT_TO_HAND, HAND_TO_SKAT -> gamePhase == GamePhase.DECLARING;

            case DROP_SKAT -> gamePhase == GamePhase.DECLARING && skatIsValid() && !declarer.getTricks().skatIsDropped();

            case SET_TRUMP -> declarer.getTricks().skatIsDropped() && currentRoundNo == -1;

            case PLAY_CARD -> cardPlayIsValid(new Card(null, null));

            default -> false;
        };
    }

    public boolean makeSkatMove(SkatMove move) {

        if (moveIsValid(move)) {
            switch (move.getType()) {

                case SORT -> sort();
                case RAISE_OR_ACCEPT -> raiseOrAcceptBid();
                case PASS -> passBid();
                case SKAT_TO_HAND -> moveCardFromSkatToHand(new Card(null, null), -1);
                case HAND_TO_SKAT -> moveCardFromHandToSkat(new Card(null, null), -1);
                case DROP_SKAT -> dropSkat();
                case SET_TRUMP -> setTrump(move.trump);
                case PLAY_CARD -> playCard(new Card(null, null));
                default -> Print.debug("ERROR", "in SkatGame -> makeMove");
            }
            return true;
        }
        return false;
    }

    private void raiseOrAcceptBid() {

        auction.raiseOrAcceptBid();

        if (!auction.isRunning()) {

            finishAuction();
        }
    }

    private void passBid() {

        auction.passBid();

        if (!auction.isRunning()) {

            finishAuction();
        }
    }

    private void finishAuction() {

        declarer = auction.getAuctionWinner();
        setPlayerTricks();

        gamePhase = GamePhase.DECLARING;
    }

    private void setPlayerTricks() {

        if (players[2] != declarer) {

            if (players[1] != declarer) {

                players[2].setTricks(players[1].getTricks());

            } else {

                players[2].setTricks(players[0].getTricks());
            }

        } else {

            players[1].setTricks(players[0].getTricks());
        }
    }

    private void moveCardFromSkatToHand(Card card, int index) {

        getCurrentPlayer().getHand().addCardAt(index, card);

        for (var i = 0; i < skat.length; i++) {

            if (skat[i] == card) {

                skat[i] = null;
                break;
            }
        }
    }

    private void moveCardFromHandToSkat(Card card, int index) {

        var currentPlayersHand = getCurrentPlayer().getHand();

        for (var i = 0; i < skat.length; i++) {

            if (skat[index] == null) {

                skat[index] = card;
                break;
            }
        }

        for (var i = 0; i < currentPlayersHand.getSize(); i++) {

            if (currentPlayersHand.getCardAt(i) == card) {

                currentPlayersHand.removeCard(i);
                break;
            }
        }
    }

    private boolean skatIsValid() {

        for (Card card : skat) {

            if (card == null) {

                return false;
            }
        }
        return true;
    }

    public void dropSkat() {

        declarer.getTricks().addSkat(skat);
    }

    public void setTrump(Trump trump) {

        gameMode = trump.getGameMode();
        this.trump.setGameMode(trump.getGameMode());
        this.trump.setColor(trump.getColor());

        gamePhase = GamePhase.PLAYING;
        currentRoundNo++;
    }

    private boolean cardPlayIsValid(Card card) {

        var trickColor = currentTrick.getColor();
        var currentPlayersHand = getCurrentPlayer().getHand();

        if (currentTrick.getSize() == 0) {

            return true;
        }

        if (currentTrick.getCardAt(0).isTrump(trump)) {

            if (card.isTrump(trump)) {

                return true;

            } else {

                return !currentPlayersHand.canFollowTrump();
            }
        } else {

            return !currentPlayersHand.canFollowSuit(trickColor);
        }
    }

    public void playCard(Card card) {

        var currentTrickSize = currentTrick.getSize();

        if (currentTrickSize < 2) {

            currentTrick.addCard(card);

        } else if (currentTrickSize == 2) {

            currentTrick.addCard(card);

            finishRound();
        }
    }

    private void finishRound() {

        var trickWinnerIndex = currentTrick.getWinnerIndex();
        var nextLeaderIndex = (currentLeaderIndex + trickWinnerIndex) % players.length;

        players[nextLeaderIndex].getTricks().addTrick(currentTrick);

        currentTrick = null;
        currentLeaderIndex = nextLeaderIndex;

        if (currentRoundNo == 9) {

            gameOver();
            return;
        }

        currentRoundNo++;
        currentTrick = new Trick(trump);
    }

    private void gameOver() {

        var trumpLine = declarer.getHand().getTrumpLine();

        var finalScore = getWinFactor() * ((trumpLine + getSchneider()) * trump.getTrumpValue());

        declarer.setFinalScore(finalScore);
        result.gameHasEnded();
    }

    private int getWinFactor() {

        var declarerPoints = declarer.getTricksScore();
        var declarerTricks = declarer.getTricksAmount();

        if (trump.getGameMode() == GameMode.NULL && declarerTricks == 0) {

            return 1;

        }
        if ((trump.getGameMode() == GameMode.GRAND || trump.getGameMode() == GameMode.SUIT)
                && declarerPoints >= 60) {

            return 1;
        }

        return -2;
    }

    private int getSchneider() {

        var declarerPoints = declarer.getTricksScore();
        var declarerTricks = declarer.getTricksAmount();

        if (trump.getGameMode() == GameMode.GRAND || trump.getGameMode() == GameMode.SUIT) {

            if (declarerTricks == 10 || declarerTricks == 0) {

                return 3;
            }
            if (declarerPoints >= 90 || declarerPoints <= 30) {

                return 2;
            }
        }
        return 1;
    }
}
