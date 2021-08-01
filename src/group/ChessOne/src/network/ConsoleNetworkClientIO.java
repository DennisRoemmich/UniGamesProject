package network;

import chessframework.PrintToConsole;
import java.util.Scanner;

/**
 * Eventhandler for highlighting the squares
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class ConsoleNetworkClientIO implements NetworkClientIO {

    @Override
    public String getHostIP() {
        PrintToConsole.println("Please enter the host IP:");
		Scanner scanner = new Scanner(System.in);
		String ip = scanner.nextLine();
		scanner.close();
        return ip;
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
