package network;

import engine.Chess;
import engine.board.ChessMove;
import framework.GameController;
import framework.Player;
import framework.Presenter;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * This Controller is used for the client side in case of network usage
 */
public class ClientController extends GameController {

    private static final int PORT = 6066;

    String hostName = "Host";
    String clientName = "Client";

    String hostIP;
    Socket sock = null;
    Player player;
    Presenter presenter;

    private Chess mChessGame;


    public ClientController(Player guiPlayer, Presenter presenter){

        this.player = guiPlayer;
        this.presenter = presenter;
        this.mChessGame = new Chess();

        setParams();

        try {
            setUpHostConnection();
        } catch (Exception e){

            System.out.println("Couldn't connect to the Host. Exception:\n" + e);

        }


    }

    public Chess getGame(){
        return mChessGame;
    }

    public void startGame(){
        presenter.refreshOutput();
        gameLoop();
    }

    private void gameLoop(){
        boolean validConnection = true;

        do {
            String moveString = "";
            try {
                moveString = listen();
            } catch (Exception e){
                System.out.println("Failed to receive move:\n" + e);
                validConnection = false;
            }

            if (validConnection){

                var moveIn = ChessMove.valueOf(moveString, mChessGame);
                mChessGame.makeMove(moveIn);
                presenter.refreshOutput();

                var moveOut = ChessMove.valueOf(player.requestMove(createRequestJSON("move")));
                mChessGame.makeMove(moveOut);
                presenter.refreshOutput();

                try {
                    send(moveOut.toString());
                } catch (Exception e){

                    System.out.println("Failed to send move:\n" + e);
                    validConnection = false;

                }
            }
        } while(validConnection);
    }

    private void setParams(){
        hostIP = JOptionPane.showInputDialog(null, "You play as Client. Please enter the IP of the host.","Network Connection", JOptionPane.QUESTION_MESSAGE);
    }

    private void setUpHostConnection() throws IOException {

        sock = new Socket(hostIP, PORT);

        System.out.print("Connecting to Server...\n");

        var hostName = listen();
        send(clientName);

        System.out.print("Connection to Host " + hostName + " established.\n");

    }

    private String listen() throws IOException {

        DataInputStream in = new DataInputStream(sock.getInputStream());
        return in.readUTF();

    }

    private void send(String message) throws IOException {

            DataOutputStream out = new DataOutputStream(sock.getOutputStream());
            out.writeUTF(message);

    }


    @Override
    public void executeMove(JSONObject move) {

    }

    @Override
    public JSONObject metaSettingsToJSON() {
        return null;
    }

    @Override
    public JSONObject gameSettingsToJSON() {
        return null;
    }

    @Override
    public void restoreMetaSettings(JSONObject metaSettings) {

    }

    @Override
    public void restoreGameSettings(JSONObject gameSettings) {

    }

    @Override
    public void newGame() {

    }
}















