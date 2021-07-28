package framework;

import console.Print;
import controller.SkatController;
import org.json.simple.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class NetworkPlayer implements Player {

    static final int PORT = 6066;

    int playerIndex = -1;

    State state;
    SkatController controller = null;
    String name = "no_name";


    /**
     * Host is Player 1
     * Client is Player 2
     */

    /** Constructor SERVER / HOST */
    public NetworkPlayer(SkatController controller) throws IOException {

        this.controller = controller;
        controller.addPlayer(this);

        Print.debug("Network", "⇢ The Network Player is SERVER");

        ServerSocket serverSock = new ServerSocket(PORT);
        Socket sock = serverSock.accept();

        Print.debug("Network", "⇢ Waiting for Client...");
        this.state = State.WAITING_FOR_CLIENT;

        DataOutputStream out = new DataOutputStream(sock.getOutputStream());
        out.writeUTF(playerNameList().toJSONString());

        DataInputStream in = new DataInputStream(sock.getInputStream());
        this.state = State.CONNECTED;
        this.name = in.readUTF();


    }

    /** Constructor CLIENT */
    public NetworkPlayer(SkatController controller, String ip){

        this.controller = controller;
        controller.addPlayer(this);


    }


    /* OTHER */

    JSONObject playerNameList() {

        var playerNames = controller.getPlayerNames();

        var obj = new JSONObject();
        obj.put(playerNames, "PLAYERNAMES");

        return obj;

    }

    /* GETTER */

    public String getName() {
        return name;
    }

    /** listen for the turn of the other party */
    @Override
    public JSONObject requestMove(JSONObject inputType) {

        if ( state != State.CONNECTED ) {
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
