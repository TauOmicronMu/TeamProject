package main;

import networking.NetworkClient;
import networking.NetworkServerEngine;

import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * This is the main class for the server. Run it, then run a Main client
 * to connect to it!
 */
public class ServerMain implements Runnable {

    private final int port;
    private NetworkServerEngine networkEngine;
    private ConcurrentLinkedQueue<Player> waitQueue = new ConcurrentLinkedQueue<>();

    private ServerMain(int port) {
        this.port = port;
    }

    /**
     * Setup the main server class so it can communicate via the network.
     */
    private void initialize() {
        networkEngine = new NetworkServerEngine(port);
        networkEngine.initialize();
    }

    /**
     * The run() method handles each successive client based on the
     * type of game they've requested.
     */
    public void run() {
        // Block until a new client has connected.
        while (true) {
            // Wait for client to connect.
            Socket clientSocket = networkEngine.acceptNewConnection();
            NetworkClient client = new NetworkClient(clientSocket);
            Player player = new HumanPlayer(client);

            // Do they want to play against a human or an AI?
            OpponentType requestedOpponentType = (OpponentType) player.waitForMessage().getObject();

            // If they want to play against an AI, make it so.
            if (requestedOpponentType == OpponentType.AI) {
                Player opponent = new AIPlayer();
                new Match(player, opponent).begin();
            }

            // Otherwise, they want to play against a human.
            else if (requestedOpponentType == OpponentType.HUMAN) {

                // Check if there's anybody already queued up to play.
                Player otherPlayer = waitQueue.poll();

                // If not, just add this player to the wait queue.
                if (otherPlayer == null) {
                    waitQueue.add(player);
                }

                // If there is, make a new match between the two players.
                else {
                    new Match(player, otherPlayer).begin();
                }
            }
        }
    }

    public static void main(String... args) {
        ServerMain main = new ServerMain(8080);
        main.initialize();
        main.run();
    }
}
