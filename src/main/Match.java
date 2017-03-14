package main;

class Match implements Runnable {

    private final Player playerA;
    private final Player playerB;

    Match(Player playerA, Player playerB) {
        this.playerA = playerA;
        this.playerB = playerB;
    }

    void begin() {
        new Thread(this).start();
    }

    void handleMessages() {
        playerA.handleMessages();
        playerB.handleMessages();
    }

    void updateLogic() {
        playerA.updateLogic();
        playerB.updateLogic();
    }

    void updatePhysics() {
        playerA.updatePhysics();
        playerB.updatePhysics();
    }

    void sendGameStates() {
        playerA.sendGameState();
        playerB.sendGameState();
    }

    @Override
    public void run() {
        // Create a game state and copy it for each player.
        playerA.setGameState(
                new GameState(Constants.windowWidth, Constants.windowHeight)
        );
        playerB.setGameState(
                playerA.getGameState().copy()
        );

        // Play the main game loop.
        int count = 0;
        while (true) {
            count = (count + 1) % 50;

            handleMessages();
            updateLogic();
            if (count == 0) sendGameStates();

            try {
                Thread.sleep(1000/Constants.PHYSICS_FPS);
            } catch (InterruptedException e) {
                System.out.println("Match thread was interrupted. Breaking out of main loop...");
                break;
            }
        }
    }
}
