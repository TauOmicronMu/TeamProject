package networking;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * A NetworkEngine exists to abstract away the sockets, streams and queues
 * into a nice interface for sending and receiving messages.
 */
abstract class NetworkEngine implements Runnable {

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private boolean running;
    private final ConcurrentLinkedQueue<Message> messages = new ConcurrentLinkedQueue<>();

    /**
     * Stop our message-reception loop from continuing at the next iteration.
     */
    private void stop() {
        running = false;
    }

    /**
     * Essentially this method is a "poll" for messages. It returns an
     * Optional Message: either the next message in our received queue,
     * or Empty.
     */
    Optional<Message> nextMessage() {
        Message m = messages.poll();
        if (m != null) return Optional.of(m);
        return Optional.empty();
    }

    /**
     * Send a message to our output stream, and therefore our connected device.
     *
     * @param m The message to send.
     */
    void sendMessage(Message m) {
        try {
            outputStream.writeObject(m);
        } catch (IOException e) {
            System.out.println("Failed to write message. Stopping...");
            System.exit(0);
        }
    }

    /**
     * Start a main loop which reads messages from our input stream into a
     * buffer of received messages.
     */
    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                Message m = (Message) inputStream.readObject();
                messages.add(m);
            } catch (Exception e) {
                e.printStackTrace();
                stop();
            }
        }
    }

    /**
     * Given a socket representing our connection to another device, create
     * the necessary input and output streams to be able to send data to that
     * device.
     *
     * @param socket The socket across which we want to send data.
     */
    void initialize(Socket socket) {
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.err.println("Couldn't get one of input or output stream for socket.");
            e.printStackTrace();
            return;
        }
        new Thread(this).start();
    }
}
