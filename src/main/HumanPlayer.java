package main;

import networking.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class HumanPlayer implements Player {
    private final ServerSideClientHandler serverSideClientHandler;

    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final Queue<String> inputQueue;

    public HumanPlayer(ServerSideClientHandler serverSideClientHandler) {
        this.serverSideClientHandler = serverSideClientHandler;
        this.outputStream = serverSideClientHandler.outputStream;
        this.inputStream = serverSideClientHandler.inputStream;

        this.inputQueue = new LinkedBlockingQueue<>();

        new Thread(new ServerInputBroker(inputQueue, inputStream)).start();
    }

    @Override
    public Optional<String> getMove() throws InterruptedException {
        String input = this.inputQueue.poll();
        if (input == null) return Optional.empty();
        return Optional.of(input);
    }

    @Override
    public void updateGameState(GameState gameState, boolean isCurrentPlayer) throws InterruptedException {
        Message msg = new Message(gameState, isCurrentPlayer);
        try {
            outputStream.writeObject(msg);
            outputStream.flush();
            outputStream.reset();
        } catch (IOException e) {
            System.err.println("[ERROR] HumanPlayer.updateGameState : IOException " + e);
            System.err.println("[ERROR] HumanPlayer.updateGameState : InterruptedException thrown...");
            throw new InterruptedException();
        }
    }

    @Override
    public void updateSeed(int seed) throws InterruptedException {
        try {
            outputStream.writeObject(new Message(seed));
            outputStream.flush();
            outputStream.reset();
        } catch (IOException e) {
            System.err.println("[ERROR] HumanPlayer.updateSeed : IOException " + e);
            System.err.println("[ERROR] HumanPlayer.updateSeed : InterruptedException thrown...");
            throw new InterruptedException();
        }
    }
}
