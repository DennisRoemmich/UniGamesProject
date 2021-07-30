package engine;

import console.Print;
import controller.SkatMove;
import engine.enums.CardColor;
import engine.enums.CardValue;
import engine.enums.GameMode;
import engine.enums.GamePhase;

import java.util.ArrayList;
import java.util.Random;

/**
 * central class for the came logic
 */
public class SkatGame {

    private GamePhase mGamePhase;
    private SkatPlayer[] mPlayers;

    private ArrayList<Card> mCardStack;

    private Card[] mSkat;
    private Trick mCurrentTrick;

    private Auction mAuction;

    private SkatPlayer mDeclarer;
    private Trump mTrump;

    private int mCurrentRoundNo;
    private int mCurrentLeaderIndex;

    private GameResult mResult;

    private Random mRand = new Random();

    /* CONSTRUCTOR */

    public SkatGame() {

        mGamePhase = GamePhase.AUCTION;
        mCurrentRoundNo = -1;
        mCurrentLeaderIndex = 0;

        mTrump = new Trump();
        mDeclarer = null;

        mPlayers = new SkatPlayer[3];
        for (var i = 0; i < mPlayers.length; i++) {

            mPlayers[i] = new SkatPlayer(mTrump, i);
        }

        mCardStack = new ArrayList<>();
        mSkat = new Card[2];
        mAuction = new Auction(mPlayers);
        mResult = new GameResult(mPlayers, mDeclarer, mTrump);
        mCurrentTrick = new Trick(mTrump);

        createCardStack();
        dealCards();
    }

    /* GETTER */

    public SkatPlayer getPlayerAt(int index) {

        return mPlayers[index];
    }

    /**
     * @return current player to move
     */
    public SkatPlayer getCurrentPlayer() {

        return switch (mGamePhase) {

            case AUCTION -> mAuction.getCurrentAuctioneer();
            case DECLARING, ENDED -> mDeclarer;
            case PLAYING -> mPlayers[(mCurrentLeaderIndex + mCurrentTrick.getSize()) % mPlayers.length];
            default -> null;
        };
    }

    /**
     * @return auction of game
     */
    public Auction getAuction() {

        return mAuction;
    }

    /**
     * needed to print a trick properly
     */
    public int getCurrentLeaderIndex() {

        return mCurrentLeaderIndex;
    }

    /**
     * @return number of current round
     */
    public int getCurrentRoundNo() {

        return mCurrentRoundNo;
    }

    /**
     * @return current game phase
     */
    public GamePhase getGamePhase() {

        return mGamePhase;
    }

    /**
     * @return declarer
     */
    public SkatPlayer getDeclarer() {

        return mDeclarer;
    }

    /**
     * @return game result of game
     */
    public GameResult getGameResult() {

        return mResult;
    }

    /**
     * @return skat at the beginning of the game
     */
    public Card[] getSkat() {

        return mSkat;
    }

    /**
     * @return trump of game
     */
    public Trump getTrump() {

        return mTrump;
    }

    /**
     * @return current trick
     */
    public Trick getCurrentTrick() {

        return mCurrentTrick;
    }

    /**
     * needed for console print
     * @return last trick
     */
    public Trick getLastTrick() {

        var tricks = mPlayers[mCurrentLeaderIndex].getTricks();

        return tricks.getTrickAt(tricks.getSize() - 1);
    }

    /* ELSE */

    /**
     * initialising the cards
     */
    private void createCardStack() {

        for (CardColor color : CardColor.values()) {

            for (CardValue value : CardValue.values()) {

                mCardStack.add(new Card(color, value));
            }
        }
    }

    /**
     * dealing of cards
     */
    private void dealCards() {

        for (var i = 0; i < mSkat.length; i++) {

            mSkat[i] = getRandomCardFromStack();
        }

        for (SkatPlayer player : mPlayers) {

            for (var j = 0; j < 10; j++) {

                player.getHand().addCard(getRandomCardFromStack());
            }
        }
    }

    private Card getRandomCardFromStack() {

        var randomIndex = mRand.nextInt(mCardStack.size());

        var randomCard = mCardStack.get(randomIndex);
        mCardStack.remove(randomIndex);

        return randomCard;
    }

