package networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class NetworkServerEngine extends NetworkEngine implements Runnable {
    void initialize(int port) {
        ServerSocket serverSocket;
        Socket socket;

        // Bind the socket to our server.
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
