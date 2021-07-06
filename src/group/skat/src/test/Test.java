package test;

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

        mainTest();

    }


    void mainTest(){

        andi();

    }

    void maik(){

    }

    void andi(){

        var move = new SkatMove(ActionType.NEW_GAME);
        controller.forwardMove(move);

        move = new SkatMove(ActionType.PASS);
        controller.forwardMove(move);
        move = new SkatMove(ActionType.PASS);
        controller.forwardMove(move);
        move = new SkatMove(ActionType.RAISE_OR_ACCEPT);
        controller.forwardMove(move);
        move = new SkatMove(ActionType.DROP_SKAT);
        controller.forwardMove(move);

        var trump = new Trump(GameMode.GRAND);

        move = new SkatMove(trump);
        controller.forwardMove(move);

        move = new SkatMove(0);
        controller.forwardMove(move);


    }

    /* print Funktionen -> eigene Klasse */



    /* toString Funktionen */




}


















