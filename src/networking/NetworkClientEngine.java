package networking;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

class NetworkClientEngine extends NetworkEngine implements Runnable {

    void initialize(String host, int address) {
        Socket socket;

        // Bind the socket to our server.
        try {
            socket = new Socket(host, address);
        } catch (IOException e) {
            System.err.println("Couldn't initialize socket (connection to server).");
            e.printStackTrace();
            return;
        }

        super.initialize(socket);
    }
}
