package skatguiframework;

import console.Print;
import controller.SkatController;
import org.json.simple.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkPlayer implements Player {

    static final int PORT = 6066;

    int mPlayerIndex = -1;

    State mState;
    SkatController mController = null;
    String mName = "no_name";


    /**
     * Host is Player 1
     * Client is Player 2
     */

    /** Constructor SERVER / HOST */
    public NetworkPlayer(SkatController controller) throws IOException {

        var network = "Network";

        this.mController = controller;
        controller.addPlayer(this);

        Print.debug(network, "⇢ The Network Player is SERVER");

        var serverSock = new ServerSocket(PORT);
        Socket sock = serverSock.accept();

        Print.debug(network, "⇢ Waiting for Client...");
        this.mState = State.WAITING_FOR_CLIENT;

        var out = new DataOutputStream(sock.getOutputStream());
        out.writeUTF(playerNameList().toJSONString());

        var in = new DataInputStream(sock.getInputStream());
        this.mState = State.CONNECTED;
        this.mName = in.readUTF();


    }

    /** Constructor CLIENT */
    public NetworkPlayer(SkatController controller, String ip) {

        this.mController = controller;
        controller.addPlayer(this);


    }


    /* OTHER */

    JSONObject playerNameList() {

        var playerNames = mController.getPlayerNames();

        var obj = new JSONObject();
        obj.put(playerNames, "PLAYERNAMES");

        return obj;

    }

    /* GETTER */

    public String getName() {
        return mName;
    }

    /** listen for the turn of the other party */
    @Override
    public JSONObject requestMove(JSONObject inputType) {

        if ( mState != State.CONNECTED ) {
            Print.debug("Network", "A move was request from a p2p connection but there is no connection established.");
            return null;
        }


        return  null;

    }

}

enum State {

    WAITING_FOR_CLIENT,
    CONNECTED,
    DISCONNECTED;

}