    /**
     * @param move move
     * @return true if move is possible, false if not
     */
    public boolean moveIsValid(SkatMove move) {

        return switch (move.getType()) {

            case SORT -> true;

            case ON_HAND, ON_SKATHAND -> moveCardIsValid(move.getIndexFrom(), move.getIndexTo());

            case RAISE_OR_ACCEPT, PASS -> mGamePhase == GamePhase.AUCTION;

            case DROP_SKAT -> mGamePhase == GamePhase.DECLARING && !mDeclarer.getTricks().skatIsDropped();

            case SET_TRUMP -> mGamePhase == GamePhase.DECLARING && mDeclarer.getTricks().skatIsDropped();

            case PLAY_CARD -> mGamePhase == GamePhase.PLAYING && cardPlayIsValid(move.getIndexFrom());

            default -> false;
        };
    }

    /**
     * executes a move
     * @param move move
     * @return true if move was played, false if not
     */
    public boolean makeSkatMove(SkatMove move) {

        Print.debug("INFO", "make skat move");

        if (moveIsValid(move)) {

            switch (move.getType()) {

                case SORT -> sort();

                case ON_HAND, ON_SKATHAND -> moveCard(move.getIndexFrom(), move.getIndexTo());

                case RAISE_OR_ACCEPT -> raiseOrAcceptBid();

                case PASS -> passBid();

                case DROP_SKAT -> dropSkat();

                case SET_TRUMP -> setTrump(move.getTrump());

                case PLAY_CARD -> playCard(move.getIndexFrom());

                default -> Print.debug("ERROR", "in SkatGame -> makeMove");
            }
            return true;
        }
        return false;
    }

    /**
     * sorts the hand of the current player
     */
    public void sort() {

        if (mGamePhase == GamePhase.PLAYING) {

            getCurrentPlayer().getHand().sort(mTrump);

        } else {

            getCurrentPlayer().getHand().sort(new Trump(GameMode.GRAND));
        }
    }

    /**
     * @param indexFrom index of card to move
     * @param indexTo index of target
     * @return if move is possible
     */
    private boolean moveCardIsValid(int indexFrom, int indexTo) {

        return getCurrentPlayer().getHand().moveCardIsValid(indexFrom, indexTo);

    }

    /**
     * executes a card play
     * @param indexFrom index of card to play
     * @param indexTo index of target
     */
    private void moveCard(int indexFrom, int indexTo) {

        if (mGamePhase == GamePhase.DECLARING) {

            getCurrentPlayer().getHand().moveCardOnSkatHand(indexFrom, indexTo);

        } else {

            getCurrentPlayer().getHand().moveCardOnHand(indexFrom, indexTo);
        }
    }

    /**
     * @return true if skat is already dropped, false if not
     */
    public boolean skatIsDropped() {

        return mDeclarer != null && mDeclarer.getTricks().skatIsDropped();
    }

    /**
     * current player raises or accepts bid
     */
    private void raiseOrAcceptBid() {

        mAuction.raiseOrAcceptBid();

        if (!mAuction.isRunning()) {

            finishAuction();
        }
    }

    /**
     * current player passes bid
     */
    private void passBid() {

        Print.debug("INFO", "passing");

        mAuction.passBid();

        if (!mAuction.isRunning()) {

            Print.debug("INFO", "auction finished");
            finishAuction();
        }
    }

    /**
     * finishes the auction and sets up the playing phase
     */
    private void finishAuction() {

        if (mAuction.passedOut()) {

            Print.debug("INFO", "passedOut");

            mGamePhase = GamePhase.ABORTED;
            mResult.setAborted(true);
            return;
        }

        mDeclarer = mAuction.getAuctionWinner();
        mResult.setDeclarer();
        setPlayerTricks();

        mGamePhase = GamePhase.DECLARING;
    }

    /**
     * creates the tricks for the player
     */
    private void setPlayerTricks() {

        mDeclarer.getHand().addCard(mSkat[0]);
        mDeclarer.getHand().addCard(mSkat[1]);

        if (mPlayers[2] != mDeclarer) {

            if (mPlayers[1] != mDeclarer) {

                mPlayers[2].setTricks(mPlayers[1].getTricks());

            } else {

                mPlayers[2].setTricks(mPlayers[0].getTricks());
            }

        } else {

            mPlayers[1].setTricks(mPlayers[0].getTricks());
        }

    }

