package main;

import java.util.Optional;
import java.util.Random;

public class Match implements Runnable {

    private Player playerOne;
    private Player playerTwo;

    public Match(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    @Override
    public void run() {
        System.out.println("[INFO] Match.run: Creating game state...");

        // Create new authoratative game states with the same random seed
        int seed = new Random().nextInt();
        GameState playerOneGameState = new GameState(seed, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        GameState playerTwoGameState = new GameState(seed, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);]

        System.out.println("[INFO] Match.run: Starting match...");

        boolean running = true;
        int loopNum = 0;

        while (running) {
            // Have we received input from either client?
            // If so, relay immediately regardless of counter
            Optional<String> playerOneMove = playerOne.getMove();
            Optional<String> playerTwoMove = playerTwo.getMove();

            if(playerOneMove.isPresent()) {
                // Handle player one input
                String move = playerOneMove.get();

                // Update game state locally (mutate)
                // TODO: Handle powerups
                playerOneGameState.handleInput(move);

                // Relay new game state to clients
                playerOne.updateGameState(playerOneGameState);
                playerTwo.updateGameState(playerTwoGameState);
            }
            if(playerTwoMove.isPresent()) {
                // Handle player two input
                String move = playerTwoMove.get();

                // Update game state
                // TODO: Handle powerups
                playerTwoGameState.handleInput(move);

                // Relay to both clients
                playerOne.updateGameState(playerOneGameState);
                playerTwo.updateGameState(playerTwoGameState);
            }

            if (loopNum % 10 == 0) {
                // Send gamestate to player 1
                playerOne.updateGameState(playerOneGameState);
                // Send gamestate to player 2
                playerTwo.updateGameState(playerTwoGameState);
            }

            // Logic tick
            playerOneGameState.updateLogic();
            playerTwoGameState.updateLogic();
            // Physics tick
            playerOneGameState.updatePhysics();
            playerTwoGameState.updatePhysics();

            loopNum++;

            try {
                Thread.sleep(Constants.FPS_SLEEP);
            } catch(InterruptedException e) {
                System.err.println("[WARN] Match.run:  " + e);
                running = false; // Something has gone wrong with the match, or it has ended
            }
        }
        System.out.println("[INFO] Match.run: Match concluded.");
    }
}