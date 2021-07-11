package test;

import console.Console;
import console.Print;
import controller.SkatController;
import controller.SkatMove;
import controller.enums.ActionType;
import engine.*;
import engine.enums.CardColor;
import engine.enums.GameMode;
import engine.enums.GamePhase;
import framework.GameController;

import java.util.Random;

public class Test {

    SkatController controller;
    SkatGame game;

    public Test(SkatController controller){

        this.controller = controller;
        this.game = controller.getGame();

    }

    public void consoleSetUpUno() {

        controller.makeMove(new SkatMove(ActionType.NEW_GAME));
        controller.makeMove(new SkatMove(ActionType.RAISE_OR_ACCEPT));
        controller.makeMove(new SkatMove(ActionType.PASS));
        controller.makeMove(new SkatMove(ActionType.PASS));
        controller.makeMove(new SkatMove(ActionType.DROP_SKAT));
        controller.makeMove(new SkatMove(new Trump(GameMode.GRAND)));
    }

    public void consoleSetUpDos() {

        for (var g = 0; g < 12; g++) {

            controller.makeMove(new SkatMove(ActionType.NEW_GAME));
        //    controller.makeMove(new SkatMove(ActionType.PASS));
            controller.makeMove(new SkatMove(ActionType.RAISE_OR_ACCEPT));
            controller.makeMove(new SkatMove(ActionType.PASS));
            controller.makeMove(new SkatMove(ActionType.PASS));
            controller.makeMove(new SkatMove(ActionType.DROP_SKAT));
        //    controller.makeMove(new SkatMove(new Trump(GameMode.GRAND)));
            randomTrump();
            controller.makeMove(new SkatMove(ActionType.SORT));

            simulatePlaying();
        }

    }

    public void consoleSetUpTres() {

        controller.makeMove(new SkatMove(ActionType.NEW_GAME));
        controller.makeMove(new SkatMove(ActionType.RAISE_OR_ACCEPT));
        controller.makeMove(new SkatMove(ActionType.PASS));
        controller.makeMove(new SkatMove(ActionType.PASS));
        controller.makeMove(new SkatMove(ActionType.DROP_SKAT));
        randomTrump();

        simulateRounds(10);
    }

    private void simulatePlaying() {

        boolean moveNotValid;

        do {

            moveNotValid = true;

            for (var i = 0; i < 10 && moveNotValid; i++) {

                moveNotValid = !controller.makeMove(new SkatMove(i));

            }

        } while (controller.getGame().getGamePhase() == GamePhase.PLAYING);
    }

    private void simulateRounds(int n) {

        boolean moveNotValid;
        for (var j = 0; j < n * 3; j++) {

            moveNotValid = true;

            for (var i = 0; i < 10 && moveNotValid; i++) {

                moveNotValid = !controller.makeMove(new SkatMove(i));

            }
        }
    }

    private void randomTrump() {

        var rand = new Random();

        switch (rand.nextInt(6)) {

            case 0 -> {
                controller.makeMove(new SkatMove(new Trump(GameMode.NULL)));
                Print.debug("MAIK", "Trump: NULL");
            }
            case 1 -> {
                controller.makeMove(new SkatMove(new Trump(GameMode.GRAND)));
                Print.debug("MAIK", "Trump: GRAND");
            }
            case 2 -> {
                controller.makeMove(new SkatMove(new Trump(CardColor.CLUBS)));
                Print.debug("MAIK", "Trump: CLUBS");
            }
            case 3 -> {
                controller.makeMove(new SkatMove(new Trump(CardColor.SPADES)));
                Print.debug("MAIK", "Trump: SPADES");
            }
            case 4 -> {
                controller.makeMove(new SkatMove(new Trump(CardColor.HEARTS)));
                Print.debug("MAIK", "Trump: HEARTS");
            }
            default -> {
                controller.makeMove(new SkatMove(new Trump(CardColor.DIAMONDS)));
                Print.debug("MAIK", "Trump: DIAMONDS");
            }
        }
    }


    public void mainTest(){

        andi();

    }

    void maik(){

    }

    void andi(){

        consoleSetUpTres();
        var console = new Console(controller);



    }

    /* print Funktionen -> eigene Klasse */



    /* toString Funktionen */




}


















