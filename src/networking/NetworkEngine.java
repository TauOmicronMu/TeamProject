package networking;


import main.GameState;

import java.io.*;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;


/**
 * A NetworkEngine exists to abstract away the sockets, streams and queues
 * into a nice interface for sending and receiving messages.
 */
abstract class NetworkEngine implements Runnable {

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private boolean running;
    private final BlockingDeque<Message> messages = new LinkedBlockingDeque<>();
    private Socket socket;

    /**
     * Stop our message-reception loop from continuing at the next iteration.
     */
    void stop() {
        running = false;
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Closing socket threw an exception. Still closing it.");
        }
    }

    Message waitForMessage() throws InterruptedException {
        return messages.take();
    }

    /**
     * Essentially this method is a "poll" for messages. It returns an
     * Optional networking.Message: either the next message in our received queue,
     * or Empty.
     */
    Optional<Message> nextMessage() {
        Message m = messages.poll();
        if (m == null) return Optional.empty();
        return Optional.of(m);
    }

    /**
     * Send a message to our output stream, and therefore our connected device.
     *
     * @param m The message to send.
     */
    boolean sendMessage(Message m) {
        try {
// N.B.: this code tests that the message will serialize correctly. It's probably slow.
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            ObjectOutputStream oos = new ObjectOutputStream((byteArrayOutputStream));
//            oos.writeObject(m);
//            oos.close();
//            byte[] messageBytes = byteArrayOutputStream.toByteArray();
//
//            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(messageBytes);
//            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
//            Message serializedMessage = (Message) objectInputStream.readObject();
//
//            double serializedY = ((GameState) serializedMessage.getObject()).getBall().getY();
//            System.out.println("After serialization, y="+serializedY);

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
     * buffer of received messages.
     */
    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                Message m = (Message) inputStream.readObject();
                messages.add(m);
            } catch (EOFException e) {
                System.out.println("EOFException. Stopping...");
                stop();
            } catch (ClassNotFoundException e) {
                System.err.println("Invalid object. Continuing...");
            } catch (IOException e) {
                System.err.println("IOException. Stopping...");
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

    void accept() {

    }

    boolean isRunning() {
        return running;
    }
}
