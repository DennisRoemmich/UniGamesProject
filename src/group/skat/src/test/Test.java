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
        controller.makeMove(move);

        move = new SkatMove(ActionType.PASS);
        controller.makeMove(move);
        move = new SkatMove(ActionType.PASS);
        controller.makeMove(move);
        move = new SkatMove(ActionType.RAISE_OR_ACCEPT);
        controller.makeMove(move);
        move = new SkatMove(ActionType.DROP_SKAT);
        controller.makeMove(move);

        var trump = new Trump(GameMode.GRAND);

        move = new SkatMove(trump);
        controller.makeMove(move);

        move = new SkatMove(0);
        controller.makeMove(move);


    }

    /* print Funktionen -> eigene Klasse */



    /* toString Funktionen */




}


















