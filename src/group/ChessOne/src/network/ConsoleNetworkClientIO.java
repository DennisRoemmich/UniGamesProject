package network;

import framework.PrintToConsole;
import java.util.Scanner;

public class ConsoleNetworkClientIO implements NetworkClientIO {

    @Override
    public String getHostIP(){
        PrintToConsole.println("Please enter the host IP:");
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
