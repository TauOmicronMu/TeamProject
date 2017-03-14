package networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * A NetworkServerEngine exists to specialize the initialization of a
 * NetworkEngine for the use of a game server. Instead of connecting
 * to a remote server using a hostname and port, we use a ServerSocket
 * to listen for incoming connections.
 */
public class NetworkServerEngine extends NetworkEngine implements Runnable {

    private final int port;
    private ServerSocket serverSocket = null;


    public NetworkServerEngine(int port) {
        this.port = port;
    }

    /**
     * Create a server-socket, acceptNewConnection a single connection and setup the
     * NetworkEngine using it.
     */
    public void initialize() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Couldn't initialize socket (connection to client).");
            System.exit(1);
        }
    }

    public Socket acceptNewConnection() {
        try {
            return serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Couldn't listen for conenctions. Shutting down...");
            System.exit(1);
        }
    }
}
