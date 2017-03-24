package networking.integration;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * An EchoProtocol should handle a single client connection. The behaviour
 * it defines is to reply to any object it receives with the same object.
 */
public class EchoProtocol implements Runnable {
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    EchoProtocol(Socket s) throws IOException {
        this.socket = s;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        System.out.println("Connected to socket: "+socket.getInetAddress());
        while (true) {
            try {
                Object o = this.inputStream.readObject();
                System.out.println("Got an object: "+o);
                outputStream.writeObject(o);
                System.out.println("Sent the object.");
                outputStream.flush();
                System.out.println("Flushed output stream.");
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("Server couldn't read or write. Stopping...");
                break;
            } catch(ClassNotFoundException e) {
                System.err.println("Couldn't resolve class:");
                e.printStackTrace();
                break;
            }
        }
    }
}
