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
class NetworkServerEngine extends NetworkEngine implements Runnable {

    /**
     * Create a server-socket, accept a single connection and setup the
     * NetworkEngine using it.
     * @param port The port we should listen on for a connection.
     */
    void initialize(int port) {
        ServerSocket serverSocket;
        Socket socket;

        try {
            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Couldn't initialize socket (connection to server).");
            e.printStackTrace();
            return;
        }

        super.initialize(socket);
    }
}
