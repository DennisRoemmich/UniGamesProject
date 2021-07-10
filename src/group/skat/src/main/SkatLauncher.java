package main;

import controller.SkatController;
import javaFX.FXLauncher;
import test.Test;

public class SkatLauncher {

    private static final boolean FX_LAUNCHER = false;

    public static void main(String[] args){

        // TODO: make arguments decide whether console or FX is opened

        var controller = new SkatController(12, new String[]{"Tabalooga","AngeloMerte","Dönerfrau"});

        if( FX_LAUNCHER ){

            var fxLauncher = new FXLauncher();
            fxLauncher.launchFX(controller);

        } else {

            var test = new Test(controller);
            test.mainTest();

        }

    }

}
