package main;

import networking.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Queue;


public class ServerSideClientHandler implements Runnable {
    protected ObjectInputStream inputStream = null;
    protected ObjectOutputStream outputStream = null;
    private Socket clientSocket = null;
    private Queue<Player> playerQueue = null;

    public ServerSideClientHandler(Socket clientSocket, Queue playerQueue) {
        if (clientSocket == null) {
            System.err.println("[ERROR] ServerSideClientHandler constructor: Client socket is null.");
            System.exit(1);
        }
        this.clientSocket = clientSocket;
        try {
            this.inputStream = new ObjectInputStream(clientSocket.getInputStream());
            this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.err.println("[ERROR] ServerSideClientHandler constructor: Couldn't get input or output stream from socket.");
        }
        this.playerQueue = playerQueue;
    }

    @Override
    public void run() {
        try {

            OpponentType opponentType = (OpponentType) waitForMessage().getObject();
            System.out.println("[INFO] ServerSideClientHandler.run: Client requested opponent type: " + opponentType);

            switch (opponentType) {
                case AI:
                    createAIMatch();
                    break;
                case HUMAN:
                    createHumanMatch();
                    break;
                default:
                    System.err.println("[WARN] ServerSideClientHandler.run: Client requested unknown opponent type.");
            }

        } catch (InterruptedException e) {
            System.err.println("[WARN] ServerSideClientHandler.run: Thread interrupted.");
        }
    }

    /**
     * If there's someone in the wait queue currently, create a match between them and us.
     * If not, just place ourselves into the wait queue.
     */
    private void createHumanMatch() {
        System.out.println("[INFO] ServerSideClientHandler.createHumanMatch: Setting up a human match.");
        Player us = new HumanPlayer(this);
        Player opp;
        if((opp = playerQueue.poll()) != null) new Thread(new Match(us, opp)).start();
        playerQueue.add(us);
    }

    void createAIMatch() {
        System.out.println("[INFO] ServerSideClientHandler.createAIMatch: Setting up an AI match.");
        Player us = new HumanPlayer(this);
        Player opponent = new AIPlayer();
        new Thread(new Match(us, opponent)).start();
    }

    private Message waitForMessage() throws InterruptedException {
        try {
            return (Message) inputStream.readObject();
        } catch (IOException e) {
            System.err.println("[ERROR] ServerSideClientHandler.waitForMessage: IOException when reading object.");
            throw new InterruptedException();
        } catch (ClassNotFoundException e) {
            System.err.println("[ERROR] ServerSideClientHandler.waitForMessage: ClassNotFoundException when decoding object.");
        }
        System.err.println("[WARN] ServerSideClientHandler.waitForMessage: returning null when Message should be returned.");
        return null;
    }
}
