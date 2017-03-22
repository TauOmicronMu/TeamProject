package main;

import java.io.Serializable;
import java.util.Random;


public class MovingHorizontallyPlatform extends Platform implements Serializable {

    private double x1, x2;
    private int score = 0;
    private double dx;

    /*
     *Constructor for platform object
     *@param x the current x position of the platform(the top left corner of the platform)
     *@param y the current y position of the platform(the top left corner of the platform)
     *@param width the width of the platform
     *@param height the height of the platform
     */
    MovingHorizontallyPlatform(double x, double y, int width, int height, int x1, int x2 ) {
        super(x, y, width, height);
        this.x1 = x1;
        this.x2 = x2;
        dx = 15;
    }

    /*
     *Updates the position of the platform
     *@param game the game class object
     *@param ball the ball class object
     */
    @Override
    void update(GameState game, double timeStep) {
        if (timeStep <= Constants.MIN_TIME_PER_FRAME) return;
        Ball ball = game.getBall();
        if (ball.gameOver()) return;

        double deltaTime = timeStep * Constants.TIME_STEP_COEFFICIENT;

        // If we've got the flying power-up, don't bother with collision.
        if (ball.getCountFlyPower() > 0f) {
            y += Constants.FLY_POWERUP_SPEED * deltaTime;
            game.score += Constants.FLY_POWERUP_SPEED * deltaTime;
            return;
        }

        // Otherwise, check for collision.
        checkForCollision(ball, game, deltaTime);

        if (ball.heightIsLocked()) {
            y -= ball.getDy() * deltaTime;
        }

        // Update platform Y position.
        y += dy * deltaTime;
        game.score += dy * deltaTime;

        // Update platform X position.
        if (x <= x1 || x >= x2) dx = -dx;
        x += dx * deltaTime;
    }

    public void setDx(int dx){
        this.dy = dx;
    }
}