package main;

import ai.AI;
import ai.AIThread;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Optional;

public class AIPlayer implements Player {

    private AI ai;
    private GameState gs;
    private Hashtable<String, Double> db;

    AIPlayer() {
        super();
        try {
            this.ai = new AI();
            db = ai.getDB();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[ERROR] AIPlayer constructor : Error while creating AI player.");
        }
    }

    @Override
    public Optional<String> getMove() {
        if (gs == null) {
            System.out.println("[INFO] AIPlayer.getMove : Game state is null.");
            return Optional.empty();
        }
        double xVel = new AIThread(db, gs).run();
        switch((int) xVel) {
            case -1:
                return Optional.of("a");
            case 0:
                return Optional.empty();
            case 1:
                return Optional.of("d");
            default:
                System.out.println("[ERROR] AIPlayer.getMove : Returned unexpected value: " + xVel);
                return Optional.empty();
        }
    }


    @Override
    public void updateGameState(GameState gameState, boolean isCurrentPlayer) throws InterruptedException {
        if (isCurrentPlayer) gs = gameState;
    }

    @Override
    public void updateSeed(int seed) throws InterruptedException {}
}
