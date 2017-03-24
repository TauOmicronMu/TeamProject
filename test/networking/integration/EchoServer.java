package networking.integration;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This server simply sends back any objects it receives.
 */
public class EchoServer implements Runnable {
    public void run() {

        // Setup the server socket to listen for connections.
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(25565);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Create a new EchoProtocol for each connection.
        while (true) {
            try {
                Socket s = serverSocket.accept();
                new Thread(new EchoProtocol(s)).start();
            } catch (IOException e) {
                break;
            }
        }
    }

    public static void main(String... args) {
        new Thread(new EchoServer()).start();
    }
}
