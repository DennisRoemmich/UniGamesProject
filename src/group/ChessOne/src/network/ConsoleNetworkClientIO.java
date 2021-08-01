package network;

import engine.Chess;
import engine.board.ChessMove;
import framework.Player;
import framework.Presenter;
import framework.PrintToConsole;
import org.json.simple.JSONObject;
import torpedo.TorpedoChess;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConsoleNetworkClientIO implements NetworkClientIO {

    @Override
    public String getHostIP(){
        System.out.println("Please enter the host IP:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    @Override
    public void presentMessage(NetworkClientMessage message) {
        String messageString = switch (message) {
            case HOST_CONNECTED -> "Connection to Host established.";
            case SEND_MOVE_FAILED -> "Failed to send move.";
            case CONNECTING_TO_HOST -> "Connecting to Server...";
            case RECEIVE_MOVE_FAILED -> "Failed to receive move.";
            case HOST_CONNECTION_REFUSED -> "Couldn't connect to Host";
            default -> "Unknown Client Message.";
        };
        PrintToConsole.println(messageString);
    }
}
