package network;

import engine.Controller;
import engine.board.ChessMove;
import framework.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class NetworkPlayer implements Player {

    private static final int PORT = 6066;

    String hostName = "Host";
    String clientName = "Client";

    Controller mController;
    String hostIP;
    Socket sock;

    public NetworkPlayer(Controller controller){
        this.mController = controller;
    }

    public NetworkPlayer(Controller controller, String gameMode){
        this.mController = controller;
        tryConnectingClient(gameMode);
    }

    public void tryConnectingClient(String gameMode) {
        try {
            setUpClientConnection(gameMode);
        } catch (Exception e){
            System.out.println("Connection to client failed:\n" + e);
        }
    }

    private void setUpClientConnection(String gameMode) throws IOException {

        InetAddress addr = InetAddress.getByName("0.0.0.0");

        System.out.println("Waiting for client...");

        ServerSocket serverSock = new ServerSocket(PORT, 128, addr);
        sock = serverSock.accept();

        send(gameMode);
        clientName = listen();

        System.out.println("Client connected.");


    }

    private String listen() throws IOException {

        DataInputStream in = new DataInputStream(sock.getInputStream());
        return in.readUTF();

    }

    private void send(String message) throws IOException {
        DataOutputStream out = new DataOutputStream(sock.getOutputStream());
        out.writeUTF(message);
    }

    public void requestMove(JSONObject inputType) {

        var moveOut = mController.getLastMove();

        try {
            send(moveOut.toString());
        } catch (Exception e) {
            System.out.println("Sending last move to connected client failed:\n" + e);
            mController.quitGame();
            return;
        }

        ChessMove moveIn;

        try {
            var game = mController.getGame();
            if (game.isPresent()) {
                String moveString = listen();
                JSONParser parser = new JSONParser();
                JSONObject moveJSON = (JSONObject) parser.parse(moveString);
                mController.getMoveQueue().add(moveJSON);
            } else {
                throw new IllegalStateException();
            }
        } catch (Exception e) {
            System.out.println("Receiving move from client failed:\n" + e);
            mController.quitGame();
            return;
        }

        return;

    }

    @Override
    public BlockingQueue<JSONObject> getRequestQueue() {
        return null;
    }
}











