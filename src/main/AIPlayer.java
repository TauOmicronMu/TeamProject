package main;

import ai.AI;

import java.util.Optional;

public class AIPlayer implements Player {
    GameState game;

    @Override
    public Optional<String> getMove() {
        if(game == null) return Optional.empty();
        AI.Move move = AI.getMove(game);
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
        if (isCurrentPlayer) game = gameState;
    }

    @Override
    public void updateSeed(int seed) throws InterruptedException {}
}
