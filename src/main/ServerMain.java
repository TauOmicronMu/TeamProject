package main;

import networking.Message;
import networking.NetworkServer;

import java.awt.geom.Point2D;

/**
 * This is the main class for the server. Run it, then run a Main client
 * to connect to it!
 */
public class ServerMain extends NetworkServer implements Runnable {

    private GameState gameState;

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

        // Create a GameState for the client.
        gameState = new GameState(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        gameState.setUp();
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
                Thread.sleep(Constants.FPS_SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
     * This method is called whenever we receive a message from a client.
     * We should check whether the client has sent keyboard or mouse input
     * and act accordingly.
     * @param message The message we've been sent by the Client.
     */
    @Override
    public void handleMessage(Message message) {
        Object o = message.getObject();
        if (o.getClass() == Point2D.Float.class) {
            handleMouseClick((Point2D.Float) o);
        } else if (o.getClass() == String.class) {
            handleKeyPress((String) o);
        } else {
            System.out.println("Message didn't translate properly");
        }
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
