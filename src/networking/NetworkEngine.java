package networking;


import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;


/**
 * A NetworkEngine exists to abstract away the sockets, streams and queues
 * into a nice interface for sending and receiving messages.
 */
abstract class NetworkEngine implements Runnable {

    // A NetworkEngine handles the I/O for a socket.
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private boolean running;
    private final BlockingDeque<Message> messages = new LinkedBlockingDeque<>();
    protected Socket socket;

    /**
     * Stop our message-reception loop from continuing.
     */
    public void stop() throws InterruptedException {
        running = false;
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Closing socket threw an exception. Still closing it.");
        }
        throw new InterruptedException();
    }

    /**
     * Essentially this method is a "poll" for messages. It returns an
     * Optional networking.Message: either the next message in our received queue,
     * or Empty if we haven't received any messages lately.
     * @return Either the message we've received, or Optional.empty().
     */
    public Optional<Message> nextMessage() {
        Message m = messages.poll();
        if (m == null) return Optional.empty();
        return Optional.of(m);
    }

    /**
     * This method blocks until we receive a message, then returns it.
     * @return The Message we've received.
     */
    public Message waitForMessage() {
        try {
            return messages.take();
        } catch (InterruptedException e) {
            System.err.println("[ERROR] NetworkEngine.waitForMessage : " + e);
        }
        return null;
    }

    /**
     * Send a message to our output stream, and therefore our connected device.
     * Note that this doesn't have any guarantees about concurrency.
     * @param m The message to send.
     */
    public boolean sendMessage(Message m) {
        try {
            outputStream.writeObject(m);
            outputStream.reset();
        } catch (IOException e) {
            System.out.println("Failed to write message.");
            return false;
        }
        return true;
    }

    /**
     * Start a main loop which reads messages from our input stream into a
     * queue of messages we've received.
     */
    @Override
    public void run() {
        try {
            running = true;
            while (running) {
                try {
                    // The main component of our loop: read objects from the input stream,
                    // and add them to our buffer of received messages.
                    Message m = (Message) inputStream.readObject();
                    messages.add(m);
                } catch (EOFException e) {
                    System.out.println("[WARN] NetworkEngine.run : EOFException. Stopping...");
                    stop();
                } catch (ClassNotFoundException e) {
                    System.err.println("[WARN] NetworkEngine.run : Invalid object. Continuing...");
                } catch (IOException e) {
                    System.err.println("[WARN] NetworkEngine.run : IOException. Stopping...");
                    stop();
                }
            }
        } catch (InterruptedException ignored) {
            System.err.println("[WARN] NetworkEngine.run : Stopped Networking thread.");
        }
    }

    /**
     * Given a socket representing our connection to another device, create
     * the necessary input and output streams to be able to send data to that
     * device.
     * @param socket The socket through which we want to send/receive data.
     */
    void engineInitialize(Socket socket) {
        try {
            this.socket = socket;
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.err.println("Couldn't get one of input or output stream for socket.");
            e.printStackTrace();
            return;
        }
        new Thread(this).start();
    }

    /**
     * Is the engine currently accepting messages?
     * @return True if the engine's main loop is running, False otherwise.
     */
    public boolean isRunning() {
        return running;
    }
}
