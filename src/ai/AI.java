package ai;

import object.Ball;
import object.platform.Platform;
import object.platform.TrapPlatform;
import shared.Constants;
import shared.GameState;

import java.util.Random;

public class AI {

    /** We don't want to make more than one of these! */
    private static Random random = new Random();

    /** Enum used to represent the domain of all possible moves the AI could make. */
    public enum Move {
        LEFT, RIGHT, NO_MOVE
    }

    /**
     * Returns the distance from a Platform, p, to a Ball, b.
     * @param p The Platform to find the distance from
     * @param b The Ball to find the distance to
     * @return The distance between the Ball, b, and the Platform, p
     */
    public static double dist(Platform p, Ball b) {
        return Math.min(leftDist(p, b), rightDist(p, b));
    }

    /**
     * Returns the distance to the left side of a Platform, p,
     * from a Ball, b. Takes in to account the position the Ball
     * will be in when it makes the move, rather than where it
     * currently is.
     * @param p The Platform to find the distance to the left edge of
     * @param b The Ball to find the distance from
     * @return The distance to the left edge of the Platform, p from the Ball, b
     */
    public static double leftDist(Platform p, Ball b) {
        return Math.hypot( b.getX() - p.x,
                           b.getY() - p.y );
    }

    /**
     * Returns the distance to the right side of a Platform, p,
     * from a Ball, b.
     * @param p The Platform to find the distance to the right edge of
     * @param b The Ball to find the distance from
     * @return The distance to the right edge of the Platform, p from the Ball, b
     */
    public static double rightDist(Platform p, Ball b) {
        return Math.hypot( b.getX() - (p.x + Constants.PLATFORM_WIDTH),
                           b.getY() - p.y );
    }

    /**
     * Returns true if the given Platform is within reach of the
     * Platform, p, and false otherwise.
     * @param p The Platform that the Ball is trying to reach
     * @param b The Ball trying to reach the Platform, p
     * @return Whether the given Platform, p, is within reach
     */
    public static boolean reachable(Platform p, Ball b) {
        /* We can't reach a Platform if it's going off of the screen! */
        if (p.getY() + p.getDy() > Constants.WINDOW_HEIGHT) return false;

        if(p.y < 0) return false; /* The platform is not on the screen */

        if(p instanceof TrapPlatform) return false; /* Don't jump on traps */

        // if(dist(p, b) > Constants.AI_MAX_DIST) return false;

        return true;
    }

    /**
     * Return the optimal Platform to move towards based on the GameState
     * @param game The GameState from which to find the optimal Platform
     * @return The optimal Platform
     */
    public static Platform choosePlatform(GameState game) {
        Platform[] platforms = game.getBasicPlatforms();
        Ball ball = game.getBall();

        /* Work out which platform is closest to us! */
        Platform closestPlatform = null;
        double minDist = Double.POSITIVE_INFINITY;

        for(Platform platform : platforms) {
            double d = dist(platform, ball);
            if(!reachable(platform, ball) || d > minDist) continue;
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
        Platform optimalPlatform = choosePlatform(game);
        if(optimalPlatform == null) return Move.NO_MOVE;

        /* Work out if the optimal platform is to the left, right or where we are (horizontally) */
        Ball ball = game.getBall();
        double ballx = ball.getX();
        double platformx = optimalPlatform.getX() + Constants.PLATFORM_WIDTH/2; /* Aim for the center of the Platform */

        double diff = ballx - platformx;
        if(diff == 0) return Move.NO_MOVE; /* Don't move if we're above the platform */
        return (ballx - platformx > 0) ? Move.LEFT : Move.RIGHT; /* Move in the direction of the platform */
    }

}