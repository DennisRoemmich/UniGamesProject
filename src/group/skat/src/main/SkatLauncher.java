package main;

import javaFX.FXLauncher;

public class SkatLauncher {

    public static void main(String[] args){

        var fxLauncher = new FXLauncher();
        fxLauncher.launchFX();

    }

    public static void print(String key, String str){

        if(key.equals("mainTest")) {

            System.out.println(str);

        }

    }


}
