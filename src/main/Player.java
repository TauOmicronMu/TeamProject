package main;

import java.util.Optional;

public interface Player {
    Optional<String> getMove() throws InterruptedException;

    void updateGameState(GameState gameState, boolean isCurrentPlayer) throws InterruptedException;

    void updateSeed(int seed) throws InterruptedException;
}
