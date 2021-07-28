package main;

import console.Console;
import console.Print;
import controller.SkatController;
import framework.NetworkController;
import framework.NetworkPlayer;
import framework.Player;
import javaFX.FXLauncher;
import jdk.jshell.EvalException;
import test.Test;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SkatLauncher {

    private static boolean FX_LAUNCHER = false;
    private static boolean WINDOWS = false;

    static final int GAME_AMOUNT = 12;

    static final int GUI_PLAYER = 2;
    static final int NETWORK_PLAYER = 1;
    static final int KI_PLAYER = 0;

    public static void main(String[] args) throws IOException {


        try {

            FX_LAUNCHER = args[0].equals("gui");

        } catch (Exception ignored) {

        }


        var standardPlayerNames = new ArrayList<String>(Arrays.asList("Yoshi","HuiBuh","Neymar","Mr. Crabs","Chewbacca"));
        var playerNo = GUI_PLAYER + NETWORK_PLAYER + KI_PLAYER;

        while (standardPlayerNames.size() > playerNo ){
            standardPlayerNames.remove(standardPlayerNames.size()-1);
        }

        var controller = new SkatController(GAME_AMOUNT, standardPlayerNames);

        new Test(controller);


        /* NETWORK PLAYER */

        if (NETWORK_PLAYER != 0){

            if (NETWORK_PLAYER > 1) {
                Print.debug("ERROR", "MORE THAN ONE NETWORK PLAYER! NOT SUPPORTED YET! SO YEAH, THIS WILL PROBABLY BREAK EVERYTHING.");
            }

            var input = JOptionPane.showOptionDialog(null, "Configure P2P Connection as:","Peer2Peer Connection",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null,
                    new String[]{"HOST", "CLIENT"}, "HOST");

            if (input == 0) {

                var networkPlayer = new NetworkPlayer(controller);

            }

            if (input == 1) {

                var ip = JOptionPane.showInputDialog("Enter the IP of the host:");
                var networkPlayer = new NetworkPlayer(controller, ip);

            }

        }

        /* ADD PLAYER */

            /* GUI PLAYER - LAST! */


        if (FX_LAUNCHER) {

            var fxLauncher = new FXLauncher();
            fxLauncher.launchFX(controller); // Process will fall in a loop here

        } else {

            try {

                WINDOWS = args[0].equals("windows");

            } catch (Exception ignored) {}

            Print.setWINDOWS(WINDOWS);

        }






    }




}










