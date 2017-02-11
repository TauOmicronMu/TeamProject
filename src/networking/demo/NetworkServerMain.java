import networking.Message;
import networking.NetworkServer;


/**
 * A Server is a basic implementation of NetworkServer that might look something like
 * the main GameServer class. It's Runnable, so the main loop is contained in a run() method.
 */
class Server extends NetworkServer implements Runnable {

    private int gameState = 0;

    /**
     * A Server can delegate to NetworkServer for all necessary setup.
     * @param port The port to run our server on.
     */
    Server(int port) {
        super(port);
    }


    /**
     * Run the main server loop. This should update the authoritative game
     * state by applying the user inputs received, and regularly "ping"
     * the connected client with the authoritative game state.
     */
    @Override
    public void run() {

        // Start listening on ports etc.
        initialize();

        // Enter the main loop.
        while (true) {
            try {

                Thread.sleep(5000);
                System.out.println("Server tick");

                // We must implement handleMessage (singular) below.
                handleMessages();
                sendMessage(new Message(String.valueOf(gameState)));

            } catch (InterruptedException e) {
                System.out.println("Server thread interrupted:");
                e.printStackTrace();
                break;
            }
        }
    }

    /**
     * Deal with a single message received from the client. This should be
     * called behind-the-scenes; we just have to implement this method.
     *
     * @param m The message we've been sent.
     */
    @Override
    public void handleMessage(Message m) {
        System.out.println("Got input from client. Updating game state with server-side physics:");
        updateGameState(Integer.valueOf(m.getText()));
    }

    /**
     * Given some player input, update the authoritative game state.
     * In this simple example, this method is correct, unlike that of the
     * client: this is so that we can demonstrate the value "snapping" back to
     * the authoritative value on the server.
     *
     * @param input The input sent to us by the player.
     */
    private void updateGameState(int input) {
        gameState += input;
        gameState %= 20;
    }
}


/**
 * Start up a Server to demonstrate the synchronisation mechanism.
 */
public class NetworkServerMain {
    public static void main(String... args) {
        Server server = new Server(8080);
        new Thread(server).run();
    }
}
