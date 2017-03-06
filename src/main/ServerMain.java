package main;

import networking.Message;
import networking.NetworkServer;
import networking.Client;

import java.awt.geom.Point2D;
import java.util.concurrent.BlockingQueue;

/**
 * This is the main class for the server. Run it, then run a Main client
 * to connect to it!
 */
public class ServerMain extends NetworkServer implements Runnable {

    private GameState gameState;
    private BlockingQueue<Client> players;

    private ServerMain(int port) {
        super(port);

    }

    /**
     * The run() method of ServerMain waits for a client to connect,
     * initializes a new GameState for that client, and runs the
     * main server-loop.
     *
     * Currently this loop is at a fixed timestep, but this will change
     * in future.
     */
    public void run() {
        // Block until a new client has connected.
        System.out.println("Waiting for client to connect...");
        waitForClient();

        // Get opponent type and shiz ~Tom
        Message opponentTypeMsg = null;
        try {
            opponentTypeMsg = waitForMessage();
        }
        catch(InterruptedException e) {
            System.err.println(e);
            System.exit(1);
        }
        OpponentType opponentType = (OpponentType) opponentTypeMsg.getObject();

        // Handle the creation of matches
        createMatch(opponentType);

        // Create a GameState for the client.
        gameState = new GameState(800, 800);
        gameState.setup();
        gameState.generatePlatforms();
        gameState.generateItems();
 

        // Main loop:
        while (true) {

            // First of all, send the current game-state to the client.
            if (!sendGameState()) {
                System.err.println("Lost connection to client.");
                break;
            }

            // Handle any input messages the client has sent us.
            if (!handleMessages()) break;
            gameState.updateLogic();
            gameState.updatePhysics();                   
            
            gameState.printScore();

            // Sleep for a fixed duration. Todo: think about variable timestep!
            try {
                Thread.sleep(1000/120);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handles the creation of matches with two players.
     * @param opType The type of opponent the player should be playing against
     */
    private void createMatch(OpponentType opType) {
        // If the player is an AI, set up a match straight away
        // otherwise, wait for another player to connect (that isn't
        // looking to play with an AI)
    }

    /**
     * Wrapper method to send the current GameState to the client.
     * @return boolean Whether the message has been sent successfully.
     */
    private boolean sendGameState() {
        Message m = new Message(gameState);
        return sendMessage(m);
    }




    /**
     * This function handles the behaviour of a user pressing a key on their keyboard.
     * @param key A String representing the key pressed.
     */
    private void handleKeyPress(String key) {
        switch(key) {
            case "a": {
                gameState.getBall().moveLeft();
                break;
            }
            case "d": {
                gameState.getBall().moveRight();
                break;
            }
        }
    }

    /**
     * This function handles the behaviour of a mouseclick.
     * @param coords The coordinates of the click.
     */
    private void handleMouseClick(Point2D.Float coords) {
        gameState.getBall().setX(coords.getX());
    }

    public static void main(String... args) {
        ServerMain main = new ServerMain(8080);
        main.initialize();
        while (true) {
            main.run();
        }
    }
}
