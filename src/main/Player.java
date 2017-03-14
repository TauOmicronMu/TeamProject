package main;


import networking.Message;

abstract class Player {
    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    GameState gameState;
    abstract Message waitForMessage();

    public abstract void handleMessages();

    public abstract void updateLogic();

    public abstract void updatePhysics();

    public abstract void sendGameState();
}
