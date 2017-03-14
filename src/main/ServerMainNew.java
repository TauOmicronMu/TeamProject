package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMainNew {

    private final int port;
    private ServerSocket serverSocket;

    ServerMainNew(int port) {
        this.port = port;
        this.serverSocket = null;
    }

    void initialize() {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("[ERROR] ServerMainNew.initialize: Couldn't initialize server socket.");
            System.exit(1);
        }
    }

    void run() {
        while (true) {
            try {
                System.out.println("[INFO] ServerMainNew.run: Waiting for client connection.");
                Socket clientSocket = serverSocket.accept();
                System.out.println("[INFO] ServerMainNew.run: clientSocket is " + clientSocket);
                new Thread(new ServerSideClientHandler(clientSocket)).start();
            } catch (IOException e) {
                System.err.println("[ERROR] ServerMainNew.run: Couldn't accept connection from client.");
                System.exit(1);
            }
        }

    }

    public static void main(String... args) {
        ServerMainNew main = new ServerMainNew(Constants.PORT);
        main.initialize();
        main.run();
    }
}
