package main;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

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

        while (running) {
            // Have we received input from either client?
            // If so, relay immediately regardless of counter
            Optional<String> playerOneMove = null;
            Optional<String> playerTwoMove = null;
            try {
                playerOneMove = playerOne.getMove();
                playerTwoMove = playerTwo.getMove();
            } catch (InterruptedException e) {
                System.err.println("[WARN] Match.run : Player disconnect while retrieving move!");
                running = false;
                break;
            }

            if(playerOneMove.isPresent()) {
                // Handle player one input
                String move = playerOneMove.get();

                // Update game state locally (mutate)
                // TODO: Handle powerups
                playerOneGameState.handleInput(move);

                // Relay new game state to clients
                try {
                    playerOne.updateGameState(playerOneGameState, true);
                    playerTwo.updateGameState(playerTwoGameState, false);
                } catch (InterruptedException e) {
                    System.err.println("[WARN] Match.run : Player disconnect while updating game state after player one input!");
                    running = false;
                    break;
                }

            }
            if(playerTwoMove.isPresent()) {
                // Handle player two input
                String move = playerTwoMove.get();

                // Update game state
                // TODO: Handle powerups
                playerTwoGameState.handleInput(move);

                // Relay to both clients
                try {
                    playerOne.updateGameState(playerOneGameState, false);
                    playerTwo.updateGameState(playerTwoGameState, true);
                } catch (InterruptedException e) {
                    System.err.println("[WARN] Match.run : Player disconnect while updating game state after player two input!");
                    running = false;
                    break;
                }

            }

            if (loopNum % Constants.MAX_FPS == 0) {
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