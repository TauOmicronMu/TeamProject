package ai;

import main.*;
import org.apache.commons.math3.analysis.function.Constant;

import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

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
    private static double dist(Platform p, Ball b) {
        return Math.min(leftDist(p, b), rightDist(p, b));
    }

    /**
     * Returns the distance to the left side of a Platform, p,
     * from a Ball, b.
     * @param p The Platform to find the distance to the left edge of
     * @param b The Ball to find the distance from
     * @return The distance to the left edge of the Platform, p from the Ball, b
     */
    private static double leftDist(Platform p, Ball b) {
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
    private static double rightDist(Platform p, Ball b) {
        return Math.hypot( b.getX() - (p.x + Constants.PLATFORM_WIDTH),
                           b.getY() - p.y );
    }

    /**
     * Returns the coordinates of the closest point on a
     * Platform, p, to a Ball, b.
     * @param p The Platform to find the closest point on
     * @param b The Ball to find the closest point to
     * @return The closest point on the Platform, p, to the Ball, b
     *         in the form <X, Y>.
     */
    private static Vector<Double> closestPoint(Platform p, Ball b) {
        double s1 = leftDist(p, b);
        double s2 = rightDist(p, b);
        return (s1 < s2) ? new Vector<Double>(p.getX(), p.getY()) :
                           new Vector<Double>(p.getX() + Constants.PLATFORM_WIDTH, p.getY());
    }

    /**
     * Calculates the time taken to reach a Platform for a Ball
     *                   t = √(u^2−2as)/a
     * We use the above (from rearranging SUVAT or using the quadratic
     * formula on: s = ut + 0.5at^2 ) to calculate this.
     *     See: https://en.wikipedia.org/wiki/Equations_of_motion
     *          for more info.
     * @param s The displacement of the Ball
     * @param u The initial (current) velocity of the Ball
     * @param a The acceleration of the Ball
     * @return The time taken for a Ball to reach a Platform
     */
    private static double timeToPlatform(double s, double u, double a) {
        return Math.sqrt((Math.pow(u, 2) - 2 * a * s)/a);
    }

    /**
     * Returns true if the given Platform is within reach of the
     * Platform, p, and false otherwise.
     * @param p The Platform that the Ball is trying to reach
     * @param b The Ball trying to reach the Platform, p
     * @return Whether the given Platform, p, is within reach
     */
    private static boolean reachable(Platform p, Ball b) {
        /* We can't reach a Platform if it's going off of the screen! */
        if (p.getY() + p.getDy() > Constants.WINDOW_HEIGHT) return false;

        Vector<Double> pClosestPoint = closestPoint(p, b);

        /* pClosestPoint is in the form <X,Y> so just use first/last Element rather than get */
        double sx = Math.abs(b.getX() - pClosestPoint.firstElement());
        double sy = Math.abs(b.getY() - pClosestPoint.lastElement());

        double ux = Constants.MAX_SPEED /* Assume that we are travelling at the max speed (because we can! ^-^ ) */
        double uy = b.getDy();

        double ax = 0.0; /* No acceleration in the x direction */
        double ay = Constants.GRAVITY;

        double tx = timeToPlatform(sx, ux, ax);
        double ty = timeToPlatform(sy, uy, ay);

        /* Can we reach the platform in time given our speed? */
        /* Can we reach it in the x direction? */
        double dx = 0;
        if (p instanceof MovingHorizontallyPlatform) {
            MovingHorizontallyPlatform hp = (MovingHorizontallyPlatform) p;
            dx = hp.getDx();
        }

        double totalxs = dx * tx + sx; /* How far do we have to travel to reach the platform? */

        if (ux * tx < totalxs) return false;

        /* Can we reach it in the y direction? */
        double dy = p.dy;

        double totalys = dy * ty + sy; /* How far do we have to travel to reach the platform? */

        return (uy * ty >= totalys); /* Just return if it's reachable in y (it already is in x) */
    }

    /**
     * Return the optimal Platform to move towards based on the GameState
     * @param game The GameState from which to find the optimal Platform
     * @return The optimal Platform
     */
    private static Platform choosePlatform(GameState game) {
        Platform[] platforms = game.getBasicPlatforms();
        Ball ball = game.getBall();

        /* Work out which platform is closest to us! */
        Platform closestPlatform = null;
        double minDist = Double.POSITIVE_INFINITY;

        for(Platform platform : platforms) {
            double d = dist(platform, ball);
            if(!reachable(platform, ball) || d > minDist || platform instanceof TrapPlatform) continue;
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
        /* Calculate the optimal platform */
        Platform optimalPlatform = choosePlatform(game);

        /* Work out if the optimal platform is to the left, right or where we are (horizontally) */
        Ball ball = game.getBall();
        double ballx = ball.getX();
        double platformx = optimalPlatform.getX() + Constants.PLATFORM_WIDTH/2; /* Aim for the center of the Platform */

        double diff = ballx - platformx;
        if(diff == 0) return Move.NO_MOVE; /* Don't move if we're above the platform */
        return (ballx - platformx > 0) ? Move.LEFT : Move.RIGHT; /* Move in the direction of the platform */
    }

}