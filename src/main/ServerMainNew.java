package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerMainNew {

    private final int port;
    private ServerSocket serverSocket;
    private Queue<Player> playerQueue;

    ServerMainNew(int port) {
        this.port = port;
        this.serverSocket = null;
        this.playerQueue = new LinkedBlockingQueue<>(2);
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
                new Thread(new ServerSideClientHandler(clientSocket, playerQueue)).start();
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
