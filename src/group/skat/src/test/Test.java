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

        mainTest();

    }


    void mainTest(){

        andi();

    }

    void maik(){

    }

    void andi(){

        var console = new Console(controller);


    }

    /* print Funktionen -> eigene Klasse */



    /* toString Funktionen */




}


















