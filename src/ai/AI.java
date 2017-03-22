package ai;

import main.*;

import java.util.Arrays;

public class AI {

    public enum Move {
        LEFT, RIGHT, NO_MOVE
    }

    /**
     * Returns the distance from a Platform, p, to a Ball, b.
     * @param p The Platform to find the distance from
     * @param b The Ball to find the distance to
     * @return The distance between the ball and the platform.
     */
    private static double dist(Platform p, Ball b) {
        return Math.min(
                Math.hypot( // Left edge of platform
                        b.getX() - p.getX(),
                        b.getY() - p.getY()
                ),
                Math.hypot( // Right edge of platform
                        b.getX() - (p.getX() + Constants.PLATFORM_WIDTH),
                        b.getY() - p.getY()
                )
        );
    }

    /**
     * Return the optimal Platform to move towards based on the GameState
     * @param game The GameState from which to find the optimal Platform
     * @return The optimal Platform
     */
    private static Platform choosePlatform(GameState game) {
        Platform[] platforms = game.getBasicPlatforms();
        Ball ball = game.getBall();

        // Work out which platform is closest to us!
        Platform closestPlatform = null;
        double minDist = Double.POSITIVE_INFINITY;
        for(Platform platform : platforms) {
            double d = dist(platform, ball);
            if(d > minDist || platform instanceof TrapPlatform) continue;
            closestPlatform = platform;
            minDist = d;
        }

        return closestPlatform;
    }

    /**
     * Given a GameState, return one of:
     *     >> Move.LEFT
     *     >> Move.RIGHT
     *     >> Move.NO_MOVE
     * based on the optimal decision for the AI with the current GameState
     * @param game The GameState from which to find the optimal Move.
     * @return The optimal Move for the AI to make.
     */
    public static Move getMove(GameState game) {
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