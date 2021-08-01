package network;

import engine.Controller;
import chessframework.Player;
import chessframework.PrintToConsole;
import chessframework.WriteError;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class implementing the network player
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class NetworkPlayer implements Player {

    private static final int PORT = 6066;

    String mHostName = "Host";
    String mClientName = "Client";

    Controller mController;
    String mHostIP;
    Socket mSock;

    public NetworkPlayer(Controller controller) {
        this.mController = controller;
    }

    public NetworkPlayer(Controller controller, String gameMode) {
        this.mController = controller;
        tryConnectingClient(gameMode);
    }

    public void tryConnectingClient(String gameMode) {
        try {
            setUpClientConnection(gameMode);
        } catch (Exception e) {
            PrintToConsole.println("Connection to client failed:\n" + e);
        }
    }

    private void setUpClientConnection(String gameMode) throws IOException {

        InetAddress addr = InetAddress.getByName("0.0.0.0");

        PrintToConsole.println("Waiting for client...");

        try (ServerSocket serverSock = new ServerSocket(PORT, 128, addr)) {
            mSock = serverSock.accept();

            send(gameMode);
            mClientName = listen();

            PrintToConsole.println("Client connected.");
        }

    }

    private String listen() throws IOException {

        DataInputStream in = new DataInputStream(mSock.getInputStream());
        return in.readUTF();

    }

    private void send(String message) throws IOException {
        DataOutputStream out = new DataOutputStream(mSock.getOutputStream());
        out.writeUTF(message);
    }

    public void requestMove(JSONObject inputType) {

        if (inputType.containsKey("type") && inputType.get("type").equals("quit")) {
            try {
                send("quit");
            } catch (Exception e) {
                WriteError.writeErrorLog("Client couldn't be notified before quitting");
            }
        }

        var moveOut = mController.getLastMove();

        try {
            send(moveOut.toString());
        } catch (Exception e) {
            PrintToConsole.println("Sending last move to connected client failed:\n" + e);
            mController.quitGame();
            return;
        }

        try {
            var game = mController.getGame();
            if (game.isPresent()) {
                String moveString = listen();
                JSONParser parser = new JSONParser();
                JSONObject moveJSon = (JSONObject) parser.parse(moveString);
                mController.addMoveToQueue(moveJSon);
            } else {
                throw new IllegalStateException();
            }
        } catch (Exception e) {
            PrintToConsole.println("Receiving move from client failed:\n" + e);
            mController.quitGame();
        }
    }
}











