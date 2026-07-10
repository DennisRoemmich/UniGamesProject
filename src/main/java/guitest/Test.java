package guitest;

import guiconsole.Console;
import guiconsole.Print;
import guicontroller.SkatController;
import guicontroller.SkatMove;
import guicontroller.enums.ActionType;
import guiengine.*;
import guiengine.enums.CardColor;
import guiengine.enums.GameMode;
import guiengine.enums.GamePhase;

import java.util.Random;

public class Test {

    SkatController controller;
    SkatGame game;

    public Test(SkatController controller) {

        this.controller = controller;
        this.game = controller.getGame();

    }


    public void testEndgame(){
        skipDeclaration();
        simulatePlaying(24);

        controller.messageNextPlayer();


    }

    public void skipDeclaration() {


        var move = new SkatMove(ActionType.NEW_GAME);
        move.testMove();
        controller.makeMove(move);

        move = new SkatMove(ActionType.PASS);
        move.testMove();
        controller.makeMove(move);

        move = new SkatMove(ActionType.RAISE_OR_ACCEPT);
        move.testMove();
        controller.makeMove(move);

        move = new SkatMove(ActionType.PASS);
        move.testMove();
        controller.makeMove(move);

        move = new SkatMove(ActionType.DROP_SKAT);
        move.testMove();
        controller.makeMove(move);

        move = new SkatMove(new Trump(GameMode.GRAND));
        move.testMove();
        controller.makeMove(move);


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

            simulatePlaying(-1);
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

    private void simulatePlaying(int o) {

        boolean moveNotValid;



        do {

            o--;

            moveNotValid = true;

            for (var i = 0; i < 10 && moveNotValid; i++) {

                var move = new SkatMove(i);
                move.testMove();
                moveNotValid = !controller.makeMove(move);

            }

        } while (controller.getGame().getGamePhase() == GamePhase.PLAYING && o > 0);



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


    public void mainTest() {

        andi();

    }

    void maik() {

    }

    void andi() {

        consoleSetUpTres();
        var console = new Console(controller);


    }

}


















