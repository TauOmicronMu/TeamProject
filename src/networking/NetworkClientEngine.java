package networking;

import java.io.IOException;
import java.net.Socket;

/**
 * A NetworkClientEngine exists to specialize the initialization of a
 * NetworkEngine for the local game client. It connects to a server
 * given a host and a port.
 */
public class NetworkClientEngine extends NetworkEngine implements Runnable {

    /**
     * Given a host and port, make an initial connection to the server.
     * Initialize ourselves as a NetworkEngine, using this connection.
     */
    public void initialize(String host, int port) {

        // Attempt to setup the socket, handling any IO exceptions.
        Socket socket;

        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            System.err.println("Couldn't engineInitialize socket (connection to server).");
            e.printStackTrace();
            return;
        }

        // NetworkEngine's engineInitialize method should create our input/output capabilities,
        // including starting the Thread which polls for messages and adds them to a queue.
        super.engineInitialize(socket);
    }

    public Socket getSocket() {
        return socket;
    }
}
