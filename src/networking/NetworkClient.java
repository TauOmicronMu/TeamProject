package networking;


import main.OpponentType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;

/**
 * A NetworkClient exists to abstract away some setup code for creating a
 * NetworkClientEngine and initializing it, then defaulting to NetworkUser
 * for everything else.
 */
public class NetworkClient {
    private final Socket socket;
    private ObjectOutputStream objectOutputStream = null;
    private ObjectInputStream objectInputStream = null;

    public NetworkClient(Socket clientSocket) {
        this.socket = clientSocket;
        try {
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static NetworkClient fromHostPort(String host, int port) {
        Socket socket = null;
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            System.err.println("Couldn't open a socket connection. Stopping...");
            System.exit(1);
        }
        return new NetworkClient(socket);
    }

    public Message waitForMessage() {
        try {
            return (Message) this.objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public Optional<Message> pollForMessage() throws IOException {
        try {
            if (this.objectInputStream.available() > 0) {
                return Optional.of((Message) objectInputStream.readObject());
            }
            else {
                return Optional.empty();
            }
        } catch (IOException e) {
            System.err.println("Couldn't read from socket.");
            e.printStackTrace();
            System.out.println("\n\n\n");
            throw e;
        } catch (ClassNotFoundException e) {
            System.err.println("Couldn't find class:");
            e.printStackTrace();
            System.out.println("\n\n\n");
            System.exit(1);
        }
        return null;
    }
}
