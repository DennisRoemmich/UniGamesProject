package main;

import controller.SkatController;
import javaFX.FXLauncher;
import test.Test;

public class SkatLauncher {

    public static void main(String[] args){

        // var fxLauncher = new FXLauncher();
        // fxLauncher.launchFX();

        var controller = new SkatController(12, new String[]{"Tabalooga","AngeloMerte","Dönerfrau"});
        var test = new Test(controller);

    }

}
