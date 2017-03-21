package main;

import java.util.Optional;

public class AIPlayer implements Player {
    @Override
    public Optional<String> getMove() {
        return Optional.empty();
    }

    @Override
    public void updateGameState(GameState gameState, boolean isCurrentPlayer) throws InterruptedException {
    }

    @Override
    public void updateSeed(int seed) throws InterruptedException {
    }
}
