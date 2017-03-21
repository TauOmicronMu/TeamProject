package main;

import networking.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Queue;


public class ServerInputBroker implements Runnable {
    private final Queue<String> inputQueue;
    private final ObjectInputStream inputStream;

    public ServerInputBroker(Queue<String> inputQueue, ObjectInputStream inputStream) {
        this.inputQueue = inputQueue;
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.inputQueue.add(((Message) this.inputStream.readObject()).getText());
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("[WARN] ServerInputBroker.run : " + e);
                break;
            }
        }
    }
}
