package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    static final int PORT = 6066;
    static final String IP = "localhost";

    public static void main(String[] args) throws IOException {

        var in = new Scanner(System.in);

        System.out.print("[C]lient or [S]erver?\n-> ");
        String s = in.nextLine();
        s = s.toUpperCase(Locale.ROOT);

        if (s.equals("S")) {

            createServer();

            return;
        }

        if (s.equals("C")) {

            client();

            return;
        }

        System.out.println("Invalid input.");

    }


    static void createServer() throws IOException {

        System.out.print("Waiting for Client...\n");

        InetAddress addr = InetAddress.getByName("0.0.0.0");

        ServerSocket serverSock = new ServerSocket(PORT, 128, addr);
        Socket sock = serverSock.accept();

        var scanner = new Scanner(System.in);

        String word = ":q";

        String welcome = "[SERVER] You connected to the server!\n";

        do {

            System.out.print("Enter message \n:");
            word = scanner.nextLine();

            DataOutputStream out = new DataOutputStream(sock.getOutputStream());
            out.writeUTF(word + "\n");

            System.out.print(">> Waiting for message \n:");
            DataInputStream in = new DataInputStream(sock.getInputStream());
            System.out.print("[Client] " + in.readUTF() + "\n");

        } while (!word.equals(":q"));


        sock.close();

    }


    static void client() throws IOException {

        var scanner = new Scanner(System.in);

        System.out.print("Enter ip of the host\n:");
        String ip = scanner.nextLine();

        Socket sock = new Socket(ip, PORT);

        System.out.print("Connecting to Server...\n");

        DataInputStream in = new DataInputStream(sock.getInputStream());

        System.out.print("Connection to Server established.\n");
        System.out.print("[Host] " + in.readUTF() + "\n");

        String word = ":q";

        do {

            System.out.print("Enter message \n:");
            word = scanner.nextLine();

            DataOutputStream out = new DataOutputStream(sock.getOutputStream());
            out.writeUTF(word + "\n");

            System.out.print(">> Waiting for message \n:");
            in = new DataInputStream(sock.getInputStream());
            System.out.print("[P-CL] " + in.readUTF() + "\n");

        } while (!word.equals(":q"));

        sock.close();

    }


}
