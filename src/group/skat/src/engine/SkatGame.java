package engine;

import console.Print;
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
        currentRoundNo = -1;
        currentLeaderIndex = 0;

        trump = new Trump();
        declarer = null;

        players = new SkatPlayer[3];
        for (var i = 0; i < players.length; i++) {

            players[i] = new SkatPlayer(trump, i);
        }

        cardStack = new ArrayList<>();
        skat = new Card[2];
        auction = new Auction(players);
        result = new GameResult(players, declarer, trump);
        currentTrick = new Trick(trump);

        createCardStack();
        dealCards();
    }

    /* GETTER */

    public SkatPlayer getPlayerAt(int index) {

        return players[index];
    }

    public SkatPlayer getCurrentPlayer() {

        return switch (gamePhase) {

            case AUCTION -> auction.getCurrentAuctioneer();
            case DECLARING, ENDED -> declarer;
            case PLAYING -> players[(currentLeaderIndex + currentTrick.getSize()) % players.length];
            default -> null;
        };
    }

    public Auction getAuction() {
        return auction;
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

    public Card[] getSkat() {

        return skat;
    }

    public Trump getTrump() {

        return trump;
    }

    public Trick getCurrentTrick() {

        return currentTrick;
    }

    public Trick getLastTrick() {

        var tricks = players[currentLeaderIndex].getTricks();

        return tricks.getTrickAt(tricks.getSize() - 1);
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

    public boolean moveIsValid(SkatMove move) {

        return switch (move.getType()) {

            case SORT -> true;

            case ON_HAND, ON_SKATHAND -> moveCardIsValid(move.getIndexFrom(), move.getIndexTo());

            case RAISE_OR_ACCEPT, PASS -> gamePhase == GamePhase.AUCTION;

            case DROP_SKAT -> gamePhase == GamePhase.DECLARING && !declarer.getTricks().skatIsDropped();

            case SET_TRUMP -> gamePhase == GamePhase.DECLARING && declarer.getTricks().skatIsDropped();

            case PLAY_CARD -> gamePhase == GamePhase.PLAYING && cardPlayIsValid(move.getIndexFrom());

            default -> false;
        };
    }

    public boolean makeSkatMove(SkatMove move) {

        if (moveIsValid(move)) {

            switch (move.getType()) {

                case SORT -> sort();

                case ON_HAND, ON_SKATHAND -> moveCard(move.getIndexFrom(), move.getIndexTo());

                case RAISE_OR_ACCEPT -> raiseOrAcceptBid();

                case PASS -> passBid();

                case DROP_SKAT -> dropSkat();

                case SET_TRUMP -> setTrump(move.trump);

                case PLAY_CARD -> playCard(move.getIndexFrom());

                default -> Print.debug("ERROR", "in SkatGame -> makeMove");
            }
            return true;
        }
        return false;
    }

    public void sort() {

        if (gamePhase == GamePhase.PLAYING) {

            getCurrentPlayer().getHand().sort(trump);

        } else {

            getCurrentPlayer().getHand().sort(new Trump(GameMode.GRAND));
        }
    }

    private boolean moveCardIsValid(int indexFrom, int indexTo) {

        return getCurrentPlayer().getHand().moveCardIsValid(indexFrom, indexTo);

    }

    private void moveCard(int indexFrom, int indexTo) {

        if (gamePhase == GamePhase.DECLARING) {

            getCurrentPlayer().getHand().moveCardOnSkatHand(indexFrom, indexTo);

        } else {

            getCurrentPlayer().getHand().moveCardOnHand(indexFrom, indexTo);
        }
    }

    public boolean skatIsDropped() {

        return declarer != null && declarer.getTricks().skatIsDropped();
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

        if (auction.passedOut()) {

            gamePhase = GamePhase.ABORTED;
            result.setAborted(true);
            return;
        }

        declarer = auction.getAuctionWinner();
        result.setDeclarer();
        setPlayerTricks();

        gamePhase = GamePhase.DECLARING;
    }

    private void setPlayerTricks() {

        declarer.getHand().addCard(skat[0]);
        declarer.getHand().addCard(skat[1]);

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

    public void dropSkat() {

        declarer.getTricks().addSkat(skat);
    }

    public void setTrump(Trump trump) {

        gameMode = trump.getGameMode();
        this.trump.setGameMode(trump.getGameMode());
        this.trump.setColor(trump.getColor());

        declarer.getHand().setTrumpLine();

        for (var i = declarer.getHand().getSize() - 2; i < declarer.getHand().getSize(); i++) {

            declarer.getHand().removeCard(i);
        }

        gamePhase = GamePhase.PLAYING;
        currentRoundNo++;
    }

    private boolean cardPlayIsValid(int cardIndex) {

        var trickColor = currentTrick.getColor();
        var currentPlayersHand = getCurrentPlayer().getHand();

        if (cardIndex >= currentPlayersHand.getSize()) {

            return false;
        }

        var card = currentPlayersHand.getCardAt(cardIndex);

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

            if (!card.isTrump(trump) && card.getCardColor() == trickColor) {

                return true;
            }

            return !currentPlayersHand.canFollowSuit(trickColor);
        }
    }

    public void playCard(int cardIndex) {

        var currentPlayersHand = getCurrentPlayer().getHand();
        var card = currentPlayersHand.getCardAt(cardIndex);
        var currentTrickSize = currentTrick.getSize();

        if (currentTrickSize < 2) {

            currentTrick.addCard(card);
            currentPlayersHand.removeCard(cardIndex);

        } else if (currentTrickSize == 2) {

            currentTrick.addCard(card);
            currentPlayersHand.removeCard(cardIndex);

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

    // TODO: comment: gameValue
    private void gameOver() {

        gamePhase = GamePhase.ENDED;

        var trumpLine = declarer.getHand().getTrumpLine();

        var finalScore = getWinFactor() * ((trumpLine + getSchneider()) * trump.getTrumpValue());

        Print.debug("MAIK", getWinFactor() + " * ((" + trumpLine + " + " + getSchneider() + ") * " + trump.getTrumpValue() + "))");

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
