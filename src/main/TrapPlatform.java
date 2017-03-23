package main;

import java.io.Serializable;


public class TrapPlatform extends Platform implements Serializable {

    /*
     *Constructor for platform object
     *@param x the current x position of the platform(the top left corner of the platform)
     *@param y the current y position of the platform(the top left corner of the platform)
     *@param width the width of the platform
     *@param height the height of the platform
     */
    TrapPlatform(double x, double y, int width, int height) {
        super(x, y, width, height);
    }

    /*
     *Updates the position of the platform
     *@param game the game class object
     *@param ball the ball class object
     */
    @Override
    void update(GameState game, double timeStep) {
        if (timeStep < Constants.MIN_TIME_PER_FRAME) return;
        double deltaTime = timeStep * Constants.TIME_STEP_COEFFICIENT;
        Ball ball = game.getBall();

//        // If platform is offscreen, move it back on!
//        if (y > game.getWindowHeight()) {
//            //System.out.println("[INFO] Platform.update : y is > window height " + game.getWindowHeight() + " at " + y);
//            y -= game.getWindowHeight()*1.3;
//            x = game.random.nextInt(game.getWindowWidth() - width);
//            return;
//        }

        // If we've got the flying power-up, don't bother with collision.
        if (ball.getCountFlyPower() > 0) {
            y += Constants.FLY_POWERUP_SPEED * deltaTime;
            game.score += Constants.FLY_POWERUP_SPEED * deltaTime;
            return;
        }

        // Otherwise, check for collision with the ball.
        checkForCollision(ball, game, deltaTime);

        // If the ball's height is locked, we need to compensate by moving
        // the platform down at the speed the ball's meant to be rising.
        if (ball.heightIsLocked()) {
            y -= ball.getDy() * deltaTime;
        }

        // Update platform Y position.
        y += dy * deltaTime;
        game.score += dy * deltaTime;

        //System.out.println("Dy is" + dy/deltaTime);
    }

    @Override
    public void checkForCollision(Ball ball, GameState game, double deltaTime) {
        if(noDraw)return;
            double ballX = ball.getX();
            double ballY = ball.getY();
            int radius = ball.getRadius();

            double ballBottom = ballY + radius;
            double rectTop = y;
            double rectLeft = x;

            // Todo: How is only half the platform colliding?
            double rectRight = x + width*2;

            // Check if the ball is above the platform *and* will be below
            // it after exactly one tick at the current framerate.
            if (ballBottom >= rectTop) return;
            double newBallBottom = ballBottom + ball.getDy() * deltaTime;
            if (newBallBottom <= rectTop) return;

            // Check the ball is aligned with the top of the platform.
            if (ballX+radius < rectLeft) return;
            if (ballX-radius > rectRight) return;

            // If the ball has collided with the top of the platform ~Tom
            AudioEngine.getInstance().playTrack(AudioEngine.BLOP); // Play the boing sound
            //ball.setY(rectTop - radius);
            //ball.setDy(-ball.getMaxSpeed());
            noDraw = true;

    }

    /*
     * Draws the platform
     */
    void paint(Window game, boolean opponent) {
        if(noDraw)return;
        double scaledX = game.glScaleX(x, opponent, Screen.GAME);
        double scaledY = game.glScaleY(y);
        double widthGl = game.glScaleDistance(width);
        double heightGl = game.glScaleDistance(height);

        double[] verticesb = {scaledX, scaledY, 0.3f, scaledX, (scaledY - heightGl), 0.3f, (scaledX + widthGl), (scaledY - heightGl), 0.3f, (scaledX + widthGl), scaledY, 0.3f};
        Rectangle.drawrectangle(verticesb, Menu.getRectangleModel(), false);
    }

    public void setDx(int dx){
        this.dy = dx;
    }
}