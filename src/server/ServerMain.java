package server;

import audio.AudioEngine;
import shared.Constants;
import server.matchmaking.ServerSideClientHandler;
import server.player.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerMain {

    private final int port;
    private ServerSocket serverSocket;
    private Queue<Player> playerQueue;

    ServerMain(int port) {
        this.port = port;
        this.serverSocket = null;
        this.playerQueue = new LinkedBlockingQueue<>(2);
    }

    void initialize() {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("[ERROR] ServerMain.initialize: Couldn't initialize server socket.");
            System.exit(1);
        }
    }

    void run() {
        AudioEngine.isClient = false;
        while (true) {
            try {
                System.out.println("[INFO] ServerMain.run: Waiting for client connection.");
                Socket clientSocket = serverSocket.accept();
                System.out.println("[INFO] ServerMain.run: clientSocket is " + clientSocket);
                new Thread(new ServerSideClientHandler(clientSocket, playerQueue)).start();
            } catch (IOException e) {
                System.err.println("[ERROR] ServerMain.run: Couldn't accept connection from client.");
                System.exit(1);
            }
        }

    }

    public static void main(String... args) {
        ServerMain main = new ServerMain(Constants.PORT);
        main.initialize();
        main.run();
    }
}
