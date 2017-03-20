package main;


public class NormalPlatform extends Platform {

    NormalPlatform(double x, double y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    void update(GameState game, double timeStep) {

        if (timeStep < Constants.MIN_TIME_PER_FRAME) return;
        double deltaTime = timeStep * Constants.TIME_STEP_COEFFICIENT;
        Ball ball = game.getBall();

        // If we've got the flying power-up, don't bother with collision.
        if (ball.getCountFlyPower() > 0) {
            y += Constants.FLY_POWERUP_SPEED / deltaTime;
            game.score += Constants.FLY_POWERUP_SPEED / deltaTime;
            return;
        }

        // Otherwise, check for collision with the ball.
        checkForCollision(ball, game, deltaTime);

        // If the ball's height is locked, we need to compensate by moving
        // the platform down at the speed the ball's meant to be rising.
        if (ball.heightIsLocked()) {
            y -= ball.getDy() / deltaTime;
        }

        // Update platform Y position.
        y += dy / deltaTime;
        game.score += dy / deltaTime;

        //System.out.println("Dy is" + dy/deltaTime);
    }

    @Override
    void paint(Window game, boolean opponent) {
        super.paint(game, opponent);
    }
}
