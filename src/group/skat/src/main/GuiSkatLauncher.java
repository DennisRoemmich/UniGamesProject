package main;

import console.Print;
import controller.SkatController;
import kiplayer.KIPlayer;
import skatguiframework.NetworkPlayer;
import javafxcontroller.FXLauncher;
import test.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * main class for everything
 */
public class GuiSkatLauncher {

    private static boolean mFxLauncher = true;
    private static boolean mWindows = false;

    static final int GAME_AMOUNT = 12;

    static final int GUI_PLAYER = 1;
    static final int NETWORK_PLAYER = 0;
    static final int KI_PLAYER = 2;

    /**
     * main method
     * @param args args
     */
    public static void main(String[] args){


        try {

            mFxLauncher = args[0].equals("gui");

        } catch (Exception ignored) { /* game will start in console */ }


        var standardPlayerNames = new ArrayList<String>(Arrays.asList("Yoshi","HuiBuh","Neymar","Mr. Crabs","Chewbacca"));
        var playerNo = GUI_PLAYER + NETWORK_PLAYER + KI_PLAYER;

        if(playerNo < 3){
            Print.debug("ERROR", "Cannot play with less then 3 players");
            return;
        }

        while (standardPlayerNames.size() > playerNo ){
            standardPlayerNames.remove(standardPlayerNames.size()-1);
        }

        var controller = new SkatController(GAME_AMOUNT, standardPlayerNames.toArray(new String[standardPlayerNames.size()]));

        new Test(controller);


        /* NETWORK PLAYER */

        if (NETWORK_PLAYER != 0){

            if (NETWORK_PLAYER > 1) {
                Print.debug("ERROR", "More than one networkplayer not supported yet");
            }

            var input = JOptionPane.showOptionDialog(null, "Configure P2P Connection as:","Peer2Peer Connection",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null,
                    new String[]{"HOST", "CLIENT"}, "HOST");

            if (input == 0) {

                try {
                    var networkPlayer = new NetworkPlayer(controller);
                } catch (Exception e) {

                }

            }

            if (input == 1) {

                var ip = JOptionPane.showInputDialog("Enter the IP of the host:");
                var networkPlayer = new NetworkPlayer(controller, ip);

            }

        }

        if (KI_PLAYER != 0){

            if (NETWORK_PLAYER > 4) {
                Print.debug("ERROR", "4 it the maximum amount of possible KI Players");
            }

            for (var i = 0; i < KI_PLAYER; i++){

                controller.addPlayer(new KIPlayer(controller, i));

            }


        }

            /* ADD PLAYER */

            /* GUI PLAYER - LAST! */

        if (GUI_PLAYER < 1) {
            Print.debug("ERROR", "There must be at least one GUI Player");
            return;
        }

        if (mFxLauncher) {

            var fxLauncher = new FXLauncher();
            fxLauncher.launchFX(controller, GUI_PLAYER); // Process will fall in a loop here

        } else {

            try {

                mWindows = args[0].equals("windows");

            } catch (Exception ignored) { /* console will go with mac-viable symbols */ }

            Print.setWINDOWS(mWindows);

        }

    }

}










