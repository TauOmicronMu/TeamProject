package networking;

import java.io.IOException;
import java.net.Socket;

/**
 * A NetworkClientEngine exists to specialize the initialization of a
 * NetworkEngine for the local game client. It connects to a server
 * given a host and a port.
 */
class NetworkClientEngine extends NetworkEngine implements Runnable {

    /**
     * Given a host and port, connect to a server and initialize the network engine.
     * @param host The server's hostname.
     * @param port The server's port.
     */
    void initialize(String host, int port) {
        Socket socket;

        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            System.err.println("Couldn't initialize socket (connection to server).");
            e.printStackTrace();
            return;
        }

        super.initialize(socket);
    }
}
