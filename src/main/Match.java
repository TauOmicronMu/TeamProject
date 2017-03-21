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
        System.out.println("[INFO] Match.run : Creating game state...");

        // Create new authoritative game states with the same random seed
        int seed = new Random().nextInt();
        GameState playerOneGameState = new GameState(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        playerOneGameState.setSeed(seed);
        GameState playerTwoGameState = new GameState(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        playerTwoGameState.setSeed(seed);

        System.out.println("[INFO] Match.run : Generating Platforms...");
        playerOneGameState.generatePlatforms();
        playerTwoGameState.generatePlatforms();

        System.out.println("[INFO] Match.run : Generating Items...");
        playerOneGameState.generateItems();
        playerTwoGameState.generateItems();

        System.out.println("[INFO] Match.run : Sending seed to client(s)...");
        try {
            playerOne.updateSeed(seed);
            playerTwo.updateSeed(seed);
        } catch (InterruptedException e) {
            System.err.println("[WARN] Match.run : InterruptedException... Player Disconnect?...");
            return; // exit the thread :)
        }

        System.out.println("[INFO] Match.run : Starting match...");

        boolean running = true;
        int loopNum = 0;
        long timeStep = 1;

        MainLoop: while (running) {
            long startTime = System.currentTimeMillis();

            // Have we received input from either client?
            // If so, relay immediately regardless of counter
            Optional<String> playerOneMove = Optional.empty();
            Optional<String> playerTwoMove = Optional.empty();

            InputLoop: do {
                try {
                    playerOneMove = playerOne.getMove();
                    playerTwoMove = playerTwo.getMove();
                } catch (InterruptedException e) {
                    System.err.println("[WARN] Match.run : Player disconnect while retrieving move!");
                    running = false;
                    break MainLoop;
                }

                if (playerOneMove.isPresent()) {
                    // Handle player one input
                    String move = playerOneMove.get();
                    // System.out.println("[INFO] Match.run : got a move from Player One: " + move);

                    // Update game state locally (mutate)
                    // TODO: Handle powerups
                    playerOneGameState.handleInput(move);

                    // Relay new game state to clients
/*                    try {
                        playerOne.updateGameState(playerOneGameState, true);
                        playerTwo.updateGameState(playerTwoGameState, false);
                    } catch (InterruptedException e) {
                        System.err.println("[WARN] Match.run : Player disconnect while updating game state after player one input!");
                        running = false;
                        break MainLoop;
                    }*/
                }

                if (playerTwoMove.isPresent()) {
                    // Handle player two input
                    String move = playerTwoMove.get();
                    // System.out.println("[INFO] Match.run : got a move from Player Two: " + move);

                    playerTwoGameState.handleInput(move);

/*                    // Relay to both clients
                    try {
                        playerOne.updateGameState(playerOneGameState, false);
                        playerTwo.updateGameState(playerTwoGameState, true);
                    } catch (InterruptedException e) {
                        System.err.println("[WARN] Match.run : Player disconnect while updating game state after player two input!");
                        running = false;
                        break MainLoop;
                    }*/
                }
            } while (playerOneMove.isPresent() || playerTwoMove.isPresent());

            if (loopNum % 1 == 0) {
                try {
                    playerOne.updateGameState(playerOneGameState, true);
                    playerOne.updateGameState(playerTwoGameState, false);
                    playerTwo.updateGameState(playerTwoGameState, true);
                    playerTwo.updateGameState(playerOneGameState, false);
                } catch (InterruptedException e) {
                    System.err.println("[WARN] Match.run : Player disconnect while scheduled-updating game state.");
                    running = false;
                    break;
                }
            }

            // Physics tick
            playerOneGameState.updatePhysics(timeStep);
            playerTwoGameState.updatePhysics(timeStep);

            loopNum++;

            long endTime = System.currentTimeMillis();
            timeStep = endTime - startTime;

/*            if (timeStep > Constants.MAX_TIME_PER_FRAME) {
                System.err.println("[WARN] Match.run : exceeded max time per frame.");
                continue;
            }

           try {
                Thread.sleep(Constants.MAX_TIME_PER_FRAME - timeStep);
            } catch (InterruptedException e) {
                System.err.println("[WARN] Match.run : Interrupted while sleeping.");
                break;

                timeStep = System.currentTimeMillis() - startTime;
            }*/

            try {
                Thread.sleep(Constants.SRVR_MS_PER_UPDT);
                timeStep = System.currentTimeMillis() - startTime;
            } catch (InterruptedException ignored) {}

        }
        System.out.println("[INFO] Match.run: Match concluded.");

        // Todo: send game over to fix client disconnects.
    }
}