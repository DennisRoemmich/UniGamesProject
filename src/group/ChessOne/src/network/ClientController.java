package network;

import engine.Chess;
import engine.GameOwner;
import engine.board.ChessMove;
import framework.GameController;
import framework.Player;
import framework.Presenter;
import org.json.simple.JSONObject;
import torpedo.TorpedoChess;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * This Controller is used for the client side in case of network usage
 */
public class ClientController implements Runnable, GameOwner {

    private static final int PORT = 6066;

    private Player mPlayer;
    private Presenter mPresenter;

    private boolean mStandardChess = true;

    String hostName = "Host";
    String clientName = "Client";

    String hostIP;
    Socket sock = null;

    private Optional<Chess> mGame = Optional.empty();
    private BlockingQueue<JSONObject> moveQueue = new LinkedBlockingQueue<>();


    public ClientController(Player player, Presenter presenter){
        this.mPlayer = player;
        this.mPresenter = presenter;

        setParams();

        try {
            setUpHostConnection();
        } catch (Exception e){
            System.out.println("Couldn't connect to the Host. Exception:\n" + e);

        }
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

    @Override
    public void run() {
        if (!mStandardChess) {
            mGame = Optional.of(new TorpedoChess());
        } else {
            mGame = Optional.of(new Chess());
        }
        startGame();
    }

    public void startGame(){
        mPresenter.refreshOutput();
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

            if (validConnection && mGame.isPresent()){

                var moveIn = ChessMove.valueOf(moveString, mGame.get());
                mGame.get().makeMove(moveIn);

                mPresenter.refreshOutput();

                JSONObject requestJSON = new JSONObject();
                requestJSON.put("type", "move");
                mPlayer.requestMove(requestJSON);

                while (moveQueue.peek() == null) {
                }
                var moveOut = ChessMove.valueOf(moveQueue.poll());

                try {
                    send(moveOut.toJSon().toJSONString());
                    mGame.get().makeMove(moveOut);
                    mPresenter.refreshOutput();
                } catch (Exception e){

                    System.out.println("Failed to send move:\n" + e);
                    validConnection = false;

                }
            }
        } while(validConnection);
    }

    private String listen() throws IOException {

        DataInputStream in = new DataInputStream(sock.getInputStream());
        return in.readUTF();

    }

    private void send(String message) throws IOException {

            DataOutputStream out = new DataOutputStream(sock.getOutputStream());
            out.writeUTF(message);

    }

    public Optional<Chess> getGame(){
        return mGame;
    }

    public BlockingQueue<JSONObject> getMoveQueue() {
        return moveQueue;
    }
}















