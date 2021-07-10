package test;

import console.Console;
import controller.SkatController;
import controller.SkatMove;
import controller.enums.ActionType;
import engine.*;
import engine.enums.GameMode;

public class Test {

    SkatController controller;
    SkatGame game;

    public Test(SkatController controller){

        this.controller = controller;
        this.game = controller.getGame();

    }

    public void consoleSetUp() {

        controller.makeMove(new SkatMove(ActionType.NEW_GAME));
        controller.makeMove(new SkatMove(ActionType.RAISE_OR_ACCEPT));
        controller.makeMove(new SkatMove(ActionType.PASS));
        controller.makeMove(new SkatMove(ActionType.PASS));
        controller.makeMove(new SkatMove(ActionType.DROP_SKAT));
        controller.makeMove(new SkatMove(new Trump(GameMode.GRAND)));

    }


    public void mainTest(){

        andi();

    }

    void maik(){

    }

    void andi(){

        consoleSetUp();
        var console = new Console(controller);



    }

    /* print Funktionen -> eigene Klasse */



    /* toString Funktionen */




}


















