package main;

import java.io.IOException;
import java.util.Optional;

public class AIPlayer implements Player {

    private AI ai;
    private GameState gs;

    AIPlayer() {
        super();
        try {
            this.ai = new AI();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[ERROR] AIPlayer constructor : Error while creating AI player.");
        }
    }

    @Override
    public Optional<String> getMove() {
        if (gs == null) return Optional.empty();
        double xVel = ai.apply(gs.getBall(), gs.getBasicPlatforms());
        System.out.println("[INFO] AIPlayer.getMove : Requested new X velocity is " + xVel);
        if (xVel < -gs.getBall().getMaxSpeed()/2)
            return Optional.of("a");
        if (xVel > gs.getBall().getMaxSpeed()/2)
            return Optional.of("d");
        return Optional.empty();
    }

    @Override
    public void updateGameState(GameState gameState, boolean isCurrentPlayer) throws InterruptedException {
        if (isCurrentPlayer) gs = gameState;
    }

    @Override
    public void updateSeed(int seed) throws InterruptedException {}
}
