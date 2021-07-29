package KIPlayer;

import console.Print;
import controller.SkatController;
import controller.SkatMove;
import controller.enums.ActionType;
import engine.*;
import engine.enums.GamePhase;
import framework.Player;
import javafx.application.Platform;
import org.json.simple.JSONObject;

import java.util.Random;

/**
 * class for the KI
 */
public class KIPlayer implements Player {

    private static final int SLEEP_DURATION = 1000;

    private SkatController mController;
    private SkatGame mGame;
    private SkatPlayer mPlayer;
    private Hand mHand;
    private int mIndex;

    private Random mRand = new Random();

    /* CONSTRUCTOR */

    public KIPlayer(SkatController controller, int index) {

        this.mController = controller;
        this.mIndex = index;


    }

    public void init(){

        mGame = mController.getGame();
        mPlayer = mController.getSkatSet().getSkatPlayerAt(mIndex);
        mHand = mPlayer.getHand();

    }

    // TODO: mit welchem index kommt man zum richtigen SkatPlayer

    /* OTHER */

    /**
     * @return move of KI
     */
    public SkatMove getMove() {

        if (mGame.getGamePhase() == GamePhase.PLAYING) {

            return getKIPlay();

        } else {

            return getKICall();
        }
    }

    /**
     * KI only passes in auction, it will never be declarer
     * @return move
     */
    private SkatMove getKICall() {

        SkatMove finalMove;

        finalMove = new SkatMove(ActionType.PASS);

        return finalMove;
    }

    /**
     * @return move in the playing phase
     */
    private SkatMove getKIPlay() {

        SkatMove finalMove;

        var currentTrick = mGame.getCurrentTrick();
        var trickSize = currentTrick.getSize();


        if (trickSize == 0) {

            finalMove = firstToMove();

        } else if (trickSize == 1) {

            finalMove = secondToMove(currentTrick);

        } else {

            finalMove = lastToMove(currentTrick);
        }

        return finalMove;
    }

    /**
     * @return move with KI first to move
     */
    private SkatMove firstToMove() {

        return new SkatMove(getRandomNotTrumpCard());
    }

    /**
     * @return move with KI second to move
     */
    private SkatMove secondToMove(Trick trick) {

        var declarer = mGame.getDeclarer();
        var declarerIsFirst = declarer.getGameIndex() == (mIndex + 2) % 3;

        if (declarerIsFirst) {

            return new SkatMove(getMbyStingCard(trick.getCardAt(0)));
        }
        return new SkatMove(getRandomCard());
    }

    /**
     * @return move with KI third to move
     */
    private SkatMove lastToMove(Trick trick) {


        var declarer = mGame.getDeclarer();
        var declarerIsFirst = declarer.getGameIndex() == (mIndex + 1) % 3;

        var betterCardIndex = 0;
        if (trick.isStrongerCard(trick.getCardAt(0), trick.getCardAt(1))) {

            betterCardIndex = 1;
        }

        if (declarerIsFirst && betterCardIndex == 0 || !declarerIsFirst && betterCardIndex == 1) {

            return new SkatMove(getMbyStingCard(trick.getCardAt(0), trick.getCardAt(1)));

        } else {

            return new SkatMove(getMostValuableCard());
        }
    }

    /**
     * @return index of card with most value on hand
     */
    private int getMostValuableCard() {

        var max = 0;

        for (var i = 1; i < mHand.getSize(); i++) {

            var maxPoints = mHand.getCardAt(max).getPoints();
            if (mGame.moveIsValid(new SkatMove(i)) && mHand.getCardAt(i).getPoints() > maxPoints) {

                max = i;
            }
        }

        return max;
    }

    /**
     * @return index of card with least value on hand
     */
    private int getLeastValuableCard() {

        var min = 0;

        for (var i = 1; i < mHand.getSize(); i++) {

            var minPoints = mHand.getCardAt(min).getPoints();
            if (mGame.moveIsValid(new SkatMove(i)) && mHand.getCardAt(i).getPoints() < minPoints) {

                min = i;
            }
        }

        return min;
    }

    /**
     * @return index of random card on hand
     */
    private int getRandomCard() {

        int pos;
        do {

            pos = mRand.nextInt(mHand.getSize());

        } while (!mGame.moveIsValid(new SkatMove(pos)));

        return pos;
    }

    /**
     * @return index of random card on hand, but no trump if possible
     */
    private int getRandomNotTrumpCard() {

        for (var i = 0; i < 6; i++) {

            var j = getRandomCard();
            if (!mHand.getCardAt(j).isTrump(mGame.getTrump())) {

                return j;
            }
        }
        return getRandomCard();
    }

    /**
     * KI tries to sting the playe card, but evaluates if the move would be valuable.
     * If the move is valuable, the move is played, else a weak card is played
     * @param toSting card to sting with KI second to move
     * @return index of card to play
     */
    private int getMbyStingCard(Card toSting) {

        var trick = mGame.getCurrentTrick();

        for (var i = 0; i < mHand.getSize(); i++) {

            var card = mHand.getCardAt(i);
            var valuable = toSting.getPoints() + card.getPoints() > 6;

            if (mGame.moveIsValid(new SkatMove(i)) && trick.isStrongerCard(toSting, card) && valuable) {

                return i;
            }
        }
        return getLeastValuableCard();
    }

    /**
     * KI tries to sting the played cards, but evaluates if the move would be valuable.
     * If the move is valuable, the move is played, else a weak card is played
     * @ param two cards to sting with KI third to move
     * @return index of card to play
     */
    private int getMbyStingCard(Card toStingUno, Card toStingDos) {

        var trick = mGame.getCurrentTrick();
        Card strongerCard;
        if (trick.isStrongerCard(toStingUno, toStingDos)) {

            strongerCard = toStingDos;

        } else {

            strongerCard = toStingUno;
        }

        for (var i = 0; i < mHand.getSize(); i++) {

            var card = mHand.getCardAt(i);
            var valuable = toStingUno.getPoints() + toStingDos.getPoints() + card.getPoints() > 10;

            if (mGame.moveIsValid(new SkatMove(i)) && trick.isStrongerCard(strongerCard, card) && valuable) {

                return i;
            }
        }
        return getLeastValuableCard();
    }

    @Override
    public JSONObject requestMove(JSONObject inputType) {

        if (mController.getGame() == null) {
            return null;
        }

        if (mGame == null) {
            init();
        }

        if (inputType.get("YOURMOVE") == "TRUE") {

            new Thread(()->{ //use another thread so long process does not block gui

                try {Thread.sleep(SLEEP_DURATION);} catch (InterruptedException ex) { ex.printStackTrace();}
                Platform.runLater(() -> mController.makeMove(getMove()));

            }).start();


        }
        return new JSONObject();
    }
}
