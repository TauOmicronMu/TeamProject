package main;

import java.util.Optional;
import java.util.Random;

import static java.lang.System.currentTimeMillis;

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

        int loopNum = 0;
        long timeStep = 1;
        long lastTimeUpdated = 0;
        long meanFPSaccumulator = 0;

        MainLoop: while (true) {
            long startTime = currentTimeMillis();

            // Have we received input from either client?
            // If so, relay immediately regardless of counter

            boolean receivedInputFromPlayerOne = false;
            boolean receivedInputFromPlayerTwo = false;

            Optional<String> playerOneMove = Optional.empty();
            Optional<String> playerTwoMove = Optional.empty();

            do {
                // Retrieve the messages we've been sent from the network.
                try {
                    playerOneMove = playerOne.getMove();
                    playerTwoMove = playerTwo.getMove();
                } catch (InterruptedException e) {
                    System.err.println("[WARN] Match.run : Player disconnect while retrieving move!");
                    break MainLoop;
                }

                // Interleave P1 and P2's messages, and handle all of them.
                if (playerOneMove.isPresent()) {
                    receivedInputFromPlayerOne = true;
                    playerOneGameState.handleInput(playerOneMove.get());
                }
                if (playerTwoMove.isPresent()) {
                    receivedInputFromPlayerTwo = true;
                    playerTwoGameState.handleInput(playerTwoMove.get());
                }
            } while (playerOneMove.isPresent() || playerTwoMove.isPresent());


            // If we've received any input, or we get past the max number of ticks without updating, update now.
            boolean receivedInput = receivedInputFromPlayerOne | receivedInputFromPlayerTwo;
            boolean timeToUpdate = startTime - lastTimeUpdated > Constants.MS_PER_UPDATE;
            if (false && (timeToUpdate || receivedInput)) {
                System.out.println("[INFO] Match.run : Updating all players with current game states.");
                try {
                    playerOne.updateGameState(playerOneGameState, true);
                    playerOne.updateGameState(playerTwoGameState, false);
                    playerTwo.updateGameState(playerTwoGameState, true);
                    playerTwo.updateGameState(playerOneGameState, false);
                    lastTimeUpdated = startTime;
                } catch (InterruptedException e) {
                    System.err.println("[WARN] Match.run : Player disconnect while updating game state.");
                    break;
                }
            }

            // Update the physics of both game states.
            playerOneGameState.updatePhysics(timeStep);
            playerTwoGameState.updatePhysics(timeStep);

            loopNum++;

            // Print out a mean FPS every so many frames.
            meanFPSaccumulator += timeStep;
            if ((loopNum) % 10000 == 0) {
                float meanFPS = meanFPSaccumulator / 100;
                System.out.println("[INFO] Match.run : Mean frame time = " + meanFPS + " * 10^-3.");
            }

            long endTime = currentTimeMillis();
            timeStep = endTime - startTime;

            try {
                Thread.sleep(Math.max(0, 1000/2/Constants.TARGET_FPS -timeStep));
            } catch (InterruptedException e) {
                System.out.println("[WARN] Main.play : Main game loop was interrupted.");
                break;
            }

            timeStep = currentTimeMillis() - startTime;
        }
        System.out.println("[INFO] Match.run: Match concluded.");
        playerOne.endGame();
        playerTwo.endGame();
    }
}