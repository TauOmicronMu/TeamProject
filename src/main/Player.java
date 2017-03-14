package main;

import java.util.Optional;

public interface Player {
    Optional<String> getMove() throws InterruptedException;

    void updateGameState(GameState gameState) throws InterruptedException;
}
