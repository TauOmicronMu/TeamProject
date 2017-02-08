package networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NetworkEngine implements Runnable {

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private boolean running;
    private Socket outSocket;
    private ServerSocket serverSocket;
    private ConcurrentLinkedQueue<Message> messages;

    public void initialize(String host, int address) {
        try {
            outSocket = new Socket(host, address);
        } catch (IOException e) {
            System.err.println("Couldn't initialize socket.");
            e.printStackTrace();
            return;
        }

        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Couldn't get socket's input/output stream");
            e.printStackTrace();
            return;
        }

        new Thread(this).start();
    }

    public void stop() {
        running = false;
    }

    public Optional<Message> nextMessage() {
        Message m = messages.poll();
        if (m != null) return Optional.of(m);
        return Optional.empty();
    }

    @Override
    public void run() {
        while (running) {
            try {
                Message m = (Message) inputStream.readObject();
                messages.add(m);
            } catch (Exception e) {
                e.printStackTrace();
                running = false;
            }
        }
    }
}
