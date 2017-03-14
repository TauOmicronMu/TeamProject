package main;

import networking.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;

public class HumanPlayer implements Player {
    private final ServerSideClientHandler serverSideClientHandler;

    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;

    public HumanPlayer(ServerSideClientHandler serverSideClientHandler) {
        this.serverSideClientHandler = serverSideClientHandler;

        this.outputStream = serverSideClientHandler.outputStream;
        this.inputStream = serverSideClientHandler.inputStream;
    }

    @Override
    public Optional<String> getMove() throws InterruptedException {
        try {
            if(inputStream.available()>0) return Optional.of((String) ((Message) inputStream.readObject()).getObject());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[ERROR] HumanPlayer.getMove : " + e);
            System.err.println("[ERROR] HumanPlayer.getMove : InterruptedException thrown...");
            throw new InterruptedException();
        }
        return Optional.empty();
    }

    @Override
    public void updateGameState(GameState gameState, boolean isCurrentPlayer) throws InterruptedException {
        Message msg = new Message(gameState, isCurrentPlayer);
        try {
            outputStream.writeObject(msg);
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
        } catch (IOException e) {
            System.err.println("[ERROR] HumanPlayer.updateSeed : IOException " + e);
            System.err.println("[ERROR] HumanPlayer.updateSeed : InterruptedException thrown...");
            throw new InterruptedException();
        }
    }
}
