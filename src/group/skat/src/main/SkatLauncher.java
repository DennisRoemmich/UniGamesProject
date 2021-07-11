package main;

import console.Console;
import console.Print;
import controller.SkatController;
import javaFX.FXLauncher;
import test.Test;

public class SkatLauncher {

    private static final boolean FX_LAUNCHER = false;
    private static boolean WINDOWS = false;

    public static void main(String[] args){

        // TODO: make arguments decide whether console or FX is opened

        var gameAmount = 12;

        try {

            if (args[0].equals("windows")) {

                WINDOWS = true;
            }

        } catch (Exception e) {}

        var controller = new SkatController(gameAmount, new String[]{"Tabalooga","AngeloMerte","Dönerfrau"});

        if( FX_LAUNCHER ){

            var fxLauncher = new FXLauncher();
            fxLauncher.launchFX(controller);

        } else {

        /*    var test = new Test(controller);
            test.mainTest();//*/

            Print.setWINDOWS(WINDOWS);
            var console = new Console(controller);

        }

    }

}
