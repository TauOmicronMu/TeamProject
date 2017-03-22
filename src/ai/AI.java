package ai;

import main.Ball;
import main.GameState;
import main.Platform;

public class AI {

    public AI(){}

    public enum Move {
        LEFT, RIGHT, NO_MOVE
    }

    // Uses a heuristic to choose the optimal platform for a given game state
    private Platform choosePlatform(GameState game) {
        Platform[] platforms = game.getBasicPlatforms();
        return platforms[0];
    }

    public Move getMove(GameState game) {
        // Calculate the optimal platform
        Platform optimalPlatform = choosePlatform(game);

        // Work out if the optimal platform is to the left, right or where we are (horizontally)
        Ball ball = game.getBall();
        double ballx = ball.getX();
        double platformx = optimalPlatform.getX();

        double diff = ballx - platformx;
        if(diff == 0) return Move.NO_MOVE; // Don't move if we're above the platform
        return (ballx - platformx > 0) ? Move.LEFT : Move.RIGHT; // Move in the direction of the platform
    }

}
