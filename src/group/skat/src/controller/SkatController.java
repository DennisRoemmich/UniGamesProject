package controller;

import engine.SkatSet;
import framework.GameController;
import org.json.simple.JSONObject;

public class SkatController extends GameController {

    private int gameAmount;
    private SkatSet skatSet;

    public SkatController(int gameAmount, String[] names) {

        this.gameAmount = gameAmount;

        skatSet = new SkatSet(gameAmount, names);
    }

  /*  public void makeMove(SkatMove move) {

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
    }*/

    public boolean moveIsValid(SkatMove move) {

        return switch ( move.getType() ) {

            case NEW_SET -> false; // ??
            case SORT -> true;
            case ON_HAND -> true;
            default -> skatSet.moveIsValid(move);
        };
    }

    @Override
    protected JSONObject executeMove(JSONObject move) {
        return null;
    }

    @Override
    public JSONObject metaSettingsToJSON() {
        return null;
    }

    @Override
    public JSONObject gameSettingsToJSON() {
        return null;
    }

    @Override
    public void restoreMetaSettings(JSONObject metaSettings) {

    }

    @Override
    public void restoreGameSettings(JSONObject gameSettings) {

    }

    @Override
    public void newGame() {

    }
}