    /**
     * declarer drops the skat
     */
    public void dropSkat() {

        mDeclarer.getTricks().addSkat(mSkat);
    }

    /**
     * declarer sets trump for game
     * @param trump trump
     */
    public void setTrump(Trump trump) {


        this.mTrump.setGameMode(trump.getGameMode());
        this.mTrump.setColor(trump.getColor());

        mDeclarer.getHand().setTrumpLine();

        for (var i = mDeclarer.getHand().getSize() - 2; i < mDeclarer.getHand().getSize(); i++) {

            mDeclarer.getHand().removeCard(i);
        }

        mGamePhase = GamePhase.PLAYING;
        mCurrentRoundNo++;
    }

    /**
     * @param cardIndex index of card to play
     * @return true if card play is possible, false if not
     */
    private boolean cardPlayIsValid(int cardIndex) {

        var trickColor = mCurrentTrick.getColor();
        var currentPlayersHand = getCurrentPlayer().getHand();

        if (cardIndex >= currentPlayersHand.getSize()) {

            return false;
        }

        var card = currentPlayersHand.getCardAt(cardIndex);

        if (mCurrentTrick.getSize() == 0) {

            return true;
        }

        if (mCurrentTrick.getCardAt(0).isTrump(mTrump)) {

            if (card.isTrump(mTrump)) {

                return true;

            } else {

                return !currentPlayersHand.canFollowTrump();
            }
        } else {

            if (!card.isTrump(mTrump) && card.getCardColor() == trickColor) {

                return true;
            }

            return !currentPlayersHand.canFollowSuit(trickColor);
        }
    }

    /**
     * plays card
     * @param cardIndex index of card to play
     */
    public void playCard(int cardIndex) {

        var currentPlayersHand = getCurrentPlayer().getHand();
        var card = currentPlayersHand.getCardAt(cardIndex);
        var currentTrickSize = mCurrentTrick.getSize();

        if (currentTrickSize < 2) {

            mCurrentTrick.addCard(card);
            currentPlayersHand.removeCard(cardIndex);

        } else if (currentTrickSize == 2) {

            mCurrentTrick.addCard(card);
            currentPlayersHand.removeCard(cardIndex);

            finishRound();
        }
    }

    /**
     * finishes a round
     */
    private void finishRound() {

        var trickWinnerIndex = mCurrentTrick.getWinnerIndex();
        var nextLeaderIndex = (mCurrentLeaderIndex + trickWinnerIndex) % mPlayers.length;

        mPlayers[nextLeaderIndex].getTricks().addTrick(mCurrentTrick);

        if (mCurrentRoundNo == 9) {

            gameOver();
            return;
        }

        mCurrentLeaderIndex = nextLeaderIndex;
        mCurrentRoundNo++;
        mCurrentTrick = new Trick(mTrump);
    }

    /**
     * finishes a game, sets up all infos
     */
    private void gameOver() {

        mGamePhase = GamePhase.ENDED;

        var trumpLine = mDeclarer.getHand().getTrumpLine();

        var finalScore = getWinFactor() * ((trumpLine + getSchneider()) * mTrump.getTrumpValue());

        Print.debug("MAIK", getWinFactor() + " * ((" + trumpLine + " + " + getSchneider() + ") * " + mTrump.getTrumpValue() + "))");

        mDeclarer.setFinalScore(finalScore);
        mResult.gameHasEnded();
    }

    /**
     * helps calculate the final game value
     * @return win factor including "schneider" and "schwarz"
     */
    private int getWinFactor() {

        var declarerPoints = mDeclarer.getTricksScore();
        var declarerTricks = mDeclarer.getTricksAmount();

        if (mTrump.getGameMode() == GameMode.NULL && declarerTricks == 0) {

            return 1;

        }
        if ((mTrump.getGameMode() == GameMode.GRAND || mTrump.getGameMode() == GameMode.SUIT)
                && declarerPoints >= 60) {

            return 1;
        }

        return -2;
    }

    /**
     * @return multiplier for game value, including "schneider" and "schwarz"
     */
    private int getSchneider() {

        var declarerPoints = mDeclarer.getTricksScore();
        var declarerTricks = mDeclarer.getTricksAmount();

        if (mTrump.getGameMode() == GameMode.GRAND || mTrump.getGameMode() == GameMode.SUIT) {

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
