package network;

import engine.Chess;
import engine.GameOwner;
import engine.board.ChessMove;
import framework.Player;
import framework.Presenter;
import org.json.simple.JSONObject;
import torpedo.TorpedoChess;

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

    private NetworkClientIO io;

    String hostName = "Host";
    String clientName = "Client";

    String hostIP;
    Socket sock = null;

    private Optional<Chess> mGame = Optional.empty();
    private BlockingQueue<JSONObject> moveQueue = new LinkedBlockingQueue<>();


    public ClientController(NetworkClientIO io, Presenter presenter, Player player){
        this.io = io;
        this.mPlayer = player;
        this.mPresenter = presenter;
    }

    public void setupConnection() {
        hostIP = io.getHostIP();
        try {
            setUpHostConnection();
        } catch (Exception e){
            io.presentMessage(NetworkClientMessage.HOST_CONNECTION_REFUSED);
        }
    }
    private void setUpHostConnection() throws IOException {

        sock = new Socket(hostIP, PORT);

        io.presentMessage(NetworkClientMessage.CONNECTING_TO_HOST);

        var gameMode = listen();
        send(clientName);

        io.presentMessage(NetworkClientMessage.HOST_CONNECTED);

        if (gameMode == "Torpedo") {
            mGame = Optional.of(new TorpedoChess());
        } else {
            mGame = Optional.of(new Chess());
        }

    }

    @Override
    public void run() {

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
                io.presentMessage(NetworkClientMessage.RECEIVE_MOVE_FAILED);
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

                var moveJSon = moveQueue.poll();

                if (moveJSon.containsKey("quit")) {
                    validConnection = false;
                }

                var moveOut = ChessMove.valueOf(moveJSon);

                try {
                    send(moveOut.toJSon().toJSONString());
                    mGame.get().makeMove(moveOut);
                    mPresenter.refreshOutput();
                } catch (Exception e){
                    io.presentMessage(NetworkClientMessage.SEND_MOVE_FAILED);
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

    @Override
    public void addMoveToQueue(JSONObject move) {
        moveQueue.add(move);
    }
}















