package network;

import engine.Chess;
import engine.GameOwner;
import engine.board.ChessMove;
import chessframework.Player;
import chessframework.Presenter;
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
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class ClientController implements Runnable, GameOwner {

    private static final int PORT = 6066;

    private Player mPlayer;
    private Presenter mPresenter;

    private NetworkClientIO mIo;

    String mClientName = "Client";

    String mHostIP;
    Socket mSock = null;

    private Optional<Chess> mGame = Optional.empty();
    private BlockingQueue<JSONObject> mMoveQueue = new LinkedBlockingQueue<>();


    public ClientController(NetworkClientIO io, Presenter presenter, Player player) {
        this.mIo = io;
        this.mPlayer = player;
        this.mPresenter = presenter;
    }

    public void setupConnection() {
        mHostIP = mIo.getHostIP();
        try {
            setUpHostConnection();
        } catch (Exception e) {
            mIo.presentMessage(NetworkClientMessage.HOST_CONNECTION_REFUSED);
        }
    }
    
    private void setUpHostConnection() throws IOException {

        mSock = new Socket(mHostIP, PORT);

        mIo.presentMessage(NetworkClientMessage.CONNECTING_TO_HOST);

        var gameMode = listen();
        send(mClientName);

        mIo.presentMessage(NetworkClientMessage.HOST_CONNECTED);

        if (gameMode.equals("Torpedo")) {
            mGame = Optional.of(new TorpedoChess());
        } else {
            mGame = Optional.of(new Chess());
        }

    }

    @Override
    public void run() {

        startGame();
    }

    public void startGame() {
        mPresenter.refreshOutput();
        gameLoop();
    }

    @SuppressWarnings("unchecked")
	private void gameLoop() {
        boolean validConnection = true;

        do {
            String moveString = "";
            try {
                moveString = listen();
            } catch (Exception e) {
                mIo.presentMessage(NetworkClientMessage.RECEIVE_MOVE_FAILED);
                validConnection = false;
            }

            if (validConnection && mGame.isPresent()) {

                var moveIn = ChessMove.valueOf(moveString, mGame.get());
                mGame.get().makeMove(moveIn);

                mPresenter.refreshOutput();
                
                JSONObject requestJSon = new JSONObject();
                requestJSon.put("type", "move");
                mPlayer.requestMove(requestJSon);

                while (mMoveQueue.peek() == null) {
                    // Waiting for next move
                }

                var moveJSon = mMoveQueue.poll();

                if (moveJSon.containsKey("quit")) {
                    validConnection = false;
                }

                var moveOut = ChessMove.valueOf(moveJSon);

                try {
                    send(moveOut.toJSon().toJSONString());
                    mGame.get().makeMove(moveOut);
                    mPresenter.refreshOutput();
                } catch (Exception e) {
                    mIo.presentMessage(NetworkClientMessage.SEND_MOVE_FAILED);
                    validConnection = false;
                }
            }
        } while (validConnection);
    }

    private String listen() throws IOException {

        DataInputStream in = new DataInputStream(mSock.getInputStream());
        return in.readUTF();

    }

    private void send(String message) throws IOException {

            DataOutputStream out = new DataOutputStream(mSock.getOutputStream());
            out.writeUTF(message);

    }

    public Optional<Chess> getGame() {
        return mGame;
    }

    @Override
    public void addMoveToQueue(JSONObject move) {
        mMoveQueue.add(move);
    }
}















