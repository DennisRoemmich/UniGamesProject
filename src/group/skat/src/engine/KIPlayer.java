package engine;

import controller.SkatController;
import controller.SkatMove;
import controller.enums.ActionType;
import engine.enums.GamePhase;
import framework.Player;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class KIPlayer implements Player {

    private SkatController controller;
    private SkatGame game;
    private SkatPlayer player;
    private Hand hand;
    private int index;

    /* CONSTRUCTOR */

    public KIPlayer(SkatController controller, int index) {

        this.controller = controller;
        game = controller.getGame();
        this.index = index;
        player = controller.getSkatSet().getSkatPlayerAt(index);
        hand = player.getHand();
    }

    // TODO: mit welchem index kommt man zum richtigen SkatPlayer

    /* OTHER */

    public SkatMove getMove() {

        if (game.getGamePhase() == GamePhase.PLAYING) {

            return getKIPlay();

        } else {

            return getKICall();
        }
    }

    private SkatMove getKICall() {

        SkatMove finalMove;

        finalMove = new SkatMove(ActionType.PASS);

        return finalMove;
    }

    private SkatMove getKIPlay() {

        SkatMove finalMove;

        var declarer = game.getDeclarer();
        var declarerCards = declarer.getTricks().getCards();
        var opponentsCards = player.getTricks().getCards();

        ArrayList<Card> playedCards = new ArrayList<>();

        playedCards.addAll(declarerCards);
        playedCards.addAll(opponentsCards);

        var currentTrick = game.getCurrentTrick();
        var trickSize = currentTrick.getSize();


        if (trickSize == 0) {

            finalMove = firstToMove(playedCards, currentTrick);

        } else if (trickSize == 1) {

            finalMove = secondToMove(playedCards, currentTrick);

        } else {

            finalMove = lastToMove(playedCards, currentTrick);
        }

        return finalMove;
    }

    private ArrayList<Card> getPossibleMoves(Trick trick) {

        ArrayList<Card> moves = new ArrayList<>();

        for (var i = 0; i < hand.getSize(); i++) {

            if (game.moveIsValid(new SkatMove(i))) {

                moves.add(hand.getCardAt(i));
            }
        }

        return moves;
    }

    private SkatMove firstToMove(ArrayList<Card> playedCards, Trick trick) {

        return new SkatMove(getRandomNotTrumpCard());
    }

    private SkatMove secondToMove(ArrayList<Card> playedCards, Trick trick) {

        var declarer = game.getDeclarer();
        var declarerIsFirst = declarer.getGameIndex() == (index + 2) % 3;

        if (declarerIsFirst) {

            return new SkatMove(getMbyStingCard(trick.getCardAt(0)));
        }
        return new SkatMove(getRandomCard());
    }

    private SkatMove lastToMove(ArrayList<Card> playedCards, Trick trick) {

        SkatMove move;

        var declarer = game.getDeclarer();
        var declarerIsFirst = declarer.getGameIndex() == (index + 1) % 3;

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

    private int getMostValuableCard() {

        var max = 0;

        for (var i = 1; i < hand.getSize(); i++) {

            var maxPoints = hand.getCardAt(max).getPoints();
            if (game.moveIsValid(new SkatMove(i)) && hand.getCardAt(i).getPoints() > maxPoints) {

                max = i;
            }
        }

        return max;
    }

    private int getLeastValuableCard() {

        var min = 0;

        for (var i = 1; i < hand.getSize(); i++) {

            var minPoints = hand.getCardAt(min).getPoints();
            if (game.moveIsValid(new SkatMove(i)) && hand.getCardAt(i).getPoints() < minPoints) {

                min = i;
            }
        }

        return min;
    }

    private int getRandomCard() {

        var rand = new Random();

        int pos;
        do {

            pos = rand.nextInt(hand.getSize());

        } while (!game.moveIsValid(new SkatMove(pos)));

        return pos;
    }

    private int getRandomNotTrumpCard() {

        for (var i = 0; i < 6; i++) {

            var j = getRandomCard();
            if (!hand.getCardAt(j).isTrump(game.getTrump())) {

                return j;
            }
        }
        return getRandomCard();
    }

    private int getMbyStingCard(Card toSting) {

        var trick = game.getCurrentTrick();

        for (var i = 0; i < hand.getSize(); i++) {

            var card = hand.getCardAt(i);
            var valuable = toSting.getPoints() + card.getPoints() > 6;

            if (game.moveIsValid(new SkatMove(i)) && trick.isStrongerCard(toSting, card) && valuable) {

                return i;
            }
        }
        return getLeastValuableCard();
    }

    private int getMbyStingCard(Card toStingUno, Card toStingDos) {

        var trick = game.getCurrentTrick();
        Card strongerCard;
        if (trick.isStrongerCard(toStingUno, toStingDos)) {

            strongerCard = toStingDos;

        } else {

            strongerCard = toStingUno;
        }

        for (var i = 0; i < hand.getSize(); i++) {

            var card = hand.getCardAt(i);
            var valuable = toStingUno.getPoints() + toStingDos.getPoints() + card.getPoints() > 10;

            if (game.moveIsValid(new SkatMove(i)) && trick.isStrongerCard(strongerCard, card) && valuable) {

                return i;
            }
        }
        return getLeastValuableCard();
    }

    @Override
    public JSONObject requestMove(JSONObject inputType) {

        if (inputType.get("YOURMOVE") == "TRUE") {

            controller.makeMove(getMove());

        }
        return null;
    }
}
