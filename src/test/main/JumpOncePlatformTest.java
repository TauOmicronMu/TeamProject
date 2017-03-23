package main;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class JumpOncePlatformTest {

    public JumpOncePlatform platform;
    public GameState gameState;
    @Before
    public void setUp() throws Exception {
        platform = new JumpOncePlatform(400,400, 100, 40);
        gameState = new GameState(800, 800);
    }

    @Test
    public void update() throws Exception {
        double dy = platform.getDy();
        double timeStep = 4;
        if (timeStep < Constants.MIN_TIME_PER_FRAME) return;
        double deltaTime = timeStep * Constants.TIME_STEP_COEFFICIENT;
        double y = platform.y;
        y += dy * deltaTime;
        platform.update(gameState, timeStep);
        assertEquals(y, platform.getY(), 0);
    }

    @Test
    public void checkForCollision() throws Exception {
        Ball ball = gameState.getBall();
        ball.setX(450);
        ball.setY(410);
        double ballX = ball.getX();
        double ballY = ball.getY();
        int radius = ball.getRadius();

        double ballBottom = ballY + radius;
        double rectTop = ball.getY();
        double rectLeft = ball.getX();

        double timeStep = 4;
        double deltaTime = timeStep * Constants.TIME_STEP_COEFFICIENT;

        double rectRight = platform.x + platform.width*2;
        if (ballBottom >= rectTop) return;
        double newBallBottom = ballBottom + ball.getDy() * deltaTime;
        if (newBallBottom <= rectTop) return;

        if (ballX+radius < rectLeft) return;
        if (ballX-radius > rectRight) return;

        double ballNewY= (rectTop - radius);
        double ballNewDy= -ball.getMaxSpeed();

        platform.checkForCollision(ball, gameState, deltaTime);
        assertEquals(ballNewY, ball.getY(), 0);
        assertEquals(ballNewDy,ball.getDy(), 0);
    }
}