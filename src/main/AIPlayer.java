package main;

import ai.AI;

import java.io.IOException;
import java.util.Optional;

public class AIPlayer implements Player {

    private AI ai;
    private GameState gs;

    AIPlayer() {
        super();
        this.ai = new AI();
    }

    @Override
    public Optional<String> getMove() {
        if (gs == null) return Optional.empty();
        AI.Move move = ai.getMove(gs);
        switch(move) {
            case LEFT:
                return Optional.of("a");
            case RIGHT:
                return Optional.of("d");
            default:
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
