package main;


public abstract class CollidablePlatform {

    protected void checkForCollision(Ball ball, double deltaTime, double x, double y, double width) {
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
        double newBallBottom = ballBottom + ball.getDy() / deltaTime;
        if (newBallBottom <= rectTop) return;

        // Check the ball is aligned with the top of the platform.
        if (ballX+radius < rectLeft) return;
        if (ballX-radius > rectRight) return;

        // If the ball has collided with the top of the platform ~Tom
        // AudioEngine.getInstance().playTrack(AudioEngine.BOING); // Play the boing sound
        ball.setY(rectTop - radius);
        ball.setDy(-ball.getMaxSpeed());
    }
}
