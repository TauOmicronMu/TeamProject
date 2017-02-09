package networking;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

abstract class NetworkEngine implements Runnable {

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private boolean running;
    private ConcurrentLinkedQueue<Message> messages = new ConcurrentLinkedQueue<>();

    private void stop() {
        running = false;
    }

    Optional<Message> nextMessage() {
        Message m = messages.poll();
        if (m != null) return Optional.of(m);
        return Optional.empty();
    }

    void sendMessage(Message m) {
        try {
            outputStream.writeObject(m);
        } catch (IOException e) {
            System.out.println("Failed to write message. Stopping...");
            //e.printStackTrace();
            stop();
            System.exit(0);
        }
    }

    @Override
    public void run() {
        running = true;
        System.out.println("Starting to read messages from input stream...");
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

    void initialize(Socket socket) {
        // Grab input and output streams to the given socket.
        System.out.println("Initializing...");
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.err.println("Couldn't get i/o stream for socket.");
            e.printStackTrace();
            return;
        }
        System.out.println("Initialized.");

        // Start handling messages on the socket.
        new Thread(this).start();
    }
}
